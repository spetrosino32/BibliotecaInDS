/**
 * @file CatalogoServiceImpl.java
 * @brief Implementazione del servizio per la gestione del catalogo libri.
 * 
 * Questa classe implementa l'interfaccia CatalogoService e fornisce le 
 * funzionalità per:
 * - Aggiungere un libro
 * - Rimuovere un libro
 * - Cercare un libro nel catalogo
 * - Ottenere l'intero catalogo
 * 
 * I dati vengono gestiti dalla classe Archivio, che funge da contenitore
 * centrale (pattern Singleton).
 * 
 * @author Giuseppe, Sara, Vincenzo, Elena
 * @date 7 dicembre 2025
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.data.Archivio;
import com.mycompany.bibliotecainds.model.Libro;
import com.mycompany.bibliotecainds.model.Prestito;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @class CatalogoServiceImpl
 * @brief Implementazione dell'interfaccia CatalogoService.
 * 
 * Questa classe si occupa della logica di gestione del catalogo dei libri.
 * Include controlli su ISBN duplicati, rimozione di libri e ricerca tramite
 * titolo, codice ISBN o autore.
 */
public class CatalogoServiceImpl implements CatalogoService {
        
    /**
     * @brief Aggiunge un nuovo libro al catalogo.
     * 
     * Verifica che non esista già un libro con lo stesso codice ISBN.
     * 
     * @param nuovoLibro Libro da aggiungere.
     * @throws Exception Se esiste già un libro con lo stesso ISBN.
     */
    @Override
    public void aggiungiLibro(Libro nuovoLibro) throws Exception {
        List<Libro> catalogo = Archivio.getInstance().getCatalogoLibri();
    
        for (Libro l : catalogo) {
        // Controllo ISBN Duplicato
            if (l.getIsbn().equalsIgnoreCase(nuovoLibro.getIsbn())) {
                throw new Exception("Errore: Esiste già un libro con ISBN " + nuovoLibro.getIsbn());
            }
        }
        
        int annoCorrente = java.time.LocalDate.now().getYear();

        if (nuovoLibro.getAnno() > annoCorrente) {
            throw new Exception("Errore: L'anno di pubblicazione non può essere nel futuro.");
        }
        
        if (nuovoLibro.getAnno() < 0) { // O altro limite sensato
            throw new Exception("Errore: Anno di pubblicazione non valido.");
        }
    
        catalogo.add(nuovoLibro);
}
    
    /**
     * @brief Rimuove un libro dal catalogo.
     * 
     * @param libro Libro da rimuovere.
     * @throws Exception Se il libro non può essere rimosso.
     */
    @Override
    public void rimuoviLibro(Libro libro) throws Exception {
    // Recuperiamo la lista dei prestiti attivi
    List<Prestito> prestiti = Archivio.getInstance().getPrestitiAttivi();
    
    // Controlliamo se QUESTO libro (tramite ISBN) è attualmente prestato a qualcuno
    for (Prestito p : prestiti) {
        if (p.getLibro().getIsbn().equals(libro.getIsbn())) {
            throw new Exception("Impossibile eliminare: il libro è attualmente in prestito a " + p.getUtente().getCognome());
        }
    }
    
    // Se nessuno lo ha in prestito, lo cancelliamo
    Archivio.getInstance().getCatalogoLibri().remove(libro);
}
    
    /**
     * @brief Cerca libri nel catalogo in base a una stringa.
     * 
     * La ricerca avviene su:
     * - Titolo
     * - Nome degli autori
     * - Codice ISBN
     * 
     * Se la query è vuota o nulla, restituisce l'intero catalogo.
     *
     * @param query La stringa da cercare (titolo, ISBN, autore).
     * @return Lista di libri che corrispondono ai criteri di ricerca.
     */
    @Override
    public List<Libro> cercaLibri(String query) {
        if (query == null || query.isEmpty()) {
            return getCatalogo();
        }
        
        String lowerQuery = query.toLowerCase();
        
        return Archivio.getInstance().getCatalogoLibri().stream()
                .filter(l -> l.getTitolo().toLowerCase().contains(lowerQuery) || 
                             l.getIsbn().toLowerCase().contains(lowerQuery) ||
                             l.getAutori().toString().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
    
    /**
     * @brief Restituisce l'intero catalogo di libri.
     * 
     * @return Lista che contiene tutti i libri in archivio in ordine lessicografico.
     */
    @Override
    public List<Libro> getCatalogo(){
        List<Libro> libri = Archivio.getInstance().getCatalogoLibri();
        libri.sort((l1, l2) -> l1.getTitolo().compareToIgnoreCase(l2.getTitolo()));
        return libri;
    }
    
    /**
     * @brief Aggiorna i dati di un libro esistente con controllo sui duplicati.
     * Aggiorna titolo, autori, anno, copie e ISBN. Verifica che il nuovo ISBN
     * non vada in conflitto con altri libri già presenti nel catalogo.
     * @param libroEsistente Il libro oggetto della modifica.
     * @param nuovoTitolo Il nuovo titolo.
     * @param nuoviAutori La nuova lista di autori.
     * @param nuovoAnno Il nuovo anno di pubblicazione.
     * @param nuovoIsbn Il nuovo codice ISBN.
     * @param nuoveCopie Il nuovo numero di copie totali.
     * @throws Exception Se il nuovo ISBN è già assegnato ad un altro libro.
     */
    @Override
    public void aggiornaLibro(Libro libroEsistente, String nuovoTitolo, List<String> nuoviAutori, int nuovoAnno, String nuovoIsbn, int nuoveCopie) throws Exception {
        List<Libro> catalogo = Archivio.getInstance().getCatalogoLibri();

        for (Libro l : catalogo) {
            // SALTA SE STESSO
            if (l == libroEsistente) continue;

            // Controlla conflitti con GLI ALTRI
            if (l.getIsbn().equalsIgnoreCase(nuovoIsbn)) {
                throw new Exception("Errore: ISBN già assegnato ad un altro libro.");
            }
        }

        // APPLICO LE MODIFICHE
        libroEsistente.setTitolo(nuovoTitolo);
        libroEsistente.setAutori(nuoviAutori);
        libroEsistente.setAnno(nuovoAnno);
        libroEsistente.setIsbn(nuovoIsbn);
        libroEsistente.setCopie(nuoveCopie);
    }
}