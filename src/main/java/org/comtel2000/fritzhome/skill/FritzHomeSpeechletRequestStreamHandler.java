package org.comtel2000.fritzhome.skill;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

/**
 * org.comtel2000.fritzhome.skill.FritzHomeSpeechletRequestStreamHandler
 *
 * @author comtel
 *
 */
public final class FritzHomeSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

  public final static String ENV_APPID = "FRITZHOME_APPID";

  private static Set<String> supportedApplicationIds = new HashSet<>();

  static {
    String appID = System.getenv(ENV_APPID);
    if (appID != null && appID.length() > 0){
      supportedApplicationIds = Collections.singleton(appID.startsWith("amzn") ? appID : "amzn1.echo-sdk-ams.app." + appID);
    }
  }

  public FritzHomeSpeechletRequestStreamHandler() {
    super(new FritzHomeSpeechlet(), supportedApplicationIds);
  }
}
