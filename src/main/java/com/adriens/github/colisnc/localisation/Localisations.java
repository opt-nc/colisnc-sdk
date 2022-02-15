/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adriens.github.colisnc.localisation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author 3004SAL
 *         <br>
 *         <p>
 *         <code><b>Localisations</b></code> object represents a list of
 *         <code>Localisation</code> objects.
 *         </p>
 *         <u>example:</u>
 * 
 *         <pre>
 *  {@code
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
 * }
 *         </pre>
 */

public class Localisations {

    private static final Logger logger = LoggerFactory.getLogger(Localisations.class);

    private static List<Localisation> localisations = null;

    public static List<Localisation> getLocalisations() {
        init();
        return localisations;
    }

    /**
     * Return the Localisation object that match the localisation name in argument.
     * 
     * @param aLocalisation
     *                      a localisation name.
     * @return the Localisation object that match the localisation name in argument.
     *         If the localisation not found, it returns a empty Localisation
     *         object.
     */
    public static Localisation locate(String aLocalisation) {
        init();
        Localisation localisation = localisations.stream().filter(l -> l.getName().equals(aLocalisation)).findFirst()
                .orElse(null);

        if (localisation == null) {
            return localisation;
        } else {
            logger.warn("Not able to find localization <" + aLocalisation + ">. Return an empty one.");
            return new Localisation("", "", "");
        }
    }

    private static void init() {
        if (localisations == null) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            try {
                dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

                // parse XML file
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(Localisations.class.getResourceAsStream("/localisations.xml"));

                doc.getDocumentElement().normalize();

                localisations = new ArrayList<>();

                NodeList list = doc.getElementsByTagName("localisation");
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        String name = element.getElementsByTagName("name").item(0).getTextContent();
                        Localisation localisation = new Localisation(name,
                                element.getElementsByTagName("longName").item(0).getTextContent(),
                                element.getElementsByTagName("url").item(0).getTextContent());

                        localisations.add(localisation);
                    }
                }

            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new RuntimeException("Localizations xml parse error", e);
            }
        }
    }
}
