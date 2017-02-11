package org.comtel2000.fritzhome.model;

public abstract class FritzDevice implements FDevice {

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getAin() == null) ? 0 : getAin().hashCode());
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
    FritzDevice other = (FritzDevice) obj;
    if (getAin() == null) {
      if (other.getAin() != null)
        return false;
    } else if (!getAin().equals(other.getAin()))
      return false;
    return true;
  }
}
