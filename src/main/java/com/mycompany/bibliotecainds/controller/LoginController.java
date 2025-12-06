/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Giuseppe
 */

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblErrore;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        String pwd = txtPassword.getText();

        if (!email.isEmpty() && !pwd.isEmpty()) {
            App.setRoot("primary");
        } else {
            lblErrore.setText("Inserisci email e password valide.");
        }
    }
}