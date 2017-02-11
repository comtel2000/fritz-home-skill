package org.comtel2000.fritzhome.model;

import org.comtel2000.fritzhome.FritzUtils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public interface FDevice {

  StringProperty ain = new SimpleStringProperty();
  IntegerProperty id = new SimpleIntegerProperty(0);
  BooleanProperty present = new SimpleBooleanProperty();
  IntegerProperty bitmask = new SimpleIntegerProperty(0);

  StringProperty manufacturer = new SimpleStringProperty();
  StringProperty product = new SimpleStringProperty();
  StringProperty firmware = new SimpleStringProperty();


  public default boolean isHkr() {
    return FritzUtils.isHkr(bitmask.get());
  }

  public default boolean isPowermeter() {
    return FritzUtils.isPowermeter(bitmask.get());
  }

  public default boolean isTemperature() {
    return FritzUtils.isTemperature(bitmask.get());
  }

  public default boolean isSwitch() {
    return FritzUtils.isSwitch(bitmask.get());
  }

  public default boolean isDectRepeater() {
    return FritzUtils.isDectRepeater(bitmask.get());
  }


  public default StringProperty ainPorperty() {
    return ain;
  }

  public default String getAin() {
    return ain.get();
  }

  public default void setAin(String value) {
    ain.set(value);
  }

  public default IntegerProperty idPorperty() {
    return id;
  }

  public default int getId() {
    return id.get();
  }

  public default void setId(int value) {
    id.set(value);
  }

  public default IntegerProperty bitmaskPorperty() {
    return bitmask;
  }

  public default int getBitmask() {
    return bitmask.get();
  }

  public default void setBitmask(int value) {
    bitmask.set(value);
  }

  public default BooleanProperty presentPorperty() {
    return present;
  }

  public default boolean isPresent() {
    return present.get();
  }

  public default void setPresent(boolean value) {
    present.set(value);
  }

  public default StringProperty manufacturerPorperty() {
    return manufacturer;
  }

  public default String getManufacturer() {
    return manufacturer.get();
  }

  public default void setManufacturer(String value) {
    manufacturer.set(value);
  }

  public default StringProperty productPorperty() {
    return product;
  }

  public default String getProduct() {
    return product.get();
  }

  public default void setProduct(String value) {
    product.set(value);
  }

  public default StringProperty firmwarePorperty() {
    return firmware;
  }

  public default String getFirmware() {
    return firmware.get();
  }

  public default void setFirmware(String value) {
    firmware.set(value);
  }
}
