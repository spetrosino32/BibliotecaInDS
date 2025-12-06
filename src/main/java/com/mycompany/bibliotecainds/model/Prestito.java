/**
 * @file Prestito.java
 * @brief Modella un prestito fatto ad un utente
 * 
 * Questa classe rappresenta un prestito effettuato ad un utente 
 * Per ogni prestito vengono memorizzati:
 * - Il libro preso in prestito
 * - La data del prestito
 * - La data di scadenza (30 giorni dopo il prestito)
 * - L'utente che ha effettuato il prestito
 * 
 * Include anche un metodo di verifica della scadenza del prestito
 * 
 * @author Giuseppe, Sara, Vincenzo, Elena
 * @date 6 dicembre 2025
 */
package com.mycompany.bibliotecainds.model;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * @class Prestito
 * @brief Rappresenta il prestito di un libro effettuato ad un utente
 * 
 * Contiene tutte le informazioni relative al prestito di un libro 
 */

public class Prestito implements Serializable{
    private Libro libro; ///< Libro oggetto del prestito
    private LocalDate dataPrestito; ///< Data del prestito
    private LocalDate dataScadenza; ///< Data di scadenza del prestito
    private Utente utente; ///< Utente che ha ricevuto il prestito
    
    /**
     * @brief Costruttore della classe prestito
     * 
     * Inizializza un nuovo prestito impostando automaticamente la data di scadenza
     * 
     * @param libro Libro che è stato prestato
     * @param dataPrestito Data in cui è stato effettuato il prestito
     * @param utente Utente che ha ricevuto il prestito 
     */

    public Prestito(Libro libro, LocalDate dataPrestito, Utente utente) {
        this.libro = libro;
        this.dataPrestito = dataPrestito;
        this.dataScadenza= dataPrestito.plusDays(30);
        this.utente=utente;
    }
    
    /**
     * @brief Restituisce il libro preso in prestito
     * @return Libro preso in prestito
     */
    
    public Libro getLibro(){ return libro; }
    
    /**
     * @brief Restituisce la data in cui è stato effettuato il prestito
     * @return Data del prestito
     */
    
    public LocalDate getDataPrestito(){ return dataPrestito; }
    
    /**
     * @brief Restituisce l'utente che ha chiesto il prestito
     * @return Utente 
     */
    
    public Utente getUtente(){ return utente; }
    
    /**
     * @brief Restituisce la data di scadenza del prestito
     * @return Data di scadenza prestito
     */
    
    public LocalDate getDataScadenza(){return dataScadenza;}
    
    /**
     * @brief Verifica se il prestito è scaduto
     * 
     * Controlla se la data attuale è successiva alla data di scadenza prevista
     * 
     * @return true se il prestito è scaduto, false altrimenti
     */
    
    public boolean isScaduto(){
        return LocalDate.now().isAfter(dataScadenza);
    }
}