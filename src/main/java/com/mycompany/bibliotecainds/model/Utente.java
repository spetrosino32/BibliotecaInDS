/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.model;
//import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Giuseppe
 */
public class Utente {
    private String nome;
    private String cognome;
    private int matricola;
    private String email;
    private List<Prestito> listaPrestiti;
    
    public Utente (String nome, String cognome, int matricola, String email, List<Prestito> listaPrestiti){
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
    public int getMatricola(){
        return matricola;
    }
    public String getEmail(){
        return email;
    }
    public List<Prestito> getListaPrestiti(){
        return listaPrestiti;
    }
}
