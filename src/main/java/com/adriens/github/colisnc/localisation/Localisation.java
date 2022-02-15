/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adriens.github.colisnc.localisation;

/**
 * 
 * @author 3004SAL
 *         <br>
 *         <p>
 *         <code><b>Localisation</b></code> is the class representing the
 *         location/position of different city/places.
 *         </p>
 * 
 */
public class Localisation {

    private String name;
    private String longName;
    private String url;

    public Localisation(String name, String longName, String url) {
        this.name = name;
        this.longName = longName;
        this.url = url;
    }

    /**
     * Return the displayed name of the Localisation object.
     * 
     * @return the name of the Localisation.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the name of the Localisation object.
     * 
     * @return
     *         The name of the Localisation.
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Return the url to Google Map of the Localisation object.
     * 
     * @return the url of the Localisation.
     */
    public String getUrl() {
        return url;
    }

    public String toString() {
        String out = "";
        out = "Loc. Name : <" + getName() + ">\n";
        out += "Loc. url: <" + getUrl() + ">";
        return out;
    }
}
