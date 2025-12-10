/**
 * @file Archivio.java
 * @brief Contiene l'implementazione della classe archivio della biblioteca
 * 
 * Questa classe funge da contenitore centrale di tutti i dati del sistema:
 * Catalogo dei libri, registro utenti e prestiti attivi. 
 * 
 * Fornisce anche i metodi per caricare e salvare l'intero archivio su file tramite serializzazione.
 * 
 * @author Giuseppe, Sara, Vincenzo, Elena
 * @date 5 dicembre 2025
 */
package com.mycompany.bibliotecainds.data;

import com.mycompany.bibliotecainds.model.Libro;
import com.mycompany.bibliotecainds.model.Utente;
import com.mycompany.bibliotecainds.model.Prestito;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @class Archivio
 * @brief Archivio centrale dei dati della biblioteca
 * 
 * Questa classe mantiene:
 * -Il catalogo di libri disponibili
 * -Il registro degli utenti registrati
 * -L'elenco dei prestiti attivi
 */

public class Archivio implements Serializable {
    private static final long serialVersionUID = 1L; ///< Versione della serializzazione
    private List<Libro> catalogoLibri; ///< Lista dei libri presenti nel catalogo
    private List<Utente> registroUtenti; ///< Lista degli utenti registrati
    private List<Prestito> prestitiAttivi; ///< Lista dei prestiti attivi
    private static Archivio instance; ///< Istanza unica
    
    /**
     * @brief Costruttore privato
     * 
     * Inizializza le liste che compongono l'archivio
     */

    private Archivio() {
        catalogoLibri = new ArrayList<>(); 
        registroUtenti = new ArrayList<>();
        prestitiAttivi = new ArrayList<>();
    }
    
    /**
     * @brief Restituisce l'unica istanza dell'archivio
     * 
     * Se l'istanza ancora non esiste essa viene creata
     * @return Istanza unica della classe Archivio
     */

    public static Archivio getInstance() {
        if (instance == null) {
            instance = new Archivio();
        }
        return instance;
    }
    
    /**
     * @brief Restituisce il catalogo dei libri
     * @return Lista dei libri presenti nell'archivio
     */

    public List<Libro> getCatalogoLibri() { return catalogoLibri; }
    
    /**
     * @brief Restituisce il registro degli utenti
     * @return Lista degli utenti registrati
     */
    
    public List<Utente> getRegistroUtenti() { return registroUtenti; }
    
    /**
     * @brief Restituisce la lista di prestiti già attivi 
     * @return Lista di prestiti già attivi 
     */
    
    public List<Prestito> getPrestitiAttivi() { return prestitiAttivi; }
    
    /**
     * @brief Salva l'intero archivio su file tramite serializzazione
     * @param filePath Percorso del file in cui salvare i dati
     */
    
    public static void salva(String filePath) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
        oos.writeObject(getInstance());
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    /**
     * @brief Carica l'intero archivio da un file se esiste
     * @param filePath Percorso del file da cui caricare l'archivio
     */

    public static void carica(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                instance = (Archivio) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}