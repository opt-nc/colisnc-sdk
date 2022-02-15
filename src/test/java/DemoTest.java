/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.adriens.github.colisnc.colisnc.ColisCrawler;
import com.adriens.github.colisnc.colisnc.ColisDataRow;
import com.adriens.github.colisnc.countries.Country;
import com.adriens.github.colisnc.countries.ListCountriesDefinedLanguage;
import com.adriens.github.colisnc.localisation.Localisation;
import com.adriens.github.colisnc.localisation.Localisations;

import org.junit.Test;

/**
 * 
 * @author meilie
 * 
 */

public class DemoTest {
    
    @Test
    public void demoLocalisations() {
        assertTrue("there must be at least 10 localisation", Localisations.getLocalisations().size() > 10);

        for (Localisation part : Localisations.getLocalisations()) {
            System.out.println(part.getName());
            System.out.println(part.getUrl());
            System.out.println("-------------------------------------------------");
        }

        System.out.println("######################################################");

        String aLocalisation = "NOUMEA-CTP";
        Localisation local = Localisations.locate(aLocalisation);
        System.out.println(local);

        assertNotNull("we must find a city", local);
    }
    
    @Test
    public void demoCountry() {
        
        try {
            
            Country theCountry = ListCountriesDefinedLanguage.getCountry("etats-unis");
            assertEquals("etats-unis", theCountry.getName());
            
            System.out.println("find:\n" + theCountry);
            
        } catch (Exception ex) {
            System.err.println("Country not find:\n" + ex.getMessage());
            
        }
    }
    
    @Test
    public void demoColisRows() {
        
        //String itemId = "XX";
        String itemId = "8Z00136833343";
        assertNotNull("itemId is not null", itemId);
        
        try {
            
            List<ColisDataRow> coliadDetails = ColisCrawler.getColisRows(itemId);
            assertNotNull("we must find rows", coliadDetails);
            
            System.out.println("Got <" + coliadDetails.size() + "> details pour <" + itemId + ">");
            System.out.println("###############################################");
            System.out.println("latest : " + ColisCrawler.getLatest(itemId));
            System.out.println("############################################");
            
            List<String> aListOfColis = Arrays.asList(new String[]{"8Z00136833343", "XXX", "8Z00136833343", "8Z00136833343"});
            assertNotNull("we must find rows", aListOfColis);
            
            List<ColisDataRow> latestStatus = ColisCrawler.getLatestStatusForColisList(aListOfColis);
            Iterator<ColisDataRow> iterLatest = latestStatus.iterator();
            ColisDataRow aRow;
            
            while (iterLatest.hasNext()) 
            {
                aRow = iterLatest.next();
                System.out.println(aRow);
            }
            
        } catch (Exception ex) {
            System.err.println("Rows not find:\n" + ex.getMessage());
        }
        
    }
    
}
