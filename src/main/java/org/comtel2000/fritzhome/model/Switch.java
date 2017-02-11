package org.comtel2000.fritzhome.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public interface Switch {

  public enum State {
    UNDEFINED, ON, OFF
  }

  public enum Mode {
    UNDEFINED, AUTO, MANUAL
  }

  public enum Lock {
    UNDEFINED, LOCKED, UNLOCKED
  }
  
  ObjectProperty<State> state = new SimpleObjectProperty<>(State.UNDEFINED);
  ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.UNDEFINED);
  ObjectProperty<Lock> lock = new SimpleObjectProperty<>(Lock.UNDEFINED);
  
  public default ObjectProperty<State> statePorperty() {
    return state;
  }

  public default State getState() {
    return state.get();
  }

  public default void setState(State s) {
    state.set(s);
  }

  public default void setState(String value) {
    if (value == null) {
      setState(State.UNDEFINED);
      return;
    }
    switch (value) {
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

  public default ObjectProperty<Mode> modePorperty() {
    return mode;
  }

  public default Mode getMode() {
    return mode.get();
  }

  public default void setMode(Mode s) {
    mode.set(s);
  }

  public default void setMode(String value) {
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
  
  public default ObjectProperty<Lock> lockPorperty() {
    return lock;
  }

  public default Lock getLock() {
    return lock.get();
  }

  public default void setLock(Lock s) {
    lock.set(s);
  }

  public default void setLock(String value) {
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
}
