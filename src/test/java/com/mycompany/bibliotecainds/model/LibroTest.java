package com.mycompany.bibliotecainds.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibroTest {

    private Libro libro;
    private List<String> autori;

    @BeforeEach
    public void setUp() {
        autori = new ArrayList<>();
        autori.add("A. De Saint-Exupéry");

        libro = new Libro(
                "Il piccolo principe",
                autori,
                1943,
                "fg52ou",
                3
        );
    }

    @AfterEach
    public void tearDown() {
        libro = null;
    }

    /**
     * Test del costruttore e getTitolo()
     */
    @Test
    public void testGetTitolo() {
        assertEquals("Il piccolo principe", libro.getTitolo());
    }

    /**
     * Test getAutori()
     */
    @Test
    public void testGetAutori() {
        assertEquals(autori, libro.getAutori());
    }

    /**
     * Test getAnno()
     */
    @Test
    public void testGetAnno() {
        assertEquals(1943, libro.getAnno());
    }

    /**
     * Test getIsbn()
     */
    @Test
    public void testGetIsbn() {
        assertEquals("fg52ou", libro.getIsbn());
    }

    /**
     * Test getCopieDisponibili()
     */
    @Test
    public void testGetCopieDisponibili() {
        assertEquals(3, libro.getCopieDisponibili());
    }

    /**
     * Test decrementaCopie()
     */
    @Test
    public void testDecrementaCopie() {
        libro.decrementaCopie();
        assertEquals(2, libro.getCopieDisponibili());
    }

    /**
     * Test incrementaCopie()
     */
    @Test
    public void testIncrementaCopie() {
        libro.incrementaCopie();
        assertEquals(4, libro.getCopieDisponibili());
    }

    /**
     * Test setTitolo()
     */
    @Test
    public void testSetTitolo() {
        libro.setTitolo("Animal Farm");
        assertEquals("Animal Farm", libro.getTitolo());
    }

    /**
     * Test setAutori()
     */
    @Test
    public void testSetAutori() {
        List<String> nuoviAutori = new ArrayList<>();
        nuoviAutori.add("Orwell");

        libro.setAutori(nuoviAutori);
        assertEquals(nuoviAutori, libro.getAutori());
    }

    /**
     * Test setAnno()
     */
    @Test
    public void testSetAnno() {
        libro.setAnno(1945);
        assertEquals(1945, libro.getAnno());
    }

    /**
     * Test setIsbn()
     */
    @Test
    public void testSetIsbn() {
        libro.setIsbn("es88ok");
        assertEquals("es88ok", libro.getIsbn());
    }

    /**
     * Test setCopie()
     */
    @Test
    public void testSetCopie() {
        libro.setCopie(10);
        assertEquals(10, libro.getCopieDisponibili());
    }
}