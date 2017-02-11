package org.comtel2000.fritzhome;

import java.io.IOException;
import java.util.stream.Collectors;

import org.comtel2000.fritzhome.xml.Device;
import org.comtel2000.fritzhome.xml.Devicelist;
import org.comtel2000.fritzhome.xml.Group;
import org.junit.Test;

public class SwitchServiceTest {

  @Test
  public void testConnection() throws IOException, Exception {
    if (System.getenv(SwitchService.ENV_URL) == null) {
      System.err.println("no connection data - skip");
      return;
    }
    SwitchService service = new SwitchService();
    System.err.println(System.getenv(SwitchService.ENV_URL));
    service.validateConnection();

  }

  @Test
  public void testGetDeviceList() throws IOException, Exception {
    if (System.getenv(SwitchService.ENV_URL) == null) {
      System.err.println("no connection data - skip");
      return;
    }

    SwitchService service = new SwitchService();
    
    service.readDevicelist();
    
    Devicelist list = service.getDevicelist();
    System.out.println("version: " + list.getVersion());
    System.out.println("devices names: " + list.getDevice().stream().map(Device::getName).collect(Collectors.joining(",")));
    System.out.println("devices bitmasks: " + list.getDevice().stream().map(Device::getFunctionbitmask).map(String::valueOf).collect(Collectors.joining(",")));
    System.out.println("groups: " + list.getGroup().stream().map(Group::getName).collect(Collectors.joining(",")));
    System.err.println();
    service.getSwitchDevices(list).stream().forEach(System.out::println);
    
  }

  
}
