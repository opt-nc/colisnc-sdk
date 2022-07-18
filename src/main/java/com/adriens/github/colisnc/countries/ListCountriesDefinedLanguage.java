/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adriens.github.colisnc.countries;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 3004SAL
 *         <br>
 *         <p>
 *         <code><b>ListCountriesDefinedLanguage</b></code> is the class
 *         representing the list of all
 *         countries in french.
 *         </p>
 *         <u>example:</u>
 * 
 *         <pre>
 *  {@code
 * public void main(String... args) {
 *
 *      // Create a collection of all available countries
 *      Country theCountry = ListCountriesDefinedLanguage.getCountry("ÉTATS-UNIS");
 *      System.out.println("trouvé:\n" + theCountry);
 *      System.exit(0);
 *  }
 * }
 *         </pre>
 *
 */
public class ListCountriesDefinedLanguage {

    static final Logger logger = LoggerFactory.getLogger(ListCountriesDefinedLanguage.class);

    private static List<Country> countries;

    /**
     * Return a list of all Country objects in french
     *
     * @return a list of all Country objects
     */
    public static List<Country> getCountries() {
        if (countries == null) {
            countries = new ArrayList<>();
            // Map ISO countries to custom country object
            String[] countryCodes = Locale.getISOCountries();
            for (String countryCode : countryCodes) {

                Locale locale = new Locale("", countryCode);
                String iso = locale.getISO3Country();
                String code = locale.getCountry();
                String name = locale.getDisplayCountry(Locale.FRANCE).toLowerCase();
                name = normalize(name);

                countries.add(new Country(iso, code, name));
            }

            // Sort countries
            Collections.sort(countries);

            // print total number of countries
            logger.debug("found {} countries : {}", countries.size(), countries);
        }

        return countries;
    }

    /**
     * Return a Country object that match the aCountryName argument.
     *
     * @param name the name of the Country to return.
     * @return a Country object.
     */
    public static Country getCountry(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        String normalized = normalize(name);

        Country cc=
         getCountries().stream().filter(c -> c.getName().equals(normalized)).findFirst().orElse(null);
         if (cc==null) {
            logger.warn("Code pays introuvable : {}", normalized);
         }
         return cc;
    }

    private static String normalize(String text) {
        return Normalizer.normalize(text.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }
}
