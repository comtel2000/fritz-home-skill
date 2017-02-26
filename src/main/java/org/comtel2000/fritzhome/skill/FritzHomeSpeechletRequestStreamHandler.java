package org.comtel2000.fritzhome.skill;

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
  private static final Set<String> supportedApplicationIds = new HashSet<>();
  static {
    /*
     * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
     * Alexa Skill and put the relevant Application Ids in this Set.
     */
    // supportedApplicationIds.add("amzn1.echo-sdk-ams.app.[unique-value-here]");
  }

  public FritzHomeSpeechletRequestStreamHandler() {
    super(new FritzHomeSpeechlet(), supportedApplicationIds);
  }
}
