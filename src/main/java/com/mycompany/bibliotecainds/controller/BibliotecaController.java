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
import com.mycompany.bibliotecainds.service.*;
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
    private CatalogoService catalogoService;
    private UtenteService utenteService;
    private PrestitoService prestitoService;

    /**
     * Metodo chiamato automaticamente all'avvio.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.catalogoService = new CatalogoServiceImpl();
        this.utenteService = new UtenteServiceImpl();
        this.prestitoService = new PrestitoServiceImpl();

        caricaDati();

        tabellaLibri.setItems(observableLibri);
        tabellaUtenti.setItems(observableUtenti);
        tabellaPrestiti.setItems(observablePrestiti);

        configuraColonne();
    }

    private void caricaDati() {
        observableLibri = FXCollections.observableArrayList(catalogoService.getCatalogo());
        observableUtenti = FXCollections.observableArrayList(utenteService.getListaUtenti());
        observablePrestiti = FXCollections.observableArrayList(Archivio.getInstance().getPrestitiAttivi());
    }

    private void configuraColonne() {
        // --- LIBRI ---
        colTitolo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTitolo()));
        colAutore.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getAutori().toString())); // O String.join se preferisci
        colCopie.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getCopieDisponibili()).asObject());

        // --- UTENTI ---
        colNome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNome()));
        colCognome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCognome()));
        colMatricola.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMatricola()));

        // --- PRESTITI ---
        colPrestitoLibro.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getLibro().getTitolo()));
        colPrestitoUtente.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUtente().getCognome()));
        colPrestitoScadenza.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDataScadenza().toString()));

        // Evidenziazione Ritardi
        tabellaPrestiti.setRowFactory(tv -> new TableRow<Prestito>() {
            @Override
            protected void updateItem(Prestito item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.isScaduto()) {
                    setStyle("-fx-background-color: #ffcccc;"); // Rosso chiaro
                } else {
                    setStyle("");
                }
            }
        });
    }

    // --- GESTIONE LIBRI ---
    @FXML
    private void handleAggiungiLibro(ActionEvent event) {
        try {
            String titolo = txtTitolo.getText();
            String isbn = txtIsbn.getText();
            
            int copie = Integer.parseInt(txtCopie.getText());
            int anno = 0;
            if (!txtAnno.getText().isEmpty()) {
                anno = Integer.parseInt(txtAnno.getText());
            }

            if (titolo.isEmpty() || isbn.isEmpty()) {
                showAlert("Errore", "Titolo e ISBN sono obbligatori.");
                return;
            }

            ArrayList<String> autori = new ArrayList<>(Arrays.asList(txtAutore.getText().split(",")));
            Libro nuovoLibro = new Libro(titolo, autori, anno, isbn, copie);

            catalogoService.aggiungiLibro(nuovoLibro);

            // Aggiorna GUI
            observableLibri.add(nuovoLibro);
            pulisciCampiLibro();
            showAlert("Successo", "Libro aggiunto al catalogo.");

        } catch (NumberFormatException e) {
            showAlert("Errore Formato", "Copie e Anno devono essere numeri interi.");
        } catch (Exception e) {
            showAlert("Errore Business", e.getMessage());
        }
    }

    private void pulisciCampiLibro() {
        txtTitolo.clear();
        txtAutore.clear();
        txtAnno.clear();
        txtIsbn.clear();
        txtCopie.clear();
    }

    // --- GESTIONE UTENTI ---
    @FXML
    private void handleAggiungiUtente(ActionEvent event) {
        try {
            String nome = txtNomeUtente.getText();
            String cognome = txtCognomeUtente.getText();
            String email = txtEmail.getText();
            String matricola = txtMatricola.getText();

            if (nome.isEmpty() || cognome.isEmpty() || matricola.isEmpty()) {
                showAlert("Errore", "Nome, Cognome e Matricola obbligatori.");
                return;
            }

            Utente nuovoUtente = new Utente(nome, cognome, matricola, email, new ArrayList<>());

            utenteService.registraUtente(nuovoUtente);

            // Aggiorna GUI
            observableUtenti.add(nuovoUtente);
            pulisciCampiUtente();
            showAlert("Successo", "Utente registrato.");

        } catch (Exception e) {
            showAlert("Errore Business", e.getMessage());
        }
    }

    private void pulisciCampiUtente() {
        txtNomeUtente.clear();
        txtCognomeUtente.clear();
        txtMatricola.clear();
        txtEmail.clear();
    }

    // --- GESTIONE PRESTITI ---
    @FXML
    private void handleNuovoPrestito(ActionEvent event) {
        Utente utenteSelezionato = tabellaUtenti.getSelectionModel().getSelectedItem();
        Libro libroSelezionato = tabellaLibri.getSelectionModel().getSelectedItem();

        if (utenteSelezionato == null || libroSelezionato == null) {
            showAlert("Attenzione", "Seleziona un utente e un libro dalle tabelle.");
            return;
        }

        try {
            boolean esito = prestitoService.registraPrestito(utenteSelezionato, libroSelezionato);

            if (esito) {
                // Aggiorna la tabella prestiti
                observablePrestiti.setAll(Archivio.getInstance().getPrestitiAttivi());
                
                // Aggiorna la tabella libri (per vedere il numero copie scendere)
                tabellaLibri.refresh(); 
                
                showAlert("Successo", "Prestito registrato per " + utenteSelezionato.getCognome());
            }

        } catch (Exception e) {
            showAlert("Operazione Fallita", e.getMessage());
        }
    }

    @FXML
    private void handleRestituzione(ActionEvent event) {
        Prestito prestitoSelezionato = tabellaPrestiti.getSelectionModel().getSelectedItem();

        if (prestitoSelezionato == null) {
            showAlert("Attenzione", "Seleziona un prestito da restituire.");
            return;
        }

        prestitoService.restituisciPrestito(prestitoSelezionato);

        // Aggiorna GUI
        observablePrestiti.remove(prestitoSelezionato);
        tabellaLibri.refresh();
        
        showAlert("Info", "Libro restituito con successo.");
    }

    // --- NAVIGAZIONE E UTILITY ---
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