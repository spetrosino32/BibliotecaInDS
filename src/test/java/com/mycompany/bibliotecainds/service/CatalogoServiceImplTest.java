package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.model.Libro;
import com.mycompany.bibliotecainds.data.Archivio;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Questo test verifica il corretto funzionamento dei metodi di gestione
 * del catalogo dei libri: aggiunta, rimozione, ricerca e ordinamento.
 */
public class CatalogoServiceImplTest {

    private CatalogoServiceImpl catalogoService;

    /**
     * Pulisce l'archivio in modo che ogni test parta da zero.
     */
    @BeforeEach
    public void setUp() {
        Archivio.getInstance().getCatalogoLibri().clear();
        catalogoService = new CatalogoServiceImpl();
    }

    /**
     * Verifica che il libro venga correttamente inserito.
     */
    @Test
    public void testAggiungiLibro() throws Exception {
        Libro libro = new Libro("IT", Arrays.asList("S. King"), 1986, "nh44lo", 5);
        catalogoService.aggiungiLibro(libro);

        List<Libro> catalogo = catalogoService.getCatalogo();
        assertEquals(1, catalogo.size());
        assertEquals("IT", catalogo.get(0).getTitolo());
    }

    /**
     * Verifica che venga lanciata un'eccezione se si prova ad aggiungere
     * un libro con lo stesso ISBN già presente.
     */
    @Test
    public void testAggiungiLibroDuplicato() throws Exception {
        Libro libro1 = new Libro("Guerra e pace", Arrays.asList("Tolstoj"), 1869, "ss88tt", 5);
        Libro libro2 = new Libro("Orgoglio e pregiudizio", Arrays.asList("Jane Austen"), 1813, "ss88tt", 3); // stesso ISBN

        assertDoesNotThrow(() -> catalogoService.aggiungiLibro(libro1));
        Exception exception = assertThrows(Exception.class, () -> catalogoService.aggiungiLibro(libro2));
        assertTrue(exception.getMessage().contains("Esiste già un libro con questo ISBN"));
    }

    /**
     * Verifica che il libro venga rimosso correttamente.
     */
    @Test
    public void testRimuoviLibro() throws Exception {
        Libro libro = new Libro("Don Chisciotte", Arrays.asList("Miguel de Cervantes"), 1605, "io23kj", 5);
        catalogoService.aggiungiLibro(libro);

        catalogoService.rimuoviLibro(libro);
        assertEquals(0, catalogoService.getCatalogo().size());
    }

    /**
     * Verifica che venga restituito solo il libro che contiene la query.
     */
    @Test
    public void testCercaLibriPerTitolo() throws Exception {
        Libro libro1 = new Libro("Cent'anni di solitudine", Arrays.asList("Gabriel Garcia"), 1967, "we69ew", 5);
        Libro libro2 = new Libro("Decameron", Arrays.asList("Boccaccio"), 1353, "oi86cx", 3);
        catalogoService.aggiungiLibro(libro1);
        catalogoService.aggiungiLibro(libro2);

        List<Libro> risultati = catalogoService.cercaLibri("anni");
        assertEquals(1, risultati.size());
        assertEquals("Cent'anni di solitudine", risultati.get(0).getTitolo());
    }

    /**
     * Deve restituire l'intero catalogo.
     */
    @Test
    public void testCercaLibriVuoto() throws Exception {
        Libro libro1 = new Libro("Frankenstein", Arrays.asList("Mary Shelley"), 1818, "qd55yt", 5);
        catalogoService.aggiungiLibro(libro1);

        List<Libro> risultati = catalogoService.cercaLibri("");
        assertEquals(1, risultati.size());
    }

    /**
     * Verifica che il catalogo restituito sia ordinato alfabeticamente per titolo.
     */
    @Test
    public void testGetCatalogoOrdinato() throws Exception {
        Libro libro1 = new Libro("Cime tempestose", Arrays.asList("Bronte"), 1847, "yg87lk", 5);
        Libro libro2 = new Libro("Il gattopardo", Arrays.asList("Giorgio Bassani"), 1958, "wa98bv", 3);
        catalogoService.aggiungiLibro(libro1);
        catalogoService.aggiungiLibro(libro2);

        List<Libro> catalogo = catalogoService.getCatalogo();
        assertEquals("Cime tempestose", catalogo.get(0).getTitolo()); // ordinamento alfabetico
        assertEquals("Il gattopardo", catalogo.get(1).getTitolo());
    }
}