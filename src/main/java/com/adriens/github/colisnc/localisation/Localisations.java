/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adriens.github.colisnc.localisation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author 3004SAL
 *<br>
 * <p>
 * <code><b>Localisations</b></code> object represents a list of <code>Localisation</code> objects.
 * </p>
 * <u>example:</u>
 * <pre> {@code
 * public static void main(String[] args) throws Exception {
 *      JAXBContext jaxbContext = JAXBContext.newInstance(Localisations.class);
 *      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
 *
 *      //We had written this file in marshalling example
 *      Localisations parts = (Localisations) jaxbUnmarshaller.unmarshal(Localisations.class.getResourceAsStream("/localisations.xml"));
 *
 *      for (Localisation part : parts.getLocalisations()) {
 *          System.out.println(part.getName());
 *          System.out.println(part.getUrl());
 *          System.out.println("-------------------------------------------------");
 *      }
 *      
 *      System.out.println("######################################################");
 *      
 *      String aLocalisation = "NOUMEA-CTPdd";
 *      Localisation local = new Localisation();
 *      
 *      local = Localisations.locate(aLocalisation);
 *      System.out.println(local);
 *  }
 * }</pre>
 */
@XmlRootElement(name = "localisations")
@XmlAccessorType(XmlAccessType.FIELD)

public class Localisations {

    final static Logger logger = LoggerFactory.getLogger(Localisations.class);

    @XmlElement(name = "localisation")
    private List<Localisation> localisations = null;

    /**
     * Constructor
     */
    public Localisations() {
        localisations = new ArrayList<Localisation>();
    }
    
    /**
     * Return the list of Localisation objects.
     * @return the list of Localisation objects.
     */
    public List<Localisation> getLocalisations() {
        return localisations;
    }
    
    /**
     * Set the localisations.
     * @param localisations
     *              a list of Localisation objects.
     */
    public void setLocalisations(List<Localisation> localisations) {
        this.localisations = localisations;
    }
    
    /**
     * Return the Localisation object that match the localisation name in argument.
     * @param aLocalisation
     *              a localisation name.
     * @return the Localisation object that match the localisation name in argument. If the localisation not found, it returns a empty Localisation object.
     */
    public static Localisation locate(String aLocalisation) {

        Localisation out = new Localisation();

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Localisations.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Localisations parts = (Localisations) jaxbUnmarshaller.unmarshal(Localisations.class.getResourceAsStream("/localisations.xml"));

            // transform List into Hashmap
            Map<String, Localisation> cMap = parts.getLocalisations().stream().collect(Collectors.toMap(Localisation::getName, localization -> localization));
            // search in the hashmap
            out = cMap.get(aLocalisation);
            
            if (out != null) {
                return out;
            } else {
                logger.warn("Not able to find localization <" + aLocalisation + ">. Return an empty one.");
                return new Localisation();
            }

        } catch (JAXBException ex) {
            logger.error("Localizations xml parse error: " + ex.getMessage());
            return new Localisation();
        }
    }
    
}
