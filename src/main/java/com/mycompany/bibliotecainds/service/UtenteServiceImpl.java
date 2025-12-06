/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.data.Archivio;
import com.mycompany.bibliotecainds.model.Utente;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Giuseppe
 */
public class UtenteServiceImpl implements UtenteService{
    
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

    @Override
    public void rimuoviUtente(Utente utente) throws Exception {
        // Regola di Business Fondamentale: Non cancellare chi ha libri della biblioteca!
        if (!utente.getListaPrestiti().isEmpty()) {
            throw new Exception("Impossibile cancellare l'utente: ha ancora libri in prestito.");
        }
        
        Archivio.getInstance().getRegistroUtenti().remove(utente);
    }

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

    @Override
    public List<Utente> getListaUtenti() {
        return Archivio.getInstance().getRegistroUtenti();
    }
}