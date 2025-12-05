/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.controller;

import com.mycompany.bibliotecainds.App;
import java.io.IOException;
import javafx.fxml.FXML;
/**
 *
 * @author Giuseppe
 */

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}