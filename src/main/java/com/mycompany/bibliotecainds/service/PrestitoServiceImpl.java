/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.data.Archivio;
import com.mycompany.bibliotecainds.model.*;
import java.time.LocalDate;
/**
 *
 * @author Giuseppe
 */
public class PrestitoServiceImpl implements PrestitoService {

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

    @Override
    public void restituisciPrestito(Prestito p) {
        p.getLibro().incrementaCopie();
        p.getUtente().rimuoviPrestito(p);
        Archivio.getInstance().getPrestitiAttivi().remove(p);
    }
}