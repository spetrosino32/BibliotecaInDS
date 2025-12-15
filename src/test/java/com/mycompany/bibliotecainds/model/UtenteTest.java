package com.mycompany.bibliotecainds.model;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtenteTest {
    
    private Utente utente;
    private List<Prestito> listaPrestiti;
    
    @BeforeEach
    public void setUp() {
        listaPrestiti = new ArrayList<>();
        utente = new Utente(
                "Giuseppe",
                "Iannone",
                "061270884",
                "g.iannone@studenti.unisa.it",
                listaPrestiti
        );
    }
    /**
     * Test getNome()
     */
    @Test
    public void testGetNome() {
        assertEquals("Giuseppe", utente.getNome());
    }

    /**
     * Test getCognome()
     */
    @Test
    public void testGetCognome() {
        assertEquals("Iannone", utente.getCognome());
    }

    /**
     * Test getMatricola()
     */
    @Test
    public void testGetMatricola() {
        assertEquals("061270884", utente.getMatricola());
    }

    /**
     * Test getEmail()
     */
    @Test
    public void testGetEmail() {
        assertEquals("g.iannone@studenti.unisa.it", utente.getEmail());
    }

    /**
     * Test getListaPrestiti()
     */
    @Test
    public void testGetListaPrestiti() {
        assertNotNull(utente.getListaPrestiti());
        assertEquals(0, utente.getListaPrestiti().size());
    }

    /**
     * Test metodo setNome()
     */
    @Test
    public void testSetNome() {
        utente.setNome("Elena");
        assertEquals("Elena", utente.getNome());
    }

    /**
     * Test metodo setCognome
     */
    @Test
    public void testSetCognome() {
        utente.setCognome("Petrosino");
        assertEquals("Petrosino", utente.getCognome());
    }

    /**
     * Test metodo setMatricola()
     */
    @Test
    public void testSetMatricola() {
        utente.setMatricola("061270895");
        assertEquals("061270895", utente.getMatricola());
    }

    /**
     * Test metodo setEmail().
     */
    @Test
    public void testSetEmail() {
        utente.setEmail("e.petrosino@studenti.unisa.it");
        assertEquals("e.petrosino@studenti.unisa.it", utente.getEmail());
    }

    /**
     * Test metodo concediPrestito()
     */
    @Test
    public void testConcediPrestitoTrue() {
        assertTrue(utente.concediPrestito());
    }
    
    @Test
    public void testConcediPrestitoFalse() {
        utente.aggiungiPrestito(null);
        utente.aggiungiPrestito(null);
        utente.aggiungiPrestito(null);
        
        assertFalse(utente.concediPrestito());
    }
    /**
     * Test metodo aggiungiPrestito()
     */
    @Test
    public void testAggiungiPrestito() {
        Prestito p = null;
        utente.aggiungiPrestito(p);
        
        assertEquals(1, utente.getListaPrestiti().size());
    }

    /**
     * Test metodo rimuoviPrestito()
     */
    @Test
    public void testRimuoviPrestito() {
        Prestito p = null;
        utente.aggiungiPrestito(p);
        utente.rimuoviPrestito(p);
        
        assertEquals(0, utente.getListaPrestiti().size());
    }    
}