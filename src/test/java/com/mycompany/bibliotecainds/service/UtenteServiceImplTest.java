package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.data.Archivio;
import com.mycompany.bibliotecainds.model.Libro;
import com.mycompany.bibliotecainds.model.Prestito;
import com.mycompany.bibliotecainds.model.Utente;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test UtenteServiceImpl
 */
public class UtenteServiceImplTest {

    private UtenteServiceImpl service;

    @BeforeEach
    public void setUp() {
        service = new UtenteServiceImpl();
        Archivio.getInstance().getRegistroUtenti().clear();
    }

    @AfterEach
    public void tearDown() {
        Archivio.getInstance().getRegistroUtenti().clear();
    }
    /**
     * Test registrazione utente
     */
    @Test
    public void testRegistraUtente_OK() throws Exception {
        Utente u = new Utente(
                "Elena", 
                "Petrosino", 
                "0612707458", 
                "e.petrosino@studenti.unisa.it", 
                new ArrayList<>());
        service.registraUtente(u);

        assertEquals(1, Archivio.getInstance().getRegistroUtenti().size());
        assertTrue(Archivio.getInstance().getRegistroUtenti().contains(u));
    }
    /**
     * Test matricola duplicata
     */
    @Test
    public void testRegistraUtente_MatricolaDuplicata() throws Exception {
        Utente u1 = new Utente(
                "Vincenzo", 
                "Marotti", 
                "0612705236", 
                "v.marotti@studenti.unisa.it", 
                new ArrayList<>());
        Utente u2 = new Utente(
                "Sara", 
                "Menale", 
                "0612705236", 
                "s.menale@studenti.unisa.it", 
                new ArrayList<>());

        service.registraUtente(u1);

        Exception e = assertThrows(Exception.class, () -> service.registraUtente(u2));
        assertTrue(e.getMessage().toLowerCase().contains("matricola"));
    }
    /**
     * Test email duplicata
     */
    @Test
    public void testRegistraUtente_EmailDuplicata() throws Exception {
        Utente u1 = new Utente(
                "Elena", 
                "Menale", 
                "0612702365", 
                "e.menale@studenti.unisa.it", 
                new ArrayList<>());
        Utente u2 = new Utente(
                "Sara", 
                "Petrosino", 
                "0612708569", 
                "e.menale@studenti.unisa.it", 
                new ArrayList<>());

        service.registraUtente(u1);

        Exception e = assertThrows(Exception.class, () -> service.registraUtente(u2));
        assertTrue(e.getMessage().toLowerCase().contains("email"));
    }
    /**
     * Test rimozione utente
     */
    @Test
    public void testRimuoviUtente_OK() throws Exception {
        Utente u = new Utente(
                "Vincenzo", 
                "Iannone", 
                "0612708639", 
                "v.iannone@studenti.unisa.it", 
                new ArrayList<>());
        service.registraUtente(u);
        service.rimuoviUtente(u);

        assertFalse(Archivio.getInstance().getRegistroUtenti().contains(u));
    }
    /**
     * Test rimozione utente con prestiti attivi
     */
    @Test
    public void testRimuoviUtente_ConPrestiti() throws Exception {
        Utente u = new Utente("Giuseppe", "Marotti", "0612705478", "g.marotti@studenti.unisa.it", new ArrayList<>());

        List<String> autori = new ArrayList<>();
        autori.add("Autore Test");

        Libro libro = new Libro("Iliade", autori, 700, "IS85PO", 1);

        Prestito prestito = new Prestito(libro, LocalDate.now(), u);

        u.aggiungiPrestito(prestito);

        service.registraUtente(u);

        Exception e = assertThrows(Exception.class, () -> service.rimuoviUtente(u));
        assertTrue(e.getMessage().toLowerCase().contains("prestito"));
    }
    /**
     * Test cerca utente
     */
    @Test
    public void testCercaUtente() throws Exception {
        Utente u1 = new Utente(
                "Francesco", 
                "Pepe", 
                "0612715632", 
                "f.pepe@studenti.unisa.it", 
                new ArrayList<>());
        Utente u2 = new Utente(
                "Mario", 
                "Manganiello", 
                "0612724521", 
                "m.manganiello@studenti.unisa.it", 
                new ArrayList<>());

        service.registraUtente(u1);
        service.registraUtente(u2);

        List<Utente> risultati = service.cercaUtente("Pepe");

        assertEquals(1, risultati.size());
        assertEquals(u1, risultati.get(0));
    }
    /**
     * Test lista utenti
     */
    @Test
    public void testGetListaUtenti() throws Exception {
        Utente u1 = new Utente(
                "Vincenzo", 
                "Vitolo", 
                "0612718965", 
                "v.vitolo@studenti.unisa.it",
                new ArrayList<>());
        Utente u2 = new Utente(
                "Vincenzo", 
                "Iannone", 
                "0612711111", 
                "v.iannone@studenti.unisa.it", 
                new ArrayList<>());

        service.registraUtente(u1);
        service.registraUtente(u2);

        List<Utente> lista = service.getListaUtenti();

        assertEquals(2, lista.size());
        assertEquals("Iannone", lista.get(0).getCognome());
        assertEquals("Vitolo", lista.get(1).getCognome());
    }
}