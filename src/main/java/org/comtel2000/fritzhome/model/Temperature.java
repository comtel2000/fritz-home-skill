package org.comtel2000.fritzhome.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public interface Temperature {

  IntegerProperty temp = new SimpleIntegerProperty(-1);

  default IntegerProperty tempPorperty() {
    return temp;
  }

  default int getTemp() {
    return temp.get();
  }

  default void setTemp(int value) {
    temp.set(value);
  }

}
