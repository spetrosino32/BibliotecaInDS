/*
 * @file UtenteService.java
 * @brief Interfaccia per la gestione degli utenti nel sistema Biblioteca
 *
 * Questa interfaccia definisce le operazioni principali relative agli utenti,
 * permettendone la registrazione, la rimozione e la ricerca.
 * Le implementazioni gestiranno la logica di validazione dati, 
 * il controllo duplicati e la gestione dell'archivio utenti.
 *
 * Responsabilità principali:
 * - Inserimento di un nuovo utente nel sistema
 * - Eliminazione di un utente registrato
 * - Ricerca per nome/parola chiave
 * - Accesso alla lista completa degli utenti
 * 
 * @author Giuseppe Sara Vincenzo Elena
 * @date 08 dicembre 2025
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.model.Utente;
import java.util.List;
/**
 * @interface UtenteService
 * @brief Definisce le operazioni base per la gestione degli utenti
 * 
 * Le classi che implementano questa interfaccia si occuperanno delle operazioni
 * di persistenza, controllo e manipolazione dei dati utente
 */
public interface UtenteService {
    /**
     * @brief Registra un nuovo utente nel sistema.
     * 
     * @param u Utente da aggiungere.
     * @throws Exception Se l’utente è già presente o i dati non risultano validi.
     */
    public void registraUtente(Utente u) throws Exception;
    /**
     * @brief Rimuove un utente registrato.
     * 
     * @param u Utente da eliminare.
     * @throws Exception Se l’utente non è presente o non può essere rimosso.
     */
    public void rimuoviUtente(Utente u) throws Exception;
    /**
     * @brief Cerca utenti tramite parola chiave.
     * 
     * Può essere utilizzata per ricerca tramite nome, cognome 
     * o altri attributi implementati nella logica di ricerca.
     * 
     * @param q Query o stringa da confrontare.
     * @return Lista di utenti corrispondenti al criterio fornito.
     */
    public List<Utente> cercaUtente(String q);
    /**
     * @brief Restituisce la lista di tutti gli utenti registrati.
     * 
     * @return Lista completa degli utenti presenti nel sistema.
     */
    public List<Utente> getListaUtenti();
}
