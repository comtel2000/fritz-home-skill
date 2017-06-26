
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
 *         &lt;element name="groupinfo"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="masterdeviceid" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                   &lt;element name="members" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "groupinfo",
	"temperature"
})
@XmlRootElement(name = "group")
public class Group {

    protected boolean present;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(name = "switch")
    protected Switch _switch;
	 protected Temperature temperature;
    @XmlElement(required = true)
    protected Group.Groupinfo groupinfo;
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
     * Gets the value of the groupinfo property.
     * 
     * @return
     *     possible object is
     *     {@link Group.Groupinfo }
     *     
     */
    public Group.Groupinfo getGroupinfo() {
        return groupinfo;
    }

    /**
     * Sets the value of the groupinfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Group.Groupinfo }
     *     
     */
    public void setGroupinfo(Group.Groupinfo value) {
        this.groupinfo = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="masterdeviceid" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *         &lt;element name="members" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "masterdeviceid",
        "members"
    })
    public static class Groupinfo {

        protected int masterdeviceid;
        @XmlElement(required = true)
        protected String members;

        /**
         * Gets the value of the masterdeviceid property.
         * 
         */
        public int getMasterdeviceid() {
            return masterdeviceid;
        }

        /**
         * Sets the value of the masterdeviceid property.
         * 
         */
        public void setMasterdeviceid(int value) {
            this.masterdeviceid = value;
        }

        /**
         * Gets the value of the members property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMembers() {
            return members;
        }

        /**
         * Sets the value of the members property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMembers(String value) {
            this.members = value;
        }

    }

}
