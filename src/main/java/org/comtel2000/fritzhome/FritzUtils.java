package org.comtel2000.fritzhome;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import org.comtel2000.fritzhome.SwitchDevice.State;

/**
 *
 * Fritz utilities
 *
 * @author comtel
 *
 * @see <a href=
 *      "https://avm.de/fileadmin/user_upload/Global/Service/Schnittstellen/AVM_Technical_Note_-_Session_ID.pdf">AVM_Technical_Note_-_Session_ID.pdf</a>
 */
public class FritzUtils {

  /**
   * <li>Bit 6: Comet DECT, Heizkostenregler
   * <li>Bit 7: Energie Messgerät
   * <li>Bit 8: Temperatursensor
   * <li>Bit 9: Schaltsteckdose
   * <li>Bit 10: AVM DECT Repeater
   */
  public static final int HKR_MASK = 1 << 6;
  public static final int POWERMETER_MASK = 1 << 7;
  public static final int TEMPERATURE_MASK = 1 << 8;
  public static final int SWITCH_MASK = 1 << 9;
  public static final int DECT_REPEATER_MASK = 1 << 10;

  public static boolean isHkr(int bitmask) {
    return (bitmask & HKR_MASK) != 0;
  }

  public static boolean isPowermeter(int bitmask) {
    return (bitmask & POWERMETER_MASK) != 0;
  }

  public static boolean isTemperature(int bitmask) {
    return (bitmask & TEMPERATURE_MASK) != 0;
  }

  public static boolean isSwitch(int bitmask) {
    return (bitmask & SWITCH_MASK) != 0;
  }

  public static boolean isDectRepeater(int bitmask) {
    return (bitmask & DECT_REPEATER_MASK) != 0;
  }

  public static String getChallengeResponse(String challenge, String pwd) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    return challenge + "-" + getMD5Hash(challenge + "-" + pwd);
  }

  private static String getMD5Hash(String message) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] hash = md.digest(message.getBytes("UTF-16LE"));

    StringBuilder sb = new StringBuilder();
    for (byte b : hash) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }


  public static String getState(Locale locale, State state) {
    switch (state) {
      case ON:
        return "An";
      case OFF:
        return "Aus";
      case UNDEFINED:
      default:
        return "offline";
    }
  }


  /**
   * Formated current power output (sample: 10.22 W)
   *
   * @param locale Locale
   * @param power value in 0.001 W (current power output, refresh interval 2 minutes)
   * @return power in W or kW
   */
  public static String getPower(Locale locale, int power) {
    if (power < 1) {
      return "0 Watt";
    }
    double watt = Math.floor(power) * 0.001d;
    if (watt < 1000) {
      return String.format(locale, "%.1f Watt", watt);
    }
    return String.format(locale, "%.2f kW", watt / 1000);
  }

  /**
   * Formated total energy consumption since start (sample: 12.1 Wh)
   *
   * @param locale Locale
   * @param energy value in 1.0 Wh (total energy consumption since start)
   * @return energy in Wh or kWh
   */
  public static String getEnergy(Locale locale, int energy) {
    if (energy < 1) {
      return "0 Wh";
    }
    if (energy < 1000) {
      return String.format(locale, "%.1f Wh", Math.floor(energy));
    }
    return String.format(locale, "%.1f kWh", Math.floor(energy) / 1000);
  }
  
  /**
   * Formated temperature output (sample: 24.1°C)
   *
   * @param locale Locale
   * @param celsius in 0,1°C, negative or positive value
   * @return temperature
   */
  public static String getTemperature(Locale locale, int celsius) {
    if ("US".equals(locale.getCountry())) {
      return getTemperatureF(locale, celsius);
    }
    double t = Math.floor(celsius) * 0.1;
    return String.format(locale, "%.1f°C", t);
  }

  /**
   * Formated temperature output (sample: 64.1°F)
   *
   * @param locale Locale
   * @param celsius in 0,1°C, negative or positive value
   * @return temperature in fahrenheit
   */
  public static String getTemperatureF(Locale locale, int celsius) {
    double t = Math.floor(celsius) * 0.1;
    return String.format(locale, "%.1f°F", toFahrenheit(t));
  }

 
  /**
   * XXtemperatur in 0,5°C, Values: 0x10 – 0x38 16 – 56 (8 to 28°C), 16 <= 8°C, 17 = 8,5°C...... 56
   * >= 28°C, 254 = ON , 253 = OFF
   *
   * @param locale Locale
   * @param value
   * @return (8°C to 28°C), ON, OFF, < 8°C, > 28°C
   */
  public static String getHkrTemperature(Locale locale, int value) {
    if ("US".equals(locale.getCountry())) {
      return getHkrTemperatureF(locale, value);
    }
    if (value == 254) {
      return "ON";
    }
    if (value == 253) {
      return "OFF";
    }
    if (value < 16) {
      return String.format(locale, "< %.1f°C", 8.0);
    }
    if (value > 56) {
      return String.format(locale, "> %.1f°C", 28.0);
    }
    double celsius = 8 + ((value - 16) * 0.5);
    return String.format(locale, "%.1f°C", celsius);
  }
 

  public static String getHkrTemperatureF(Locale locale, int value) {
    if (value == 254) {
      return "ON";
    }
    if (value == 253) {
      return "OFF";
    }
    if (value < 16) {
      return String.format(locale, "< %.1f°F", toFahrenheit(8.0));
    }
    if (value > 56) {
      return String.format(locale, "> %.1f°F", toFahrenheit(28.0));
    }
    double celsius = 8 + ((value - 16) * 0.5);
    return String.format(locale, "%.1f°F", toFahrenheit(celsius));
  }


    /** temperature in 0,5°C 
   *
   * @param locale Locale
   * @param value
   * @return ON, OFF, 
   */
  public static String writeTemperature(int value) {

    if (value == 254) {
      return "ON";
    }
    if (value == 253) {
      return "OFF";
    }
    return (String.valueOf(value * 2));
  }
  
  
    public static String readTemperature(String str) {

	int value = Integer.valueOf(str);
	
    if (value == 254) {
      return "ON";
    }
    if (value == 253) {
      return "OFF";
    }
    return (String.valueOf(value / 2));
  }
 
  
  
  private static double toFahrenheit(double celsius) {
    double f = 9 * celsius / 5 + 32;
    return f;
  }
}
