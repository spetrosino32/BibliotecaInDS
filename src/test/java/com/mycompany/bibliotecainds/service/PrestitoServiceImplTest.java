package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.data.Archivio;
import com.mycompany.bibliotecainds.model.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifica la corretta gestione dei prestiti
 */
public class PrestitoServiceImplTest {

    private PrestitoServiceImpl prestitoService;
    private Utente utente;
    private Libro libro;

    /** 
     * Pulisce Archivio e crea oggetti validi per i test.
     */
    @BeforeEach
    public void setUp() {
        Archivio.getInstance().getPrestitiAttivi().clear();

        prestitoService = new PrestitoServiceImpl();

        utente = new Utente(
                "Sara",
                "Marotti",
                "0612705522",
                "s.marotti@studenti.unisa.it",
                new ArrayList<>()
        );

        libro = new Libro(
                "Il principe",
                new ArrayList<>(),
                1532,
                "IS00BN",
                2
        );
    }

    /**
     * Verifica il ritorno true, decremento delle copie e prestito aggiunto all'utente e all'archivio
     */
    @Test
    public void testRegistraPrestitoSuccesso() throws Exception {
        boolean risultato = prestitoService.registraPrestito(utente, libro);

        assertTrue(risultato);
        assertEquals(1, libro.getCopieDisponibili());
        assertEquals(1, utente.getListaPrestiti().size());
        assertEquals(1, Archivio.getInstance().getPrestitiAttivi().size());
    }

    /**
     * Deve sollevare un'eccezione.
     */
    @Test
    public void testRegistraPrestitoLibroNonDisponibile() {
        libro.setCopie(0);

        Exception exception = assertThrows(Exception.class, () ->
                prestitoService.registraPrestito(utente, libro)
        );

        assertTrue(exception.getMessage().contains("Libro non disponibile"));
    }

    /**
     * caso in cui l'utente ha già 3 prestiti attivi.
     */
    @Test
    public void testRegistraPrestitoLimiteUtente() {
        utente.aggiungiPrestito(null);
        utente.aggiungiPrestito(null);
        utente.aggiungiPrestito(null);

        Exception exception = assertThrows(Exception.class, () ->
                prestitoService.registraPrestito(utente, libro)
        );

        assertTrue(exception.getMessage().contains("Limite prestiti"));
    }

    /**
     * Verifica l'incremento delle copie e la rimozione del prestito
     */
    @Test
    public void testRestituisciPrestito() throws Exception {
        prestitoService.registraPrestito(utente, libro);
        Prestito prestito = utente.getListaPrestiti().get(0);

        prestitoService.restituisciPrestito(prestito);

        assertEquals(2, libro.getCopieDisponibili());
        assertEquals(0, utente.getListaPrestiti().size());
        assertEquals(0, Archivio.getInstance().getPrestitiAttivi().size());
    }
}