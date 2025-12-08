/**
 * @file CatalogoService.java
 * @brief Interfaccia per la gestione del catalogo libri
 * 
 * Questa interfaccia definisce le operazioni principali per la gestione del catalogo
 * della biblioteca, tra cui:
 * - Aggiunta libro
 * - Rimozione libro
 * - Ricerca libro
 * 
 * @author Giuseppe, Sara, Vincenzo, Elene
 * @date 6 dicembre 2025
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.model.Libro;
import java.util.List;

/**
 * @interface CatalogoService
 * @brief Servizio per la gestione del catalogo
 * Questa interfaccia fornisce i metodi per aggiungere, rimuovere e
 * cercare libri nel catalogo della biblioteca.
 */

public interface CatalogoService {
    
    /**
     * @brief Aggiunge un libro al catalogo
     * @param libro Libro da aggiungere
     * @throws Exception Se il libro non può essere aggiunto, ad esempio se è già presente nel catalogo
     * oppure si verifica un errore di salvataggio
     */
    public void aggiungiLibro(Libro libro) throws Exception;
    
    /**
     * @brief Rimuove un libro dal catalogo
     * @param libro Libro da rimuovere
     * @throws Exception Se il libro non può essere rimosso, ad esempio se non c'è nel catalogo
     */
    public void rimuoviLibro(Libro libro) throws Exception;
    
    /**
     * @brief Cerca un libro nel catalogo in base ad una stringa
     * 
     * Esegue la ricerca del libro in base al nome dell'autore, al titolo 
     * o ad esempio tramite il codice ISBN
     * @param l La stringa di ricerca
     * @return Una lista di libri corrispondenti ai criteri di ricerca
     */
    public List<Libro> cercaLibri(String l);
    
    /**
     * @brief Restituisce l'intero catalogo di libri 
     * @return Una lista contenente tutti i libri nel catalogo
     */
    public List<Libro> getCatalogo();
    
}