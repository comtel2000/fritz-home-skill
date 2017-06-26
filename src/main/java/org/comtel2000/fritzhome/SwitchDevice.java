package org.comtel2000.fritzhome;

import java.io.Serializable;

public class SwitchDevice implements Serializable {

  private static final long serialVersionUID = -6546912948581699562L;

  public enum State {
    UNDEFINED, ON, OFF
  }

  public enum Mode {
    UNDEFINED, AUTO, MANUAL
  }

  public enum Lock {
    UNDEFINED, LOCKED, UNLOCKED
  }

  private final String ain;
  private final int bitmask;
  private String name;
  private String ID;
  private String masterDevice;
  private String GroupMembers;

  private State state = State.UNDEFINED;
  private Mode mode = Mode.UNDEFINED;
  private Lock lock = Lock.UNDEFINED;

  private int power;
  private int energy;
  private int temperature;

  private boolean group;
  
  private boolean present;

  public SwitchDevice(String ain, int bitmask) {
    this.ain = ain;
    this.bitmask = bitmask;
  }

  public final String getAin() {
    return ain;
  }

  public boolean isHkr() {
    return FritzUtils.isHkr(bitmask);
  }

  public boolean isPowermeter() {
    return FritzUtils.isPowermeter(bitmask);
  }

  public boolean isTemperature() {
    return FritzUtils.isTemperature(bitmask);
  }

  public boolean isSwitch() {
    return FritzUtils.isSwitch(bitmask);
  }

  public boolean isDectRepeater() {
    return FritzUtils.isDectRepeater(bitmask);
  }

  public final String getName() {
    return name;
  }

  public final void setName(String name) {
    this.name = name;
  }
  
  public final String getId() {
    return ID;
  }

  public final void setId(String id) {
    this.ID = id;
  }

  public final State getState() {
    return state;
  }

  public final void setState(State state) {
    this.state = state;
  }

  /**
   * Ermittelt aktuell über die Steckdose entnommene Leistung Leistung in mW, "inval" wenn unbekannt
   */
  public final int getPower() {
    return power;
  }

  public final void setPower(int power) {
    this.power = power;
  }

  public final void setPower(String power) {
    try {
      this.power = Integer.parseInt(power);
    } catch (Exception e) {
      this.power = -1;
    }
  }

  /**
   * Liefert die über die Steckdose entnommene Ernergiemenge seit Erstinbetriebnahme oder
   * Zurücksetzen der Energiestatistik Energie in Wh, "inval" wenn unbekannt
   */
  public final int getEnergy() {
    return energy;
  }

  public final void setEnergy(int energy) {
    this.energy = energy;
  }

  public final void setEnergy(String energy) {
    try {
      this.energy = Integer.parseInt(energy);
    } catch (Exception e) {
      this.energy = -1;
    }
  }

  public void setState(String state) {
    switch (state) {
      case "0":
        setState(State.OFF);
        break;
      case "1":
        setState(State.ON);
        break;
      default:
        setState(State.UNDEFINED);
        break;
    }
  }

  public Mode getMode() {
    return mode;
  }

  public void setMode(Mode s) {
    mode = s;
  }

  public void setMode(String value) {
    if (value == null) {
      setMode(Mode.UNDEFINED);
      return;
    }
    switch (value) {
      case "auto":
        setMode(Mode.AUTO);
        break;
      case "manuell":
        setMode(Mode.MANUAL);
        break;
      default:
        setMode(Mode.UNDEFINED);
        break;
    }
  }

  public Lock getLock() {
    return lock;
  }

  public void setLock(Lock s) {
    lock = s;
  }

  public void setLock(String value) {
    if (value == null) {
      setLock(Lock.UNDEFINED);
      return;
    }
    switch (value) {
      case "0":
        setLock(Lock.UNLOCKED);
        break;
      case "1":
        setLock(Lock.LOCKED);
        break;
      default:
        setLock(Lock.UNDEFINED);
        break;
    }
  }

  public boolean isPresent() {
    return present;
  }

  public void setPresent(boolean present) {
    this.present = present;
  }

  public int getTemperature() {
    return temperature;
  }

  public void setTemperature(int temperature) {
    this.temperature = temperature;
  }

  public boolean isGroup() {
    return group;
  }

  public void setGroup(boolean group) {
    this.group = group;
  }
  
  public String getGroupmembers(){
	return GroupMembers;
  }
  
  public void setGroupmembers(String groupmembers){
	this.GroupMembers = groupmembers;
  }
  
  public String getMasterdevice(){
	return masterDevice;
  }
  
  public void setMasterdevice(String masterdevice){
	this.masterDevice = masterdevice;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ain == null) ? 0 : ain.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SwitchDevice other = (SwitchDevice) obj;
    if (ain == null) {
      if (other.ain != null)
        return false;
    } else if (!ain.equals(other.ain))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SwitchDevice [ain=" + ain + ", name=" + name + ", bitmask=" + bitmask + ", present=" + present + ", state=" + state + ", power=" + power
        + ", energy=" + energy + ", temp.=" + temperature + "]";
  }
}
