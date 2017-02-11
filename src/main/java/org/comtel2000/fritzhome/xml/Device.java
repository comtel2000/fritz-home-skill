
package org.comtel2000.fritzhome.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="present" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element ref="{}switch" minOccurs="0"/&gt;
 *         &lt;element ref="{}powermeter" minOccurs="0"/&gt;
 *         &lt;element ref="{}temperature" minOccurs="0"/&gt;
 *         &lt;element ref="{}hkr" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="identifier" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="functionbitmask" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="fwversion" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="manufacturer" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="productname" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "present",
    "name",
    "_switch",
    "powermeter",
    "temperature",
    "hkr"
})
@XmlRootElement(name = "device")
public class Device {

    protected boolean present;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(name = "switch")
    protected Switch _switch;
    protected Powermeter powermeter;
    protected Temperature temperature;
    protected Hkr hkr;
    @XmlAttribute(name = "identifier")
    protected String identifier;
    @XmlAttribute(name = "id")
    protected Integer id;
    @XmlAttribute(name = "functionbitmask")
    protected Integer functionbitmask;
    @XmlAttribute(name = "fwversion")
    protected String fwversion;
    @XmlAttribute(name = "manufacturer")
    protected String manufacturer;
    @XmlAttribute(name = "productname")
    protected String productname;

    /**
     * Gets the value of the present property.
     * 
     */
    public boolean isPresent() {
        return present;
    }

    /**
     * Sets the value of the present property.
     * 
     */
    public void setPresent(boolean value) {
        this.present = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the switch property.
     * 
     * @return
     *     possible object is
     *     {@link Switch }
     *     
     */
    public Switch getSwitch() {
        return _switch;
    }

    /**
     * Sets the value of the switch property.
     * 
     * @param value
     *     allowed object is
     *     {@link Switch }
     *     
     */
    public void setSwitch(Switch value) {
        this._switch = value;
    }

    /**
     * Gets the value of the powermeter property.
     * 
     * @return
     *     possible object is
     *     {@link Powermeter }
     *     
     */
    public Powermeter getPowermeter() {
        return powermeter;
    }

    /**
     * Sets the value of the powermeter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Powermeter }
     *     
     */
    public void setPowermeter(Powermeter value) {
        this.powermeter = value;
    }

    /**
     * Gets the value of the temperature property.
     * 
     * @return
     *     possible object is
     *     {@link Temperature }
     *     
     */
    public Temperature getTemperature() {
        return temperature;
    }

    /**
     * Sets the value of the temperature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Temperature }
     *     
     */
    public void setTemperature(Temperature value) {
        this.temperature = value;
    }

    /**
     * Gets the value of the hkr property.
     * 
     * @return
     *     possible object is
     *     {@link Hkr }
     *     
     */
    public Hkr getHkr() {
        return hkr;
    }

    /**
     * Sets the value of the hkr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Hkr }
     *     
     */
    public void setHkr(Hkr value) {
        this.hkr = value;
    }

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifier(String value) {
        this.identifier = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the functionbitmask property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFunctionbitmask() {
        return functionbitmask;
    }

    /**
     * Sets the value of the functionbitmask property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFunctionbitmask(Integer value) {
        this.functionbitmask = value;
    }

    /**
     * Gets the value of the fwversion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFwversion() {
        return fwversion;
    }

    /**
     * Sets the value of the fwversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFwversion(String value) {
        this.fwversion = value;
    }

    /**
     * Gets the value of the manufacturer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the value of the manufacturer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturer(String value) {
        this.manufacturer = value;
    }

    /**
     * Gets the value of the productname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductname() {
        return productname;
    }

    /**
     * Sets the value of the productname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductname(String value) {
        this.productname = value;
    }

}
