/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.model;

import java.time.LocalDate;
/**
 *
 */

public class Prestito {
    private Libro libro;
    private LocalDate dataPrestito;
    private LocalDate dataScadenza;
    private Utente utente;

    public Prestito(Libro libro, LocalDate dataPrestito, Utente utente) {
        this.libro = libro;
        this.dataPrestito = dataPrestito;
        this.dataScadenza= dataPrestito.plusDays(30);
        this.utente=utente;
    }
    
    public Libro getLibro(){
        return libro;
    }
    public LocalDate getDataPrestito(){
        return dataPrestito;
    }
    
    public boolean isScaduto(){
        return LocalDate.now().isAfter(dataScadenza);
    }
    
    public Utente getUtente(){ return utente; }
    
    public LocalDate getDataScadenza(){return dataScadenza;}
    
}