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
    supportedApplicationIds.add("amzn1.ask.skill.69ed8a07-ba0f-4ca0-886e-ea2ca01a2e77");
  }

  public FritzHomeSpeechletRequestStreamHandler() {
    super(new FritzHomeSpeechlet(), supportedApplicationIds);
  }
}
