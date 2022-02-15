/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;

import com.adriens.github.colisnc.colisnc.ColisDataRow;
import com.adriens.github.colisnc.countries.Country;
import com.adriens.github.colisnc.localisation.Localisation;

import org.junit.Test;

/**
 *
 * @author meilie
 */

public class ColisDataRowTest {
    
    @Test
    public void testGetLocalization() {
        try {
            
            ColisDataRow instance = new ColisDataRow();
            Localisation localisation = new Localisation("NOUMEA-TEST",null,null);
            instance.setLocalization(localisation);
            assertEquals("localisation name set to \"NOUMEA-TEST\"", instance.getLocalization(), localisation);
            
        } catch (Exception ex) {
            
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }
    
    @Test
    public void testGetStatusNull() {
        try {
            
            ColisDataRow aRow = null;
            assertNull("on row null, null", ColisDataRow.getStatus(aRow));
        
        } catch (Exception ex) {
            
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }
    
    @Test
    public void testGetPays() {
        try {
            
            ColisDataRow aRow = new ColisDataRow();
            aRow.setPays("test");
            assertEquals("Country set to \"test\"","test", aRow.getPays());
            
        } catch (Exception ex) {
            
            assertEquals("No exception", 0, ex.getMessage().length());
        }
        
    }
    
    @Test
    public void testGetInformations() {
        try {
            
            ColisDataRow aRow = new ColisDataRow();
            aRow.setInformations("test");
            assertEquals("Information set to \"test\"","test", aRow.getInformations());
            
        } catch (Exception ex) {
            
            assertEquals("No exception", 0, ex.getMessage().length());
        }
        
    }
    
    @Test
    public void testGetDate() {
        try {
            
            ColisDataRow aRow = new ColisDataRow();
            LocalDateTime currentDateTime = LocalDateTime.now();
            aRow.setDate(currentDateTime);
            assertEquals(currentDateTime, aRow.getDate());
            
        } catch (Exception ex) {
            
            assertEquals("No exception", 0, ex.getMessage().length());
        }
        
    }
    
    @Test
    public void testGetCountry() {
        try {
            
            ColisDataRow aRow = new ColisDataRow();
            Country countryTEST = new Country("isoTEST","codeTEST","nameTEST");
            aRow.setCountry(countryTEST);
            assertEquals("Country set to Country(\"isoTEST\",\"codeTEST\",\"nameTEST\")",countryTEST, aRow.getCountry());
            
        } catch (Exception ex) {
            
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

}
