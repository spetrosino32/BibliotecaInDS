/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.model;

import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Giuseppe
 */

public class Libro {
    private String titolo;
    private List<String> autori;
    private int anno;
    private String isbn;
    private int copie;
    
    public Libro(String titolo, List<String> autori, int anno, String isbn, int copie){
        this.titolo = titolo;
        this.autori=new ArrayList<>(autori);
        this.anno=anno;
        this.isbn=isbn;
        this.copie=copie;
    }
    
    public String getTitolo() { return titolo; }
    
    public List<String> getAutori(){ return autori;}
    
    public int getAnno(){ return anno;}
    
    public String getIsbn(){return isbn;}
    
    public int getCopieDisponibili(){ return copie; }
    
    public void decrementaCopie(){}
    
    public void incrementaCopie(){}
    
}