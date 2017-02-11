
package org.comtel2000.fritzhome.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tist" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="tsoll" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="komfort" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="absenk" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tist",
    "tsoll",
    "komfort",
    "absenk"
})
@XmlRootElement(name = "hkr")
public class Hkr {

    protected int tist;
    protected int tsoll;
    protected int komfort;
    protected int absenk;

    /**
     * Gets the value of the tist property.
     * 
     */
    public int getTist() {
        return tist;
    }

    /**
     * Sets the value of the tist property.
     * 
     */
    public void setTist(int value) {
        this.tist = value;
    }

    /**
     * Gets the value of the tsoll property.
     * 
     */
    public int getTsoll() {
        return tsoll;
    }

    /**
     * Sets the value of the tsoll property.
     * 
     */
    public void setTsoll(int value) {
        this.tsoll = value;
    }

    /**
     * Gets the value of the komfort property.
     * 
     */
    public int getKomfort() {
        return komfort;
    }

    /**
     * Sets the value of the komfort property.
     * 
     */
    public void setKomfort(int value) {
        this.komfort = value;
    }

    /**
     * Gets the value of the absenk property.
     * 
     */
    public int getAbsenk() {
        return absenk;
    }

    /**
     * Sets the value of the absenk property.
     * 
     */
    public void setAbsenk(int value) {
        this.absenk = value;
    }

}
