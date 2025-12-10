/**
 * @file LoginController.java
 * @brief Controller che gestisce la schermata di login
 * 
 * Questo controller gestisce l'interazione dell'utente con la finestra di login
 * Si occupa di:
 * - Leggere email e password inserite
 * - Validare i campi
 * - Mostrare messaggi di errore
 * - Effettuare il passaggio alla schermata principale in caso di credenziali corrette
 * 
 * @author Giuseppe, Sara, Vincenzo, Elena
 * @date 7 dicembre 2025
 */
package com.mycompany.bibliotecainds.controller;

import com.mycompany.bibliotecainds.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @class LoginController
 * @brief Controller per la gestione del login
 * 
 * La classe fornisce i metodi necessari per convalidare le credenziali inserite 
 * e di cambiare schermata in caso di credenziali valide
 */

public class LoginController {
    
    /**
     * @brief Campo di input per la email dell'utente
     */
    
    @FXML private TextField txtEmail;
    
    /**
     * @brief Campo di input per la password dell'utente
     */
    
    @FXML private PasswordField txtPassword;
    
    /**
     * Etichetta per visualizzare eventuale messaggio di errore
     */
    
    @FXML private Label lblErrore;
    
    /**
     * @brief Gestisce il tentativo di login nel momento in cui l'utente preme il pulsante
     * 
     * Il metodo verifica che i campi della email e della password non siano vuoti.
     * Nel caso in cui siano validi carica l'interfaccia principale dell'app.
     * In caso contrario mostra un messaggio di errore
     * 
     * @param event Evento generato dal pulsante di login
     * @throws IOException  Se si verifica un errore nel cambio di schermata
     */

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        String pwd = txtPassword.getText();

    
        if ("admin".equals(email) && "admin".equals(pwd)) {
            App.setRoot("primary"); // Va alla dashboard se corretto
        }
        else {
            txtPassword.clear();
            lblErrore.setText("Credenziali errate, riprova.");
        }
    }
}