/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.model.Utente;
import java.util.List;
/**
 *
 * @author Giuseppe
 */
public interface UtenteService {
    public void registraUtente(Utente u) throws Exception;
    public void rimuoviUtente(Utente u) throws Exception;
    public List<Utente> cercaUtente(String q);
    public List<Utente> getListaUtenti();
}
