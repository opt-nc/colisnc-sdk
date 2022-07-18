/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.adriens.github.colisnc.colisnc.ColisCrawler;
import com.adriens.github.colisnc.colisnc.ColisDataRow;
import com.adriens.github.colisnc.colisnc.ColisDataRow.Status;

/**
 *
 * @author meilie
 *
 */
public class ColisCrawlerTest {

    @Test
    public void testSiteIsAlive() {
        try {
            Document doc = Jsoup.connect(ColisCrawler.BASE_URL).get();
            assertTrue("You can access the page ", doc.text().contains("Suivi de Votre Envoi"));
        } catch (Exception ex) {
            System.err.println("Page inaccessible: " + ex.getMessage());
        }
    }

    @Test
    public void testGetLatestStatusForColisListNullColisListe() {
        try {
            List<ColisDataRow> colisList = ColisCrawler.getLatestStatusForColisList(null);
            assertNull("on null, null, ", colisList);
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetLatestStatusForColisListEmptyColisListe() {
        try {
            List<String> colisListe = new ArrayList<>();
            List<ColisDataRow> colisList = ColisCrawler.getLatestStatusForColisList(colisListe);
            assertTrue("On empty list, no rows", colisList.isEmpty());
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetLatestStatusForColisListOneColisListe() {
        try {
            List<String> colisListe = Arrays.asList(new String[] { "8Z00136833343" });
            List<ColisDataRow> colisList = ColisCrawler.getLatestStatusForColisList(colisListe);
            assertTrue("latest row for colisListe", colisList.size() == 1);
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetLatestStatusForColisListMultiColisListe() {
        try {
            List<String> colisListe = Arrays.asList(new String[] { "8Z00136833343", "8Z00136833343", "8Z00136833343" });
            List<ColisDataRow> colisList = ColisCrawler.getLatestStatusForColisList(colisListe);
            assertTrue("3 latest row for the 3 colisListe", colisList.size() == 3);
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetColisRowsEmptyItemId() {
        try {
            String itemId = "";
            List<ColisDataRow> colisList = ColisCrawler.getColisRows(itemId);
            assertTrue("no rows for empty itemId", colisList.isEmpty());
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetColisRowsNulltemId() {
        try {
            String itemId = null;
            List<ColisDataRow> colisList = ColisCrawler.getColisRows(itemId);
            assertTrue("no rows for null itemId", colisList.isEmpty());
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetColisRowsGoodItemId() {
        try {
            String itemId = "8Z00136833343";
            List<ColisDataRow> colisList = ColisCrawler.getColisRows(itemId);
            assertEquals("on itemId \"8Z00136833343\", there're 7 rows", 9, colisList.size());
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetColisRowsBadItemId() {
        try {
            String itemId = "XX";
            List<ColisDataRow> colisList = ColisCrawler.getColisRows(itemId);
            assertTrue("on bad itemId, empty colisList", colisList.isEmpty());
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetLatestNullItemId() {
        try {
            String itemId = null;
            ColisDataRow colisList = ColisCrawler.getLatest(itemId);
            assertNull("on null, null", colisList);
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetLatestEmptyItemId() {
        try {
            String itemId = "";
            ColisDataRow colisList = ColisCrawler.getLatest(itemId);
            assertNull("on empty itemId, null", colisList);
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetLatestBadItemId() {
        try {
            String itemId = "XX";
            ColisDataRow result = ColisCrawler.getLatest(itemId);
            assertNull("on bad itemId and empty lList, null", result);
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetLatestGoodItemId() {
        try {
            String itemId = "8Z00136833343";
            List<ColisDataRow> lList = ColisCrawler.getColisRows(itemId);
            ColisDataRow latestRow = lList.get(0);
            ColisDataRow result = ColisCrawler.getLatest(itemId);
            assertEquals(result.getItemId(), latestRow.getItemId());
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetOldestNullItemId() {
        try {
            String itemId = null;
            ColisDataRow result = ColisCrawler.getOldest(itemId);
            assertNull("on null itemId, null", result);
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetOldestEmptyItemId() {
        try {
            String itemId = "";
            ColisDataRow result = ColisCrawler.getOldest(itemId);
            assertNull("on empty itemId, null", result);
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetOldestBadItemId() {
        try {
            String itemId = "XX";
            ColisDataRow result = ColisCrawler.getOldest(itemId);
            assertNull("on bas itemId, null", result);
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testGetOldestGoodItemId() {
        try {
            String itemId = "8Z00136833343";
            List<ColisDataRow> lList = ColisCrawler.getColisRows(itemId);
            ColisDataRow oldestRow = lList.get(lList.size() - 1);
            ColisDataRow result = ColisCrawler.getOldest(itemId);
            assertEquals(result.getItemId(), oldestRow.getItemId());
        } catch (Exception ex) {
            assertEquals("No exception", 0, ex.getMessage().length());
        }
    }

    @Test
    public void testUnPeuPlusCouvrant() throws Exception {
        List<ColisDataRow> rows = ColisCrawler.getColisRows("CA107308006SI");

        assertEquals(7, rows.size());

        assertEquals("NC", rows.get(0).getCountry().getCode());
        assertEquals("NCL", rows.get(0).getCountry().getIso());
        assertEquals("nouvelle-caledonie", rows.get(0).getCountry().getName());
        assertEquals(LocalDateTime.parse("2019-09-09T09:41:12"), rows.get(0).getDate());
        assertEquals("", rows.get(0).getInformations());
        assertEquals("CA107308006SI", rows.get(0).getItemId());
        assertEquals("NOUMEA CDC", rows.get(0).getLocalisation());
        assertEquals("", rows.get(0).getLocalization().getLongName());
        assertEquals("", rows.get(0).getLocalization().getName());
        assertEquals("", rows.get(0).getLocalization().getUrl());
        assertEquals("Nouvelle-Calédonie", rows.get(0).getPays());
        assertEquals("09/09/2019 09:41:12", rows.get(0).getRawDateHeure());
        assertEquals(Status.COLIS_LIVRE, rows.get(0).getStatus());
        assertEquals("Votre courrier/colis a été livré", rows.get(0).getTypeEvenement());

        assertEquals("NC", rows.get(1).getCountry().getCode());
        assertEquals("NCL", rows.get(1).getCountry().getIso());
        assertEquals("nouvelle-caledonie", rows.get(1).getCountry().getName());
        assertEquals(LocalDateTime.parse("2019-09-06T15:38:04"), rows.get(1).getDate());
        assertEquals("", rows.get(1).getInformations());
        assertEquals("CA107308006SI", rows.get(1).getItemId());
        assertEquals("NOUMEA-CTP", rows.get(1).getLocalisation());
        assertEquals("", rows.get(1).getLocalization().getLongName());
        assertEquals("", rows.get(1).getLocalization().getName());
        assertEquals("", rows.get(1).getLocalization().getUrl());
        assertEquals("Nouvelle-Calédonie", rows.get(1).getPays());
        assertEquals("06/09/2019 15:38:04", rows.get(1).getRawDateHeure());
        assertEquals(Status.COLIS_EN_COURS_ACHEMINEMENT, rows.get(1).getStatus());
        assertEquals("Votre courrier/colis est en cours d'acheminement.", rows.get(1).getTypeEvenement());

        assertEquals("NC", rows.get(2).getCountry().getCode());
        assertEquals("NCL", rows.get(2).getCountry().getIso());
        assertEquals("nouvelle-caledonie", rows.get(2).getCountry().getName());
        assertEquals(LocalDateTime.parse("2019-09-06T10:43:27"), rows.get(2).getDate());
        assertEquals("", rows.get(2).getInformations());
        assertEquals("CA107308006SI", rows.get(2).getItemId());
        assertEquals("NOUMEA-CTP", rows.get(2).getLocalisation());
        assertEquals("", rows.get(2).getLocalization().getLongName());
        assertEquals("", rows.get(2).getLocalization().getName());
        assertEquals("", rows.get(2).getLocalization().getUrl());
        assertEquals("Nouvelle-Calédonie", rows.get(2).getPays());
        assertEquals("06/09/2019 10:43:27", rows.get(2).getRawDateHeure());
        assertEquals(Status.COLIS_EN_COURS_DEDOUANEMENT, rows.get(2).getStatus());
        assertEquals("Votre courrier/colis est en cours de dédouanement", rows.get(2).getTypeEvenement());

        assertEquals("NC", rows.get(3).getCountry().getCode());
        assertEquals("NCL", rows.get(3).getCountry().getIso());
        assertEquals("nouvelle-caledonie", rows.get(3).getCountry().getName());
        assertEquals(LocalDateTime.parse("2019-09-06T10:43:27"), rows.get(3).getDate());
        assertEquals("", rows.get(3).getInformations());
        assertEquals("CA107308006SI", rows.get(3).getItemId());
        assertEquals("NOUMEA-CTP", rows.get(3).getLocalisation());
        assertEquals("", rows.get(3).getLocalization().getLongName());
        assertEquals("", rows.get(3).getLocalization().getName());
        assertEquals("", rows.get(3).getLocalization().getUrl());
        assertEquals("Nouvelle-Calédonie", rows.get(3).getPays());
        assertEquals("06/09/2019 10:43:27", rows.get(3).getRawDateHeure());
        assertEquals(Status.COLIS_ARRIVE_PAYS_DESTINATION, rows.get(3).getStatus());
        assertEquals("Votre courrier/colis est arrivé dans le pays de destination", rows.get(3).getTypeEvenement());

        assertEquals("FR", rows.get(4).getCountry().getCode());
        assertEquals("FRA", rows.get(4).getCountry().getIso());
        assertEquals("france", rows.get(4).getCountry().getName());
        assertEquals(LocalDateTime.parse("2019-08-20T00:00:00"), rows.get(4).getDate());
        assertEquals("", rows.get(4).getInformations());
        assertEquals("CA107308006SI", rows.get(4).getItemId());
        assertEquals("FRANCE CHILLY MAZARIN", rows.get(4).getLocalisation());
        assertNull(rows.get(4).getLocalization());
        assertEquals("France", rows.get(4).getPays());
        assertEquals("20/08/2019 00:00:00", rows.get(4).getRawDateHeure());
        assertEquals(Status.COLIS_A_QUITTE_PAYS_ORIGINE, rows.get(4).getStatus());
        assertEquals("Votre courrier/colis a quitté le pays d'origine.", rows.get(4).getTypeEvenement());

        assertEquals("SI", rows.get(5).getCountry().getCode());
        assertEquals("SVN", rows.get(5).getCountry().getIso());
        assertEquals("slovenie", rows.get(5).getCountry().getName());
        assertEquals(LocalDateTime.parse("2019-08-09T08:29:00"), rows.get(5).getDate());
        assertEquals("", rows.get(5).getInformations());
        assertEquals("CA107308006SI", rows.get(5).getItemId());
        assertEquals("LJUBLJANA 1003", rows.get(5).getLocalisation());
        assertEquals("", rows.get(5).getLocalization().getLongName());
        assertEquals("", rows.get(5).getLocalization().getName());
        assertEquals("", rows.get(5).getLocalization().getUrl());
        assertEquals("Slovénie", rows.get(5).getPays());
        assertEquals("09/08/2019 08:29:00", rows.get(5).getRawDateHeure());
        assertEquals(Status.COLIS_PRET_A_QUITTE_PAYS_ORIGINE, rows.get(5).getStatus());
        assertEquals("Votre courrier/colis est prêt à quitter son pays d'origine.", rows.get(5).getTypeEvenement());

        assertEquals("SI", rows.get(6).getCountry().getCode());
        assertEquals("SVN", rows.get(6).getCountry().getIso());
        assertEquals("slovenie", rows.get(6).getCountry().getName());
        assertEquals(LocalDateTime.parse("2019-08-06T09:37:00"), rows.get(6).getDate());
        assertEquals("", rows.get(6).getInformations());
        assertEquals("CA107308006SI", rows.get(6).getItemId());
        assertEquals("2116", rows.get(6).getLocalisation());
        assertNull(rows.get(6).getLocalization());
        assertEquals("Slovénie", rows.get(6).getPays());
        assertEquals("06/08/2019 09:37:00", rows.get(6).getRawDateHeure());
        assertEquals(Status.COLIS_PRIS_EN_CHARGE, rows.get(6).getStatus());
        assertEquals("Votre courrier/colis a été pris en charge", rows.get(6).getTypeEvenement());
    }
}
