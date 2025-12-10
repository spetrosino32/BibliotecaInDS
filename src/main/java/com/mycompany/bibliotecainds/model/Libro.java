/*@file Libro.java
 * @brief una classe che rappresenta un libro nel sistema Biblioteca
 * 
 * La classe in questione contiene il titolo, gli autori, 
 * l'anno di pubblicazione, il codice isbn e le copie disponibili.
 * 
 * @author Giuseppe Sara Vincenzo Elena
 *
 * @date 5 dicembre 2025
 */
package com.mycompany.bibliotecainds.model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 *@class Libro
 * 
 * @brief Modello che rappresenta le caratteristiche principali del libro
 * 
 * un oggetto Libro contiene tutti i dati di esso ovvero, titolo, anno di pubblicazione, 
 * codice isbn, il numero di copie presenti nella biblioteca e una lista degli autori.
 *
 */

public class Libro implements Serializable{
    private String titolo; ///< Titolo del libro
    private List<String> autori;///< Lista degli autori
    private int anno;///<Anno di pubblicazione
    private String isbn;///< Codice identificativo unico assegnato ad ogni libro pubblicato.
    private int copie;///< Numero di copie presenti in biblioteca
 
    /**
     * @brief Costruttore della classe utente
     * 
     * Crea un nuovo utente inizializzando i dati personali e la lista dei prestiti.
     * 
     * @param titolo titolo del libro
     * @param autori lista di autori del libro
     * @param anno anno di pubblicazione
     * @param isbn codice isbn del libro
     * @param copie  copie presenti del libro in biblioteca
     */    
    
    public Libro(String titolo, List<String> autori, int anno, String isbn, int copie){
        this.titolo = titolo;
        this.autori=new ArrayList<>(autori);
        this.anno=anno;
        this.isbn=isbn;
        this.copie=copie;
    }
    /**
     * @brief restituisce il titolo del libro
     * @return Titolo
     */
    public String getTitolo() { return titolo; }
    
    /**
     * @brief restituisce la lista degli autori del libro
     * @return lista di autori
     */
    
    public List<String> getAutori(){ return autori;}
    
    /**
     * @brief restituisce l'anno di pubblicazione
     * @return anno di pubblicazione
     */
    
    public int getAnno(){ return anno;}
    
    /**
     * @brief restituisce il codice isbn del libro
     * @return ISBN
     */
    
    public String getIsbn(){return isbn;}
    
    /**
     * @brief restituisce il numero di copie disponibili
     * @return Numero
     */
    
    public int getCopieDisponibili(){ return copie; }
    
    /**
     * @brief Decrementa il numero di copie disponibili
     * 
     * viene utilizzato quando un libro viene preso in prestito
     */
    
    public void decrementaCopie(){copie-=1;}
    
    /**
     * @brief incrementa il numero di copie disponibili
     * 
     * viene utilizzato quando un libro viene restituito o aggiunto al catalogo
     */
    
    public void incrementaCopie(){copie+=1;}
    
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public void setAutori(List<String> autori) { this.autori = autori; }
    public void setAnno(int anno) { this.anno = anno; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setCopie(int copie) { this.copie = copie; }
    
}