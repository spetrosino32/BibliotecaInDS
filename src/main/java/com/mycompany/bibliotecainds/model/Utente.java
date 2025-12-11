/**

 * @file Utente.java

 * @brief Modella un utente della biblioteca

 * 

 * La classe Utente rappresenta un utente della biblioteca e contiene tutti i dati necessari:

 * - Nome 

 * - Cognome

 * - Matricola

 * - Email

 * 

 * Gestisce anche l'elenco dei prestiti dell'utente

 * Ogni utente può avere un massimo di 3 prestiti attivi

 * 

 * @author Giuseppe, Sara, Vincenzo, Elena

 * @date 6 dicembre 2025

 */

package com.mycompany.bibliotecainds.model;



import java.util.List;

import java.util.ArrayList;

import java.io.Serializable;



/**

 * @class Utente

 * @brief Classe che rappresenta un utente della biblioteca

 * 

 * Questa classe contiene i dati personali dell'utente e i metodi per la gestione dei prestiti

 */



public class Utente implements Serializable{

    private String nome; ///< Nome dell'utente

    private String cognome; ///< Cognome dell'utente

    private String matricola; ///< Matricola dell'utente

    private String email; ///< Indirizzo email dell'utente

    private List<Prestito> listaPrestiti; ///< Lista dei prestiti del'utente

    

    /**

     * @brief Costruttore della classe utente

     * Inizializza un nuovo utente con tutti i suoi dati e la lista dei prestiti

     * 

     * @param nome Nome dell'utente

     * @param cognome Cognome dell'utente

     * @param matricola Matricola dell'utente

     * @param email Email dell'utente

     * @param listaPrestiti  Lista dei prestiti dell'utente

     */

    

    public Utente (String nome, String cognome, String matricola, String email, List<Prestito> listaPrestiti){

        this.nome = nome;

        this.cognome = cognome;

        this.matricola = matricola;

        this.email = email;

        this.listaPrestiti = new ArrayList<>(listaPrestiti);

    }

    

    /**

     * @brief Restituisce il nome dell'utente

     * @return Nome dell'utente

     */

    

    public String getNome(){

        return nome;

    }

    

    /**

     * @brief Restituisce il cognome dell'utente

     * @return Cognome dell'utente

     */

    

    public String getCognome(){

        return cognome;

    }

    

    /**

     * @brief Restituisce la matricola dell'utente

     * @return Matricola dell'utente

     */

    

    public String getMatricola(){

        return matricola;

    }

    

    /**

     * @brief Restituisce l'email dell'utente

     * @return Email dello utente

     */

    

    public String getEmail(){

        return email;

    }

    

    /**

     * @brief Restituisce la lista dei prestiti dell'utente

     * @return Lista prestiti dell'utente

     */

    

    public List<Prestito> getListaPrestiti(){

        return listaPrestiti;

    }

    /**
     * @brief metodo setter per reimpostare il nome di un utente
     * 
     * viene utilizzato quando c'è necessità di modificare un utente dell'elenco
     * 
     * @param nome 
     */
    public void setNome(String nome) { this.nome = nome; }
    
    /**
     * @brief metodo setter per reimpostare il cognome di un utente
     * 
     * viene utilizzato quando c'è necessità di modificare un utente dell'elenco
     * 
     * @param cognome 
     */
    public void setCognome(String cognome) { this.cognome = cognome; }
    
     /**
     * @brief metodo setter per reimpostare la matricola di un utente
     * 
     * viene utilizzato quando c'è necessità di modificare un utente dell'elenco
     * 
     * @param matricola 
     */
    public void setMatricola(String matricola) { this.matricola = matricola; }
    
     /**
     * @brief metodo setter per reimpostare la mail di un utente
     * 
     * viene utilizzato quando c'è necessità di modificare un utente dell'elenco
     * 
     * @param email 
     */
    public void setEmail(String email) { this.email = email; }

    /**

     * @brief Verifica se l'utente può ricevere un prestito

     * @return true se il numero dei prestiti attivi è minore di 3, false altrimenti

     */

    

    public boolean concediPrestito(){

        return listaPrestiti.size()<3;

    }

    

    /**

     * @brief Aggiunge un prestito alla lista prestiti dell'utente

     * @param p Prestito da aggiungere

     */

    

    public void aggiungiPrestito(Prestito p){ listaPrestiti.add(p); }

    

    /**

     * @brief Rimuove un prestito dalla lista prestiti dell'utente

     * @param p Prestito da rimuovere

     */

    

    public void rimuoviPrestito(Prestito p){ listaPrestiti.remove(p); }



}
