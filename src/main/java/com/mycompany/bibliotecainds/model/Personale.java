/**
 * @file Personale.java
 * 
 * @brief Rappresenta un membro del personale della biblioteca
 * 
 * La classe Personale contiene le informazioni base relative al personale della biblioteca,
 * come ad esempio:
 * - Mail
 * - Password
 * Mette a disposizione i metodi per la gestione degli utenti e del catalogo
 * @author Giuseppe, Sara, Vincenzo, Elena
 * @date 6 dicembre 2025
 */
package com.mycompany.bibliotecainds.model;

import java.io.Serializable;
/**
 *@class Personale
 * @brief Classe che modella un membro del personale
 * 
 * Questa classe rappresenta un dipendente della biblioteca.
 * Implementa Serializable per consentire il salvataggio su file
 */
public class Personale implements Serializable {
    private String email; ///< Email del membro del personale
    private String password; ///< Password del membro del personale
    
    /**
     * @brief Costruttore della classe Personale
     * 
     * Inizializza un nuovo membro del personale con email e password
     * @param email Email del personale
     * @param password  Password del personale
     */
    
    public Personale(String email, String password){
        this.email=email;
        this.password=password;
    }
    
    /**
     * @brief Restituisce l'email del personale
     * @return Email del personale
     */
    
    public String getEmail(){ return email;}
    
    /**
     * @brief Restituisce la password del personale
     * @return Password del personale
     */
    
    public String getPassword(){return password;}
    
}