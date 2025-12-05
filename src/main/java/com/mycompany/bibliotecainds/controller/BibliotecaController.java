/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.controller;

import com.mycompany.bibliotecainds.App;
import com.mycompany.bibliotecainds.data.Archivio;
import com.mycompany.bibliotecainds.model.Libro;
import com.mycompany.bibliotecainds.model.Prestito;
import com.mycompany.bibliotecainds.model.Utente;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.converter.NumberStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
/**
 *
 * @author Giuseppe
 */

public class BibliotecaController implements Initializable {
   
    @FXML private TableView<Libro> tabellaLibri;
    @FXML private TableColumn<Libro, String> colTitolo;
    @FXML private TableColumn<Libro, String> colAutore;
    @FXML private TableColumn<Libro, Integer> colCopie;
    @FXML private TextField txtTitolo, txtAutore, txtAnno, txtIsbn, txtCopie;
    @FXML private TableView<Utente> tabellaUtenti;
    @FXML private TableColumn<Utente, String> colNome, colCognome, colMatricola;
    @FXML private TextField txtNomeUtente, txtCognomeUtente, txtMatricola, txtEmail;
    @FXML private TableView<Prestito> tabellaPrestiti;
    @FXML private TableColumn<Prestito, String> colPrestitoLibro, colPrestitoUtente, colPrestitoScadenza;

    private ObservableList<Libro> observableLibri;
    private ObservableList<Utente> observableUtenti;
    private ObservableList<Prestito> observablePrestiti;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Carica i dati dall'Archivio (Singleton)
        observableLibri = FXCollections.observableArrayList(Archivio.getInstance().getCatalogoLibri());
        observableUtenti = FXCollections.observableArrayList(Archivio.getInstance().getRegistroUtenti());
        observablePrestiti = FXCollections.observableArrayList(Archivio.getInstance().getPrestitiAttivi());

        // 2. Collega i dati alle tabelle
        tabellaLibri.setItems(observableLibri);
        tabellaUtenti.setItems(observableUtenti);
        tabellaPrestiti.setItems(observablePrestiti);

        // 3. Inizializza le colonne (Mapping)
        initColonne();
    }

    private void initColonne() {
        // Libri
        colTitolo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTitolo()));
        colAutore.setCellValueFactory(d -> new SimpleStringProperty(String.join(", ", d.getValue().getAutori())));
        colCopie.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getCopieDisponibili()).asObject());

        // Utenti
        colNome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNome()));
        colCognome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCognome()));
        colMatricola.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMatricola()));

        // Prestiti (naviga gli oggetti)
        colPrestitoLibro.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getLibro().getTitolo()));
        colPrestitoUtente.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUtente().getCognome()));
        colPrestitoScadenza.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDataScadenza().toString()));

        // Evidenzia ritardi in rosso
        tabellaPrestiti.setRowFactory(tv -> new TableRow<Prestito>() {
            @Override protected void updateItem(Prestito item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.isScaduto()) {
                    setStyle("-fx-background-color: #ffcccc;"); // Rosso
                } else {
                    setStyle("");
                }
            }
        });
    }


    @FXML
    private void handleAggiungiLibro(ActionEvent event) {
        try {
            String titolo = txtTitolo.getText();
            int copie = Integer.parseInt(txtCopie.getText());
            int anno = 0; 
            try { anno = Integer.parseInt(txtAnno.getText()); } catch(Exception e) {} // Anno opzionale
            
            if (titolo.isEmpty()) { showAlert("Errore", "Titolo obbligatorio."); return; }

            ArrayList<String> autori = new ArrayList<>(Arrays.asList(txtAutore.getText().split(",")));
            
            Libro nuovo = new Libro(titolo, autori, anno, txtIsbn.getText(), copie);
            
            observableLibri.add(nuovo);
            Archivio.getInstance().getCatalogoLibri().add(nuovo);
            
            txtTitolo.clear(); txtAutore.clear(); txtIsbn.clear(); txtCopie.clear(); txtAnno.clear();
        } catch (NumberFormatException e) {
            showAlert("Errore", "Le copie devono essere un numero.");
        }
    }

    @FXML
    private void handleAggiungiUtente(ActionEvent event) {
        if (txtNomeUtente.getText().isEmpty() || txtCognomeUtente.getText().isEmpty()) {
            showAlert("Errore", "Nome e Cognome obbligatori.");
            return;
        }
        Utente nuovo = new Utente(txtNomeUtente.getText(), txtCognomeUtente.getText(), 
                                  txtMatricola.getText(), txtEmail.getText(), new ArrayList<>());
        observableUtenti.add(nuovo);
        Archivio.getInstance().getRegistroUtenti().add(nuovo);
        txtNomeUtente.clear(); txtCognomeUtente.clear(); txtMatricola.clear(); txtEmail.clear();
    }

    @FXML
    private void handleNuovoPrestito(ActionEvent event) {
        Utente u = tabellaUtenti.getSelectionModel().getSelectedItem();
        Libro l = tabellaLibri.getSelectionModel().getSelectedItem();

        if (u == null || l == null) {
            showAlert("Selezione Mancante", "Seleziona un Utente e un Libro dalle rispettive tabelle prima di procedere.");
            return;
        }

        if (l.getCopieDisponibili()>0 && u.concediPrestito()) {
            Prestito p = new Prestito(l, LocalDate.now(), u); // Data calcolata nel costruttore
            
            // Aggiorna logica
            l.decrementaCopie();
            u.aggiungiPrestito(p);
            
            // Aggiorna View e Archivio
            observablePrestiti.add(p);
            Archivio.getInstance().getPrestitiAttivi().add(p);
            
            tabellaLibri.refresh(); // Per vedere il calo delle copie
            showAlert("Successo","Prestito registrato per " + u.getCognome());
        } else {
            showAlert("Impossibile procedere", "Libro non disponibile o Utente ha troppi prestiti.");
        }
    }

    @FXML
    private void handleRestituzione(ActionEvent event) {
        Prestito p = tabellaPrestiti.getSelectionModel().getSelectedItem();
        if (p == null) {
            showAlert("Selezione", "Seleziona un prestito da restituire.");
            return;
        }
        
        p.getLibro().incrementaCopie();
        p.getUtente().rimuoviPrestito(p);
        
        observablePrestiti.remove(p);
        Archivio.getInstance().getPrestitiAttivi().remove(p);
        tabellaLibri.refresh();
    }

    @FXML
    private void handleInfo() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void handleLogout() throws IOException {
        App.setRoot("login");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
