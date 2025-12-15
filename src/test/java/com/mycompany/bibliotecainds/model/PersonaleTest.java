package com.mycompany.bibliotecainds.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonaleTest {
    
    private Personale personale;

    public PersonaleTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        personale = new Personale("admin@biblioteca.it", "password123");
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetEmail() {
        String expected = "admin@biblioteca.it";
        assertEquals(expected, personale.getEmail());
    }

    @Test
    public void testGetPassword() {
        String expected = "password123";
        assertEquals(expected, personale.getPassword());
    }
}