/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.data;

import com.mycompany.bibliotecainds.model.Libro;
import com.mycompany.bibliotecainds.model.Utente;
import com.mycompany.bibliotecainds.model.Prestito;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Archivio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Libro> catalogoLibri;
    private List<Utente> registroUtenti;

    private List<Prestito> prestitiAttivi; 

    private static Archivio instance;

    private Archivio() {
        catalogoLibri = new ArrayList<>();
        registroUtenti = new ArrayList<>();
        prestitiAttivi = new ArrayList<>();
    }

    public static Archivio getInstanza() {
        if (instance == null) {
            instance = new Archivio();
        }
        return instance;
    }

    public List<Libro> getCatalogoLibri() { return catalogoLibri; }
    public List<Utente> getRegistroUtenti() { return registroUtenti; }
    public List<Prestito> getPrestitiAttivi() { return prestitiAttivi; }

    
    public static void salva(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(getInstanza());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void carica(String filePath) {
    }
}
//ciao cenzo