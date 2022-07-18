/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adriens.github.colisnc.colisnc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adriens.github.colisnc.countries.Country;
import com.adriens.github.colisnc.countries.ListCountriesDefinedLanguage;
import com.adriens.github.colisnc.localisation.Localisation;
import com.adriens.github.colisnc.localisation.Localisations;

/**
 *
 * @author 3004SAL
 *         <br>
 *         <p>
 *         <code><b>ColisCrawler</b></code> is the class representing the list
 *         of all rows for a parcel number.
 *         </p>
 *         <u>example:</u>
 * 
 *         <pre>
 *  {@code
 * public void testGetOldestGoodItemId() {
 *      try {
 *
 *          String itemId = "CA107308006SI";
 *          ArrayList<ColisDataRow> lList = ColisCrawler.getColisRows(itemId);
 *          ColisDataRow oldestRow = lList.get(lList.size() - 1);
 *          ColisDataRow result = ColisCrawler.getOldest(itemId);
 *          assertEquals(result.getItemId(), oldestRow.getItemId());
 *
 *      } catch (Exception ex) {
 *          
 *          assertEquals("No exception", 0, ex.getMessage().length());
 *      }
 *  }
 * }
 *         </pre>
 */
public class ColisCrawler {

    /**
     * The base url of the parcel research.
     */
    public static final String BASE_URL = "https://webtracking-nca.ptc.post/IPSWeb_item_events.aspx";// http://webtrack.opt.nc/ipswebtracking/IPSWeb_item_events.asp";

    /**
     * The url parameter of the parcel id.
     */
    public static final String QUERY = "?itemid=";

    /**
     * The message when no rows was found.
     */
    public static final String NO_ROWS_MESSAGE = "Le colis demandé est introuvable...";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(ColisCrawler.class);

    /**
     * Return a list of the latests rows for the parcel number in parameter.
     * 
     * @param colisListe
     *                   A list of parcel number.
     * @return a list of the latests rows for the parcel number in parameter.
     * @throws Exception if there are rows for the parcel number.
     */
    public static final List<ColisDataRow> getLatestStatusForColisList(List<String> colisListe) throws Exception {
        List<ColisDataRow> out = new ArrayList<>();
        if (colisListe == null) {
            logger.debug("liste nulle détectée en entrée");
            return null;
        }
        logger.info("Getting latest status for colisLIste <{}>", colisListe);

        Iterator<String> iterColis = colisListe.iterator();
        String lColisId;
        ColisDataRow lDataRow;

        while (iterColis.hasNext()) {
            lColisId = iterColis.next();
            logger.info("Getting latest status for colis <{}>...", lColisId);
            try {
                lDataRow = ColisCrawler.getLatest(lColisId);
                logger.info("Got <{}> data: {}", lColisId, lDataRow);
                out.add(lDataRow);
            } catch (Exception ex) {
                logger.warn("Not able to fetch colis <{}> : {}", lColisId, ex.getMessage(), ex);
            }

        }

        return out;
    }

    /**
     * Return a list of rows for the parcel number in parameter.
     * 
     * @param itemId
     *               The parcel number, as text.
     * @return each row of the ColisDataRow object for the parcel number in
     *         parameter.
     *
     */
    public static final List<ColisDataRow> getColisRows(String itemId) throws Exception {
        if (itemId == null || itemId.isEmpty()) {
            return Collections.emptyList();
        }

        Document doc = Jsoup.connect(ColisCrawler.BASE_URL + ColisCrawler.QUERY + itemId + "&Submit=Nouvelle+recherche")
                .get();
        if (doc.text().contains(NO_ROWS_MESSAGE)) {
            logger.warn("Le colis demandé <{}> est introuvable...", itemId);
            return Collections.emptyList();
        }

        // get the table
        List<ColisDataRow> result = new ArrayList<>();
        Elements rows = doc.select("table tr");
        logger.debug("Rows found : {}", rows.size());
        for (Element theRow : rows) {
            ColisDataRow colisRow = new ColisDataRow();

            Elements cells = theRow.getElementsByTag("td");
            if (cells.isEmpty()) {
                continue; // header ?
            }
            String rawDateHeure = cells.get(1).text();
            String pays = cells.get(2).text();
            String localisation = cells.get(3).text();
            String typeEvenement = cells.get(4).text();
            String informations = cells.get(5).text();

            LocalDateTime localDateTime = LocalDateTime.parse(rawDateHeure, formatter);
            Localisation geolocalized = Localisations.locate(localisation);

            colisRow.setItemId(itemId);
            colisRow.setRawDateHeure(rawDateHeure);
            colisRow.setPays(pays);
            colisRow.setLocalisation(localisation);
            colisRow.setTypeEvenement(typeEvenement);
            colisRow.setInformations(informations);
            colisRow.setDate(localDateTime);
            colisRow.setStatus();

            Country country = ListCountriesDefinedLanguage.getCountry(pays);
            if (country == null) {
                logger.warn("Code pays introuvable : {}", pays);
            }
            colisRow.setCountry(ListCountriesDefinedLanguage.getCountry(pays));
            
            colisRow.setLocalization(geolocalized);
            result.add(colisRow);

            logger.debug("RAW LINE : <" + theRow.text() + ">");
            logger.info("raw dateHeure : <" + rawDateHeure + ">");
            logger.info("Local DateTime: <" + localDateTime + ">");

            logger.info("pays : <" + pays + ">");
            logger.info("localisation : <" + localisation + ">");
            logger.info("typeEvenement : <" + typeEvenement + ">");
            logger.info("informations : <" + informations + ">");
            logger.info("localization : <" + geolocalized + ">");
            logger.info("---------------------------------------------------");
            // lTransaction = new Transaction(convertFromTextDate(dateAsString), libele,
            // extractSolde(debitAsString), extractSolde(credititAsString));
            // getTransactions().add(lTransaction);
            // logger.debug("Added new transaction : " + lTransaction.toString());

            // logger.debug("End of <" + getTransactions().size() + "> transactions
            // fetching");
        }
        return result;
    }

    /**
     * Return the latest row for the parcel in attibute.
     * 
     * @param itemId
     *               The parcel number, as text.
     * @return the latest row for the parcel in attibute.
     *
     */
    public static final ColisDataRow getLatest(String itemId) throws Exception {
        if (itemId == null) {
            return null;
        }
        if (itemId.length() == 0) {
            return null;
        }
        List<ColisDataRow> lList = ColisCrawler.getColisRows(itemId);

        if (lList.size() == 0) {
            return null;
        }

        return lList.get(0);
    }

    /**
     * Return the oldest row for the parcel in attibute.
     * 
     * @param itemId
     *               The parcel number, as text.
     * @return the oldest row for the parcel in attibute.
     *
     */
    public static final ColisDataRow getOldest(String itemId) throws Exception {
        if (itemId == null) {
            return null;
        }
        if (itemId.length() == 0) {
            return null;
        }
        List<ColisDataRow> lList = ColisCrawler.getColisRows(itemId);

        if (lList.size() == 0) {
            return null;
        }

        return lList.get(lList.size() - 1);
    }

    public static void main(String[] args) {
        try {
            String itemId = "8Z00136833343";
            List<ColisDataRow> coliadDetails = ColisCrawler.getColisRows(itemId);
            System.out.println("Got <" + coliadDetails.size() + "> details pour <" + itemId + ">");
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
