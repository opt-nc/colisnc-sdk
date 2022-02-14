/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adriens.github.colisnc.colisnc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.adriens.github.colisnc.countries.ListCountriesDefinedLanguage;
import com.adriens.github.colisnc.localisation.Localisation;
import com.adriens.github.colisnc.localisation.Localisations;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static WebClient buildWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setDownloadImages(false);
        return webClient;
    }

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
        WebClient webClient = buildWebClient();
        ArrayList<ColisDataRow> rows;
        rows = new ArrayList<>();
        if (itemId == null) {
            return rows;
        }
        if (itemId.isEmpty()) {
            return rows;
        }
        HtmlPage rowsPage = webClient
                .getPage(ColisCrawler.BASE_URL + ColisCrawler.QUERY + itemId + "&Submit=Nouvelle+recherche");
        if (rowsPage.asNormalizedText().contains(NO_ROWS_MESSAGE)) {
            logger.warn("Le colis demandé <{}> est introuvable...", itemId);
            return rows;
        }
        // get the table
        HtmlTable rowsTable = (HtmlTable) rowsPage.getElementsByTagName("table").get(0);
        for (final HtmlTableBody body : rowsTable.getBodies()) {
            final List<HtmlTableRow> tableRows = body.getRows();
            logger.debug("Rows found : {}", rows.size());
            // now fetch each row
            Iterator<HtmlTableRow> rowIter = tableRows.iterator();

            while (rowIter.hasNext()) {
                ColisDataRow colisRow = new ColisDataRow();
                HtmlTableRow theRow = rowIter.next();

                String rawDateHeure = theRow.getCell(1).asNormalizedText();
                String pays = theRow.getCell(2).asNormalizedText();
                String localisation = theRow.getCell(3).asNormalizedText();
                String typeEvenement = theRow.getCell(4).asNormalizedText();
                String informations = theRow.getCell(5).asNormalizedText();

                LocalDateTime localDateTime = LocalDateTime.parse(rawDateHeure, formatter);
                Localisation geolocalized = Localisations.locate(localisation);

                colisRow.setItemId(itemId);
                colisRow.setRawDateHeure(rawDateHeure);
                ;
                colisRow.setPays(pays);
                colisRow.setLocalisation(localisation);
                colisRow.setTypeEvenement(typeEvenement);
                colisRow.setInformations(informations);
                colisRow.setDate(localDateTime);
                colisRow.setStatus();
                colisRow.setCountry(ListCountriesDefinedLanguage.getCountry(pays));
                colisRow.setLocalization(geolocalized);
                rows.add(colisRow);

                logger.debug("RAW LINE : <" + theRow.asNormalizedText() + ">");
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
            }
            // logger.debug("End of <" + getTransactions().size() + "> transactions
            // fetching");
        }
        return rows;
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
