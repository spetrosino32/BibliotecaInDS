/*
 * @file UtenteServiceImpl.java
 * @briefImplementazione dell'interfaccia per la gestione degli utenti.
 * 
 * Questa classe implementa l'interfaccia UtenteService e gestisce le operazioni
 * principali sugli utenti come:
 * - registrazione di un nuovo utente con controllo univocità di matricola ed email
 * - rimozione di un utente previo controllo dei prestiti attivi
 * - ricerca tramite parola chiave
 * - accesso alla lista completa degli utenti salvati
 * 
 * I dati vengono recuperati e mantenuti tramite la classe Archivio, che centralizza
 * le informazioni dell'applicazione.
 * 
 * @author Giuseppe Sara Vincenzo Elena
 * @date 08 dicembre 2025
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.data.Archivio;
import com.mycompany.bibliotecainds.model.Utente;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @class UtenteServiceImpl
 * @brief Gestione completa degli utenti registrati.
 * 
 * Si occupa della registrazione e della rimozione degli utenti, con controlli di
 * validità e vincoli di business (come impedire la rimozione se sono presenti prestiti attivi).
 */

public class UtenteServiceImpl implements UtenteService{
 
    /*
     * @brief Registra un nuovo utente nel sistema.
     * 
     * Controlla che matricola ed email non siano già associate ad altri utenti. 
     * In caso contrario il sistema blocca l'operazione e avvisa tramite un errore.
     * 
     * @param nuovoUtente Utente da inserire nel registro.
     */
    
    @Override
    public void registraUtente(Utente nuovoUtente) throws Exception {
        List<Utente> utenti = Archivio.getInstance().getRegistroUtenti();
        
        for (Utente u : utenti) {
            if (u.getMatricola() == nuovoUtente.getMatricola()) {
                throw new Exception("Matricola già presente nel sistema.");
            }
            if (u.getEmail().equalsIgnoreCase(nuovoUtente.getEmail())) {
                throw new Exception("Email già presente nel sistema.");
            }
        }
        
        utenti.add(nuovoUtente);
    }
    /**
     * @brief Rimuove un utente registrato.
     * 
     * Un utente non può essere eliminato se ha prestiti ancora attivi.
     * 
     * @param utente Utente da eliminare.
     */
    @Override
    public void rimuoviUtente(Utente utente) throws Exception {
        // Regola di Business Fondamentale: Non cancellare chi ha libri della biblioteca!
        if (!utente.getListaPrestiti().isEmpty()) {
            throw new Exception("Impossibile cancellare l'utente: ha ancora libri in prestito.");
        }
        
        Archivio.getInstance().getRegistroUtenti().remove(utente);
    }
    /**
     * @brief Ricerca utenti tramite testo libero.
     * 
     * La query viene confrontata con nome, cognome o matricola. 
     * Se la stringa è vuota o nulla, viene restituita l'intera lista degli utenti.
     * 
     * @param q Testo da utilizzare per la ricerca.
     * @return Lista filtrata di utenti che corrispondono al criterio.
     */
    @Override
    public List<Utente> cercaUtente(String q) {
        if (q == null || q.isEmpty()) {
            return getListaUtenti();
        }
        
        String lowerQuery = q.toLowerCase();
        
        return Archivio.getInstance().getRegistroUtenti().stream()
                .filter(u -> u.getCognome().toLowerCase().contains(lowerQuery) ||
                             u.getNome().toLowerCase().contains(lowerQuery) ||
                             String.valueOf(u.getMatricola()).contains(lowerQuery))
                .collect(Collectors.toList());
    }
    /**
     * @brief Restituisce l'elenco completo degli utenti.
     * 
     * @return Lista attuale degli utenti registrati.
     */
    @Override
    public List<Utente> getListaUtenti() {
        return Archivio.getInstance().getRegistroUtenti();
    }
}