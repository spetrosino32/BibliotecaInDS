package com.mycompany.bibliotecainds.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrestitoTest {

    private Prestito prestito;
    private Libro libro;
    private Utente utente;
    private LocalDate dataPrestito;

    @BeforeEach
    public void setUp() {
        // --- Creazione Libro ---
        List<String> autori = new ArrayList<>();
        autori.add("J.R.R. Tolkien");

        libro = new Libro( "Il Signore degli Anelli", 
                autori,
                1954,
                "iu85lm",
                5
        );

        // --- Creazione Utente ---
        utente = new Utente(
                "Vincenzo",
                "Menale",
                "0612708636",
                "v.menale1@studenti.unisa.it",
                new ArrayList<>()
        );

        // --- Creazione Prestito ---
        dataPrestito = LocalDate.of(2025, 12, 1);
        prestito = new Prestito(libro, dataPrestito, utente);
    }

    @AfterEach
    public void tearDown() {
        prestito = null;
    }

    /**
     * Test del metodo getLibro()
     */
    @Test
    public void testGetLibro() {
        assertEquals(libro, prestito.getLibro());
    }

    /**
     * Test del metodo getDataPrestito()
     */
    @Test
    public void testGetDataPrestito() {
        assertEquals(dataPrestito, prestito.getDataPrestito());
    }

    /**
     * Test del metodo getUtente()
     */
    @Test
    public void testGetUtente() {
        assertEquals(utente, prestito.getUtente());
    }

    /**
     * Test del metodo getDataScadenza()
     */
    @Test
    public void testGetDataScadenza() {
        LocalDate dataAttesa = dataPrestito.plusDays(30);
        assertEquals(dataAttesa, prestito.getDataScadenza());
    }

    /**
     * Test del metodo isScaduto() - prestito NON scaduto
     */
    @Test
    public void testIsScadutoFalse() {
        assertFalse(prestito.isScaduto());
    }

    /**
     * Test del metodo isScaduto() - prestito SCADUTO
     */
    @Test
    public void testIsScadutoTrue() {
        LocalDate dataVecchia = LocalDate.now().minusDays(40);
        Prestito prestitoScaduto = new Prestito(libro, dataVecchia, utente);

        assertTrue(prestitoScaduto.isScaduto());
    }
}