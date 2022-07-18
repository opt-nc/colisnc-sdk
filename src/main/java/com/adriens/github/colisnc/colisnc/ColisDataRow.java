/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adriens.github.colisnc.colisnc;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adriens.github.colisnc.countries.Country;
import com.adriens.github.colisnc.localisation.Localisation;

/**
 *
 * @author 3004SAL
 * <br>
 * <p>
 * A <code><b>ColisDataRow</b></code> object represents a row for the parcel number.
 * </p>
 */
public class ColisDataRow {

    /**
     * Return the parcel ID.
     * @return the parcel ID.
     */
    public String getItemId() {
        return itemId;
    }
    
    /**
     * Set the parcel ID.
     * @param itemId
     *          The parcel number.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    
    /**
     * Return the parcel location/position.
     * @return the parcel location/position.
     */
    public Localisation getLocalization() {
        return localization;
    }
    
    /**
     * Set the parcel location/position.
     * @param localization
     *              The parcel location/position.
     */
    public void setLocalization(Localisation localization) {
        this.localization = localization;
    }
    
    static final Logger logger = LoggerFactory.getLogger(ColisDataRow.class);

    public enum Status {
     COLIS_LIVRE,
     COLIS_EN_COURS_ACHEMINEMENT,
     COLIS_EN_COURS_DEDOUANEMENT,
     COLIS_ARRIVE_PAYS_DESTINATION,
     COLIS_PRET_A_QUITTE_PAYS_ORIGINE,
     COLIS_A_QUITTE_PAYS_ORIGINE,
     COLIS_PRIS_EN_CHARGE,
     COLIS_NULL_STATUS,
     COLIS_PAS_PU_ETRE_LIVRE,
     UNEXPECTED
    }
    
    /**
     * Return the status of the row in parameter. 
     * @param aRow
     *          The row in the delivery process for the parcel concerned.
     * @return the status of the row in parameter.
     */
    public static final Status getStatus(ColisDataRow aRow){
   
        if(aRow == null){
            return null;
        }
        String aTypeEvenement = aRow.getTypeEvenement();
        if (aTypeEvenement == null){
            return Status.COLIS_NULL_STATUS;
        }
        
        // now test
        if (aTypeEvenement.contains("a été livré")){
            return Status.COLIS_LIVRE;
        }
        else if(aTypeEvenement.contains("en cours d'acheminement")){
            return Status.COLIS_EN_COURS_ACHEMINEMENT;
        }
        else if(aTypeEvenement.contains("en cours de dédouanement")){
            return Status.COLIS_EN_COURS_DEDOUANEMENT;
        }
        else if(aTypeEvenement.contains("arrivé dans le pays de destination")){
            return Status.COLIS_ARRIVE_PAYS_DESTINATION;
        }
        else if(aTypeEvenement.contains("prêt à quitter son pays d'origine")){
            return Status.COLIS_PRET_A_QUITTE_PAYS_ORIGINE;
        }
        else if(aTypeEvenement.contains("quitté le pays d'origine")){
            return Status.COLIS_A_QUITTE_PAYS_ORIGINE;
        }
        else if(aTypeEvenement.contains("a été pris en charge")){
            return Status.COLIS_PRIS_EN_CHARGE;
        }
        else if(aTypeEvenement.contains("n'a pas pu être livré")){
            return Status.COLIS_PAS_PU_ETRE_LIVRE;
        }
        else {
            logger.warn("Got unexpected status : <" + aTypeEvenement + ">. Returning <" + Status.UNEXPECTED + ">.");
            logger.warn("Plese consider adding this staus to test case ;-p");
            return Status.UNEXPECTED;
        }
    }
    
    /**
     * Return the row date and time as text.
     * @return the row date and time, as text.
     */
    public String getRawDateHeure() {
        return rawDateHeure;
    }
    
    /**
     * Set the row date and time as text.
     * @param rawDateHeure
     *          The row dateTime as text.
     */
    public void setRawDateHeure(String rawDateHeure) {
        this.rawDateHeure = rawDateHeure;
    }
    
    /**
     * Return the name of the row country.
     * @return the name of the row country.
     */
    public String getPays() {
        return pays;
    }
    
    /**
     * Set the name of the row country.
     * @param pays
     *          the name of the row country.
     */
    public void setPays(String pays) {
        this.pays = pays;
    }
    
    /**
     * Return the name of the location/place in the row.
     * @return the name of the location/place in the row.
     */
    public String getLocalisation() {
        return localisation;
    }
    
    /**
     * Set the name of the location/place in the row.
     * @param localisation
     *              The name of the location/place in the row.
     */
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    /**
     * Return the state of the parcel concerned.
     * @return the state of the parcel concerned, as text.
     */
    public String getTypeEvenement() {
        return typeEvenement;
    }
    
    /**
     * Set the state of the row of the parcel concerned, as text.
     * @param typeEvenement
     *              the state of the parcel concerned.
     */
    public void setTypeEvenement(String typeEvenement) {
        this.typeEvenement = typeEvenement;
    }
    
    /**
     * Return the row informations.
     * @return the row informations, as text.
     */
    public String getInformations() {
        return informations;
    }
    
    /**
     * Set the row informations.
     * @param informations
     *              The row informations, as text.
     */
    public void setInformations(String informations) {
        this.informations = informations;
    }
    
    /**
     * Return the row local date and time.
     * @return the LocalDateTime object.
     */
    public LocalDateTime getDate() {
        return date;
    }
    
    /** 
     * Set the row local date and time.
     * @param date 
     *          The local date and time.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    private String rawDateHeure;
    private String pays;
    private String localisation;
    private String typeEvenement;
    private String informations;
    private LocalDateTime date;
    private Status status;
    private Country country;
    private Localisation localization;
    private String itemId;
    
    /**
     * Construtor.
     */
    public ColisDataRow(){
        
    }
    
    /**
     * Set the row status.
     */
    public void setStatus(){
        setStatus(ColisDataRow.getStatus(this));
    }
    
    /**
     * Set the row status.
     * @param aStatus
     *          The row status.
     */
    public void setStatus(Status aStatus){
        this.status = aStatus;
    }
    
    /**
     * Return the row status.
     * @return the row Status object.
     */
    public Status getStatus(){
        return this.status;
    }
    
    /**
     * Set the row country.
     * @param aCountry
     *          A country.
     */
    public void setCountry(Country aCountry){
        this.country = aCountry;
    }
    
    /**
     * Return the row country.
     * @return the row Country object.
     */
    public Country getCountry(){
        return this.country;
    }
    
    public String toString(){
        String out = "";
        out = "Colis: <" + getItemId() + ">\n";
        out += "Date/Heure : <" + getRawDateHeure() + ">\n";
        out += "Localisation : <" + getLocalisation() + ">\n";
        out += "Status : <" + getStatus().toString() + ">";
        return out;
    }
}
