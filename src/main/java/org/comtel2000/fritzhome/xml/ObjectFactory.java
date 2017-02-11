
package org.comtel2000.fritzhome.xml;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.comtel2000.fritzhome.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.comtel2000.fritzhome.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Group }
     * 
     */
    public Group createGroup() {
        return new Group();
    }

    /**
     * Create an instance of {@link Devicelist }
     * 
     */
    public Devicelist createDevicelist() {
        return new Devicelist();
    }

    /**
     * Create an instance of {@link Device }
     * 
     */
    public Device createDevice() {
        return new Device();
    }

    /**
     * Create an instance of {@link Switch }
     * 
     */
    public Switch createSwitch() {
        return new Switch();
    }

    /**
     * Create an instance of {@link Powermeter }
     * 
     */
    public Powermeter createPowermeter() {
        return new Powermeter();
    }

    /**
     * Create an instance of {@link Temperature }
     * 
     */
    public Temperature createTemperature() {
        return new Temperature();
    }

    /**
     * Create an instance of {@link Hkr }
     * 
     */
    public Hkr createHkr() {
        return new Hkr();
    }

    /**
     * Create an instance of {@link Group.Groupinfo }
     * 
     */
    public Group.Groupinfo createGroupGroupinfo() {
        return new Group.Groupinfo();
    }

}
