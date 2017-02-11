package org.comtel2000.fritzhome.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public interface Powermeter {

  IntegerProperty power = new SimpleIntegerProperty(-1);

  IntegerProperty energy = new SimpleIntegerProperty(-1);

  default IntegerProperty powerPorperty() {
    return power;
  }

  default int getPower() {
    return power.get();
  }

  default void setPower(int value) {
    power.set(value);
  }

  default IntegerProperty energyPorperty() {
    return energy;
  }

  default int getEnergy() {
    return energy.get();
  }

  default void setEnergy(int value) {
    energy.set(value);
  }
}
