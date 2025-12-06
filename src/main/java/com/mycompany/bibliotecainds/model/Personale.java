/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.model;

import java.io.Serializable;
/**
 *
 * @author Giuseppe
 */
public class Personale implements Serializable {
    private String email;
    private String password;
    
    public Personale(String email, String password){
        this.email=email;
        this.password=password;
    }
    
    public String getEmail(){ return email;}
    
    public String getPassword(){return password;}
    
    public void modificaCatalogo(){}
    
    public void gestisciUtenti(){}
}
