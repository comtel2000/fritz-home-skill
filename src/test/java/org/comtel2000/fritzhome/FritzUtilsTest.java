package org.comtel2000.fritzhome;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import org.junit.Test;

public class FritzUtilsTest {

  @Test
  public void testChallengeResponse() throws UnsupportedEncodingException, NoSuchAlgorithmException {
    String challenge = "1234567z";
    String pwd = "äbc";
    assertEquals("1234567z-9e224a41eeefa284df7bb0f26c2913e2", FritzUtils.getChallengeResponse(challenge, pwd));
  }

  @Test
  public void testGetPower() {
    assertEquals("0 Watt", FritzUtils.getPower(new Locale("DE", "de"), 0));
    assertEquals(String.format("%.1f Watt", .1), FritzUtils.getPower(new Locale("DE", "de"), 100));
    assertEquals(String.format("%.1f Watt", 1.57), FritzUtils.getPower(new Locale("DE", "de"), 1570));
    assertEquals(String.format("%.2f kW", 1.11), FritzUtils.getPower(new Locale("DE", "de"), 1111190));
  }

  @Test
  public void testGetEnergy() {
    assertEquals("0 Wh", FritzUtils.getEnergy(new Locale("DE", "de"), 0));
    assertEquals(String.format("%.1f Wh", 100.0), FritzUtils.getEnergy(new Locale("DE", "de"), 100));
    assertEquals(String.format("%.1f kWh", 100.0), FritzUtils.getEnergy(new Locale("DE", "de"), 100000));
  }

  @Test
  public void testGetTemperature() {
    assertEquals(String.format("%.1f°C", 1.0), FritzUtils.getTemperature(new Locale("DE", "de"), 10));
    assertEquals(String.format("%.1f°C", 10.0), FritzUtils.getTemperature(new Locale("DE", "de"), 100));
    assertEquals(String.format("%.1f°C", -1.0), FritzUtils.getTemperature(new Locale("DE", "de"), -10));
    
    assertEquals(String.format(new Locale("US", "us"), "%.1f°F", 33.8), FritzUtils.getTemperature(new Locale("US", "us"), 10));
    assertEquals(String.format(new Locale("US", "us"), "%.1f°F", 50.0), FritzUtils.getTemperature(new Locale("US", "us"), 100));
    assertEquals(String.format(new Locale("US", "us"), "%.1f°F", 30.2), FritzUtils.getTemperature(new Locale("US", "us"), -10));
    
  }


  @Test
  public void testGetTemperatureF() {
    assertEquals(String.format("%.1f°F", 33.8), FritzUtils.getTemperatureF(new Locale("DE", "de"), 10));
    assertEquals(String.format("%.1f°F", 50.0), FritzUtils.getTemperatureF(new Locale("DE", "de"), 100));
    assertEquals(String.format("%.1f°F", 30.2), FritzUtils.getTemperatureF(new Locale("DE", "de"), -10));
  }

  @Test
  public void testDeviceType() {

    assertFalse(FritzUtils.isHkr(0));
    assertFalse(FritzUtils.isHkr(512));
    assertTrue(FritzUtils.isHkr(64));

    assertFalse(FritzUtils.isHkr(512));
    assertFalse(FritzUtils.isPowermeter(512));
    assertFalse(FritzUtils.isTemperature(512));
    assertTrue(FritzUtils.isSwitch(512));
    assertFalse(FritzUtils.isDectRepeater(512));

    assertFalse(FritzUtils.isHkr(896));
    assertTrue(FritzUtils.isPowermeter(896));
    assertTrue(FritzUtils.isTemperature(896));
    assertTrue(FritzUtils.isSwitch(896));
    assertFalse(FritzUtils.isDectRepeater(896));

    assertFalse(FritzUtils.isHkr(2944));
    assertTrue(FritzUtils.isPowermeter(2944));
    assertTrue(FritzUtils.isTemperature(2944));
    assertTrue(FritzUtils.isSwitch(2944));
    assertFalse(FritzUtils.isDectRepeater(2944));
  }

  @Test
  public void testGetHkrTemperature() {

    assertEquals("ON", FritzUtils.getHkrTemperature(new Locale("DE", "de"), 254));
    assertEquals("OFF", FritzUtils.getHkrTemperature(new Locale("DE", "de"), 253));
    assertEquals(String.format("< %.1f°C", 8.0), FritzUtils.getHkrTemperature(new Locale("DE", "de"), 15));
    assertEquals(String.format("> %.1f°C", 28.0), FritzUtils.getHkrTemperature(new Locale("DE", "de"), 57));

    assertEquals(String.format("%.1f°C", 8.0), FritzUtils.getHkrTemperature(new Locale("DE", "de"), 16));
    assertEquals(String.format("%.1f°C", 8.5), FritzUtils.getHkrTemperature(new Locale("DE", "de"), 17));
    assertEquals(String.format("%.1f°C", 27.5), FritzUtils.getHkrTemperature(new Locale("DE", "de"), 55));
    assertEquals(String.format("%.1f°C", 28.0), FritzUtils.getHkrTemperature(new Locale("DE", "de"), 56));
  }


}
