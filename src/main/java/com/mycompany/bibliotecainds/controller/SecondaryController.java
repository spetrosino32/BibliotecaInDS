/**
 * @file SecondaryController.java
 * @brief Controller della schermata secondaria dell'app
 * 
 * Questo controller gestisce la schermata secondaria dell'app, e permette di tornare 
 * a quella principale tramite una determinata azione effettuata dall'utente
 * 
 * @author Giuseppe, Sara, Vincenzo, Elena
 * @date 7 dicembre 2025
 */
package com.mycompany.bibliotecainds.controller;

import com.mycompany.bibliotecainds.App;
import java.io.IOException;
import javafx.fxml.FXML;

/**
 * @class SecondaryController
 * @brief Controller dedicato alla gestione della schermata secondaria
 * 
 * Fornisce un metodo per tornare alla schermata principale.
 */

public class SecondaryController {
    
    /**
     * @brief Passa dalla schermata secondaria alla principale
     * 
     * Questo metodo viene invocato dall'interfaccia grafica e richiama 
     * il metodo setRoot() per visualizzare la schermata principale
     * 
     * @throws IOException Se si verifica un errore durante il cambiamento della schermata
     */

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}