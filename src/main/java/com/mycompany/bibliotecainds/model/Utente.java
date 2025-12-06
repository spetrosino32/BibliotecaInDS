/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
/**
 *
 * @author Giuseppe
 */

public class Utente implements Serializable{
    private String nome;
    private String cognome;
    private String matricola;
    private String email;
    private List<Prestito> listaPrestiti;
    
    public Utente (String nome, String cognome, String matricola, String email, List<Prestito> listaPrestiti){
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
        this.listaPrestiti = new ArrayList<>(listaPrestiti);
    }
    
    public String getNome(){
        return nome;
    }
    
    public String getCognome(){
        return cognome;
    }
    
    public String getMatricola(){
        return matricola;
    }
    
    public String getEmail(){
        return email;
    }
    
    public List<Prestito> getListaPrestiti(){
        return listaPrestiti;
    }
    
    public boolean concediPrestito(){
        return listaPrestiti.size()<3;
    }
    
    public void aggiungiPrestito(Prestito p){}
    
    public void rimuoviPrestito(Prestito p){}
    
}