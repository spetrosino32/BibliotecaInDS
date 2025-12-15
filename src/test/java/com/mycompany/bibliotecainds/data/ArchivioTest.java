package com.mycompany.bibliotecainds.data;

import com.mycompany.bibliotecainds.model.Libro;
import com.mycompany.bibliotecainds.model.Utente;
import com.mycompany.bibliotecainds.model.Prestito;
import java.io.File;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArchivioTest {

    private static final String TEST_FILE = "archivio_test.ser";

    /**
     * Reset del Singleton prima di ogni test
     */
    @BeforeEach
    public void resetSingleton() throws Exception {
        Field instanceField = Archivio.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    /**
     * Test getInstance()
     */
    @Test
    public void testGetInstance() {
        Archivio a1 = Archivio.getInstance();
        Archivio a2 = Archivio.getInstance();

        assertNotNull(a1);
        assertSame(a1, a2); // stessa istanza
    }

    /**
     * Test inizializzazione catalogo libri
     */
    @Test
    public void testGetCatalogoLibri() {
        Archivio archivio = Archivio.getInstance();

        assertNotNull(archivio.getCatalogoLibri());
        assertTrue(archivio.getCatalogoLibri().isEmpty());
    }

    /**
     * Test inizializzazione registro utenti
     */
    @Test
    public void testGetRegistroUtenti() {
        Archivio archivio = Archivio.getInstance();

        assertNotNull(archivio.getRegistroUtenti());
        assertTrue(archivio.getRegistroUtenti().isEmpty());
    }

    /**
     * Test inizializzazione prestiti attivi
     */
    @Test
    public void testGetPrestitiAttivi() {
        Archivio archivio = Archivio.getInstance();

        assertNotNull(archivio.getPrestitiAttivi());
        assertTrue(archivio.getPrestitiAttivi().isEmpty());
    }

    /**
     * Test salva() e carica()
     */
    @Test
    public void testSalvaCarica() {
        Archivio archivio = Archivio.getInstance();

        // --- dati di test ---
        List<String> autori = new ArrayList<>();
        autori.add("George Orwell");

        Libro libro = new Libro(
                "1984",
                autori,
                1949,
                "yt88uj",
                3
        );

        Utente utente = new Utente(
                "Giuseppe",
                "Petrosino",
                "0612704455",
                "g.petrosino10@studenti.unisa.it",
                new ArrayList<>()
        );

        Prestito prestito = new Prestito(libro, LocalDate.now(), utente);

        archivio.getCatalogoLibri().add(libro);
        archivio.getRegistroUtenti().add(utente);
        archivio.getPrestitiAttivi().add(prestito);

        // --- salvataggio ---
        Archivio.salva(TEST_FILE);

        // reset singleton
        Archivio.carica(TEST_FILE);
        Archivio archivioCaricato = Archivio.getInstance();

        assertEquals(1, archivioCaricato.getCatalogoLibri().size());
        assertEquals(1, archivioCaricato.getRegistroUtenti().size());
        assertEquals(1, archivioCaricato.getPrestitiAttivi().size());

        // pulizia file
        new File(TEST_FILE).delete();
    }
}