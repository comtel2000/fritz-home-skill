package org.comtel2000.fritzhome.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public interface Group extends Switch, Powermeter {

  List<Integer> members = new ArrayList<>();

  StringProperty masterId = new SimpleStringProperty();

  default StringProperty masterIdPorperty() {
    return masterId;
  }

  default String getMasterId() {
    return masterId.get();
  }

  default void setMasterId(String value) {
    masterId.set(value);
  }

  default List<Integer> getMembers() {
    return members;
  }

  default void setMembers(String value) {
    if (value != null) {
      String[] split = value.split(",");
      for (String v : split) {
        try {
          members.add(Integer.valueOf(v.trim()));
        } catch (Exception e) {
        }
      }
    }

  }

}
