/*
 * @file PrestitoServiceImpl.java
 * @brief Implementazione del servizio di gestione prestiti.
 *
 * Questa classe implementa l'interfaccia PrestitoService e fornisce le funzionalità
 * principali per:
 * - Regisrazione di un nuovo prestito se il libro risulta disponibile e l'utente ha i requisiti giusti
 * - Aggiornamento copie disponibili e associazione del prestito all'utente
 * - Rimozione del prestito e aggiornamento delle copie alla restituzione
 *
 * Utilizza il pattern Singleton tramite la classe Archivio per la gestione centralizzata
 * dei prestiti attivi.
 *
 * @author Giuseppe Sara Vincenzo Elena
 * @date 8 dicembre 2025
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.data.Archivio;
import com.mycompany.bibliotecainds.model.*;
import java.time.LocalDate;
/**
 *
 * @class PrestitoServiceImpl
 * @brief Implementazione dell'interfaccia PrestitoService
 * 
 * Questa classe si occupa di gestire delle operazioni di prestito e restituzione 
 * dei libri interagendo con Archivio, Libro e Utente.
 */
public class PrestitoServiceImpl implements PrestitoService {

/**
 *
 * @brief Registra un nuovo prestito
 * 
 * Controlla la disponibilità del libro e se l'utente può prendere nuovi prestiti.
 * Se si, crea un nuovo oggetto Prestito, aggiorna copie disponibili e registra
 * il prestito nell'archivio centrale
 * 
 * @param utente Utente che richiede il prestito
 * @param libro Libro da prestare
 * @return true se il prestito viene registrato correttamente
 * @throws Exception Se il libro non è disponibile o l'utente ha raggiunto il limite prestiti
 */    

    @Override
    public boolean registraPrestito(Utente utente, Libro libro) throws Exception {
        // Logica spostata dal Controller a qui
        if (libro.getCopieDisponibili()<=0) {
            throw new Exception("Libro non disponibile");
        }
        if (!utente.concediPrestito()) {
            throw new Exception("Limite prestiti raggiunto per l'utente");
        }

        Prestito p = new Prestito(libro, LocalDate.now(), utente);
        libro.decrementaCopie();
        utente.aggiungiPrestito(p);
        
        Archivio.getInstance().getPrestitiAttivi().add(p);
        
        return true;
    }
/**
 *
 * @brief Registra la restituzione di un prestito
 * 
 * Ripristina una copia disponibile del libro e rimuove il prestito 
 * sia dall'utente che dell'archivio dei prestiti attivi
 * 
 * @param p Prestito da restituire
 */
    @Override
    public void restituisciPrestito(Prestito p) {
        p.getLibro().incrementaCopie();
        p.getUtente().rimuoviPrestito(p);
        Archivio.getInstance().getPrestitiAttivi().remove(p);
    }
}