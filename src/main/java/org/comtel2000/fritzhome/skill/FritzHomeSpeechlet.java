package org.comtel2000.fritzhome.skill;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.comtel2000.fritzhome.FritzUtils;
import org.comtel2000.fritzhome.SwitchDevice;
import org.comtel2000.fritzhome.SwitchDevice.State;
import org.comtel2000.fritzhome.SwitchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

public class FritzHomeSpeechlet implements Speechlet {

  private SwitchService service;

  private static final Logger logger = LoggerFactory.getLogger(FritzHomeSpeechlet.class);

  private final static String STATUS_INTENT = "StatusIntent";
  private final static String LIST_DEVICE_INTENT = "ListDeviceIntent";
  private final static String ALL_DEVICE_ON_INTENT = "AllDeviceOnIntent";
  private final static String ALL_DEVICE_OFF_INTENT = "AllDeviceOffIntent";
  private final static String DEVICE_ON_INTENT = "DeviceOnIntent";
  private final static String DEVICE_OFF_INTENT = "DeviceOffIntent";
  private final static String DEVICE_TEMP_INTENT = "DeviceTempIntent";
  private final static String DEVICE_POWER_INTENT = "DevicePowerIntent";

  private final static String HELP_INTENT = "AMAZON.HelpIntent";
  private final static String CANCEL_INTENT = "AMAZON.CancelIntent";
  private final static String STOP_INTENT = "AMAZON.StopIntent";

  private static final String DEVICE_SLOT = "Device";

  @Override
  public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
    logger.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    service = new SwitchService();
  }

  @Override
  public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
    logger.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    return getWelcomeResponse();
  }

  @Override
  public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
    logger.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

    Intent intent = request.getIntent();
    if (intent == null || intent.getName() == null) {
      throw new SpeechletException("Invalid Intent");
    }
    if (service == null) {
      return newResponse("Die Session ist geschlossen");
    }

    switch (intent.getName()) {
      case STATUS_INTENT:
        return getStatusResponse(request.getLocale());
      case LIST_DEVICE_INTENT:
        return getListDeviceResponse(request.getLocale());
      case ALL_DEVICE_ON_INTENT:
        return setAllDevice(true);
      case ALL_DEVICE_OFF_INTENT:
        return setAllDevice(false);
      case DEVICE_ON_INTENT:
        return setDevice(intent, true);
      case DEVICE_OFF_INTENT:
        return setDevice(intent, false);
      case DEVICE_TEMP_INTENT:
        return getDeviceTemp(intent, request.getLocale());
      case DEVICE_POWER_INTENT:
        return getDeviceEnergy(intent, request.getLocale());
      case HELP_INTENT:
        return getHelpResponse();
      case CANCEL_INTENT:
      case STOP_INTENT:
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Ok");
        return SpeechletResponse.newTellResponse(outputSpeech);
      default:
        throw new SpeechletException("Unknown Intent: " + intent.getName());
    }
  }

  private SpeechletResponse setDevice(final Intent intent, boolean enable) {
    Slot deviceSlot = intent.getSlot(DEVICE_SLOT);
    if (deviceSlot == null || deviceSlot.getValue() == null) {
      return newResponse("Gerät nicht erkannt");
    }

    try {
      Optional<SwitchDevice> dev = findDevice(deviceSlot.getValue());
      if (!dev.isPresent()) {
        return newResponse(String.format("Das Gerät %s ist mir nicht bekannt", deviceSlot.getValue()));
      }
      SwitchDevice device = dev.get();
      if (!device.isSwitch()) {
        return newResponse(String.format("%s %s ist kein Schaltgerät", toGroupName(device), deviceSlot.getValue()));
      }
      if (!device.isPresent()) {
        return newResponse(String.format("%s %s ist nicht verbunden", toGroupName(device), deviceSlot.getValue()));
      }
      service.setDeviceState(device, enable);
      boolean succeed = device.getState() == (enable ? State.ON : State.OFF);
      return newResponse("Fritz Home Switch", succeed ? "Ok" : String.format("Das Gerät %s konnte nicht geschaltet werden", deviceSlot.getValue()));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return newResponse("Es ist ein Fehler beim Schalten des Gerätes aufgetreten");
  }

  private SpeechletResponse getDeviceTemp(final Intent intent, final Locale locale) {
    Slot deviceSlot = intent.getSlot(DEVICE_SLOT);
    if (deviceSlot == null || deviceSlot.getValue() == null) {
      return newResponse("Gerät nicht erkannt");
    }

    try {
      Optional<SwitchDevice> dev = findDevice(deviceSlot.getValue());
      if (!dev.isPresent()) {
        return newResponse(String.format("Das Gerät %s ist mir nicht bekannt", deviceSlot.getValue()));
      }
      SwitchDevice device = dev.get();
      if (!device.isPresent()) {
        return newResponse(String.format("%s %s ist aktuell nicht verfügbar", toGroupName(device), deviceSlot.getValue()));
      }
      if (!device.isTemperature()) {
        return newResponse(String.format("%s %s hat keinen Temperatursensor", toGroupName(device), deviceSlot.getValue()));
      }
      return newResponse("Fritz Home Temperatur",
          String.format("Die Temperatur an Gerät %s beträgt %s", deviceSlot.getValue(), FritzUtils.getTemperature(locale, device.getTemperature())));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return newResponse("Es ist ein Fehler beim Lesen der Gerätetemperatur aufgetreten");
  }

  private SpeechletResponse getDeviceEnergy(final Intent intent, final Locale locale) {
    Slot deviceSlot = intent.getSlot(DEVICE_SLOT);
    if (deviceSlot == null || deviceSlot.getValue() == null) {
      return newResponse("Gerät nicht erkannt");
    }

    try {
      Optional<SwitchDevice> dev = findDevice(deviceSlot.getValue());
      if (!dev.isPresent()) {
        return newResponse(String.format("Das Gerät %s ist mir nicht bekannt", deviceSlot.getValue()));
      }
      SwitchDevice device = dev.get();
      if (!device.isPresent()) {
        return newResponse(String.format("%s %s ist aktuell nicht verfügbar", toGroupName(device), deviceSlot.getValue()));
      }
      if (!device.isPowermeter()) {
        return newResponse(String.format("%s %s hat keine Energieverbauchswerte", toGroupName(device), deviceSlot.getValue()));
      }
      StringBuilder sb = new StringBuilder();
      sb.append("Der aktuelle Energieverbrauch von ").append(deviceSlot.getValue()).append(" ist ")
          .append(FritzUtils.getPower(locale, device.getPower()));
      if (device.getEnergy() > 0) {
        sb.append(" mit einem Gesamtverbrauch von ").append(FritzUtils.getEnergy(locale, device.getEnergy()));
      }

      return newResponse("Fritz Home Energie", sb.toString());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return newResponse("Es ist ein Fehler beim Lesen des Energieverbrauches aufgetreten");
  }

  private Optional<SwitchDevice> findDevice(String name) throws Exception {
    if (name == null || name.isEmpty()) {
      return Optional.empty();
    }
    Collection<SwitchDevice> devices = service.getSwitchDevices();
    if (devices.isEmpty()) {
      return Optional.empty();
    }
    Optional<SwitchDevice> dev = devices.stream().filter(d -> name.equalsIgnoreCase(d.getName())).findAny();
    if (!dev.isPresent()) {
      String cleanName = name.replaceAll("\\.\\s*", "");
      dev = devices.stream().filter(d -> cleanName.equalsIgnoreCase(d.getName().replaceAll("\\.\\s*", ""))).findAny();
    }
    if (!dev.isPresent()) {
      String cleanName = name.replaceAll("\\.\\s*", "").toLowerCase();
      dev = devices.stream().filter(d -> d.getName().replaceAll("\\.\\s*", "").toLowerCase().startsWith(cleanName)).findAny();
    }
    return dev;
  }

  private SpeechletResponse setAllDevice(boolean enable) {

    try {
      Collection<SwitchDevice> devices = service.getSwitchDevices();
      devices.stream().filter(d -> d.isPresent()).forEach(dev -> {
        try {
          service.setDeviceState(dev, enable);
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }
      });

      return newResponse("Fritz Home Switch All", "ok");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return newResponse("Es ist ein Fehler beim Schalten der Geräte aufgetreten");
  }

  @Override
  public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
    logger.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    service = null;
  }

  private SpeechletResponse getWelcomeResponse() {
    return newResponse("Fritz Home", "Willkommen bei Fritz Home");
  }

  private SpeechletResponse getStatusResponse(Locale locale) {
    try {
      service.validateConnection();
      return newResponse("Fritz Home Status", "Der Verbindungsstatus ist online");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return newResponse("Fritz Home Status", "Der Verbindungsstatus ist offline");
  }

  private SpeechletResponse getListDeviceResponse(Locale locale) {

    try {
      Collection<SwitchDevice> devices = service.getSwitchDevices();
      String speechText = "Hier die Liste der Geräte:\n" + devices.stream().map(deviceOutput(locale)).collect(Collectors.joining("\n"));

      return newResponse("Fritz Home Geräte Liste", speechText);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return newResponse("Fehler beim Abruf der Geräteliste.");
  }

  private Function<SwitchDevice, String> deviceOutput(Locale locale) {
    return (dev) -> {
      StringBuilder sb = new StringBuilder();
      if (!dev.isPresent()) {
        sb.append(toGroupName(dev)).append(dev.getName()).append(" ist aktuell nicht verfügbar");
        return sb.toString();
      }
      sb.append(toGroupName(dev)).append(dev.getName()).append(" ist ").append(toString(dev.getState()));
      if (dev.isTemperature() && dev.isPowermeter()) {
        sb.append(" ,die Temperatur beträgt ").append(FritzUtils.getTemperature(locale, dev.getTemperature()));
        sb.append(" und der aktuelle Energieverbrauch liegt bei ").append(FritzUtils.getPower(locale, dev.getPower()));
        if (dev.getEnergy() > 0) {
          sb.append(" mit einem Gesamtverbrauch von ").append(FritzUtils.getEnergy(locale, dev.getEnergy()));
        }
      } else if (dev.isTemperature()) {
        sb.append(" und die Temperatur beträgt ").append(FritzUtils.getTemperature(locale, dev.getTemperature()));
      } else if (dev.isPowermeter()) {
        sb.append(" und der aktuelle Energieverbrauch liegt bei ").append(FritzUtils.getPower(locale, dev.getPower()));
        if (dev.getEnergy() > 0) {
          sb.append(" mit einem Gesamtverbrauch von ").append(FritzUtils.getEnergy(locale, dev.getEnergy()));
        }
      }
      return sb.append(".").toString();
    };
  }

  private String toGroupName(SwitchDevice dev) {
    if (dev.isGroup()) {
      return "Die Gerätegruppe";
    }
    return "Das Gerät";
  }

  private String toString(State state) {
    switch (state) {
      case ON:
        return "eingeschaltet";
      case OFF:
        return "ausgeschaltet";
      default:
        return "unbekannt";
    }

  }

  private SpeechletResponse getHelpResponse() {
    return newResponse("Frage nach Status, Geräteliste oder sage: schalte Gerät an oder aus.");
  }


  private SpeechletResponse newResponse(String text) {
    return newResponse("Fritz Home", text);
  }

  private SpeechletResponse newResponse(String title, String text) {
    SimpleCard card = new SimpleCard();
    card.setTitle(title);
    card.setContent(text);

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(text);

    return SpeechletResponse.newTellResponse(speech, card);
  }

}
