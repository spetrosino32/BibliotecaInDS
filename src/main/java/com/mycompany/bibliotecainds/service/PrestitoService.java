/**
 * @file PrestitoService.java
 * @brief Interfaccia per la gestione dei prestiti dei libri 
 * 
 * Questa interfaccia definisce le operazioni principali relative alla
 * gestione dei prestiti, tra cui: 
 * - Registrazione di un nuovo prestito
 * - Restituzione di un prestito
 * 
 * Le implementazioni si occupano della logica applicativa, come controllare se
 * il libro è disponibile o se l'utente rispetta i requisiti per effettuare il 
 * prestito. 
 * 
 * @author Giuseppe, Sara, Vincenzo, Elena
 * @date 7 dicembre 2025
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.model.*;

/**
 * @interface PrestitoService
 * @brief Servizio per la gestione dei prestiti
 * 
 * Fornisce i metodi necessari per il prestito e la restituzione dei libri
 */

public interface PrestitoService {
    
    /**
     * @brief Registra un nuovo prestito
     * @param utente Utente a cui viene effettuato il prestito
     * @param libro Libro dato in prestito 
     * @return true se il prestito è stato registrato correttamente, altrimenti false
     * @throws Exception Se il prestito non può essere effettuato 
     *                   (libro già prestato, numero di prestiti massimo raggiunto)
     */
    
    public boolean registraPrestito(Utente utente, Libro libro) throws Exception;
    
    /**
     * @brief Registra la restituzione di un prestito
     * 
     * Questo metodo aggiorna lo stato del libro indicando se è stato restituito
     * @param prestito Prestito da restituire
     */
    
    public void restituisciPrestito(Prestito prestito);
}