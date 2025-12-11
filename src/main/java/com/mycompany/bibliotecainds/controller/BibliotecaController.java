/**
 * @file BibliotecaController.java
 * @brief Controller principale dell'interfaccia della biblioteca
 * 
 * Questa classe gestisce utte le interazioni dell'utente con la GUI, 
 * incluse le operazioni su:
 * - Catalogo dei libri
 * - Registrazioni degli utenti
 * - Prestiti e restituzioni
 * 
 * Utilizza i servizi:
 * - CatalogoService
 * - UtenteService
 * - PrestitoService
 * 
 * @author Giuseppe, Sara, Vincenzo, Elena
 * @date 7 dicembre 2025
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
import javafx.scene.layout.GridPane;
/**
 * @class BibliotecaController
 * @brief Controller JavaFX che coordina le interazioni fra la GUI e logica applicativa
 * 
 * Implementa l'interfacia Initializable e viene richiamato automaticamente 
 * al caricamento della scena principale. Gestisce:
 * - Visualizzazione dei dati
 * - Inserimento dei libri 
 * - Inserimento degli utenti
 * - Gestione dei prestiti
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
    @FXML private TableColumn<Libro, String> colIsbn;
    @FXML private TableColumn<Libro, Integer> colAnno;
    @FXML private TableColumn<Utente, String> colEmail;
    @FXML private TableColumn<Utente, String> colLibriInPrestito;
    @FXML private Button btnModificaLibro;
    @FXML private Button btnModificaUtente;
    @FXML private Button btnRimuoviLibro;
    @FXML private Button btnRimuoviUtente;
    
    private ObservableList<Libro> observableLibri;
    private ObservableList<Utente> observableUtenti;
    private ObservableList<Prestito> observablePrestiti;
    private CatalogoService catalogoService;
    private UtenteService utenteService;
    private PrestitoService prestitoService;

    /**
     * @brief Metodo chiamato automaticamente all'avvio.
     * 
     * Inizializza i servizi, carica i dati da visualizzare nella tabella 
     * e configura i servizi
     * @param url Non utilizzato
     * @param rb Non utilizzato
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
        
        btnModificaLibro.disableProperty().bind(tabellaLibri.getSelectionModel().selectedItemProperty().isNull());
        btnModificaUtente.disableProperty().bind(tabellaUtenti.getSelectionModel().selectedItemProperty().isNull());
        btnRimuoviLibro.disableProperty().bind(tabellaLibri.getSelectionModel().selectedItemProperty().isNull());
        btnRimuoviUtente.disableProperty().bind(tabellaUtenti.getSelectionModel().selectedItemProperty().isNull());
    }
    
    /**
     * @brief Carica da Archivio i dati degli utenti, libri e prestiti
     * 
     * Popola le liste presenti nella TableView
     */

    private void caricaDati() {
        observableLibri = FXCollections.observableArrayList(catalogoService.getCatalogo());
        observableUtenti = FXCollections.observableArrayList(utenteService.getListaUtenti());
        observablePrestiti = FXCollections.observableArrayList(Archivio.getInstance().getPrestitiAttivi());
    }
    
    /**
     * @brief Configura le colonne della TableView
     * 
     * Imposta i valueFactory per mostrare correttamente i dati
     * ed evidenzia i prestiti scaduti
     */

    private void configuraColonne() {
        // --- LIBRI ---
        colTitolo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTitolo()));
        colAutore.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getAutori().toString())); // O String.join se preferisci
        colCopie.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getCopieDisponibili()).asObject());
        colIsbn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getIsbn()));
        colAnno.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getAnno()).asObject());
        
        // --- UTENTI ---
        colNome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNome()));
        colCognome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCognome()));
        colMatricola.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMatricola()));
        colEmail.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEmail()));
        colLibriInPrestito.setCellValueFactory(d -> {
        String elencoLibri = d.getValue().getListaPrestiti().stream()
                .map(p -> p.getLibro().getTitolo())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Nessun prestito");
        return new SimpleStringProperty(elencoLibri);
        });
        
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
    
    /**
     * @brief Bottone "Aggiungi libro"
     * 
     * Legge i dati inseriti nella GUI, crea un nuovo oggetto Libro e lo aggiunge
     * al catalogo tramite CatalogoService
     * @param event Evento generato dal click del bottone
     */
    
    @FXML
    private void handleAggiungiLibro(ActionEvent event) {
        try {
            String titolo = txtTitolo.getText();
            String isbn = txtIsbn.getText();
            
            if (!isbn.matches("^[A-Za-z]{2}\\d{2}[A-Za-z]{2}$")) {
                showAlert("Errore Formato", "L'ISBN deve essere nel formato: 2 lettere, 2 numeri, 2 lettere (es. AB12CD).");
                return;
            }
            
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

            //Aggiorna GUI
            observableLibri.add(nuovoLibro);
            pulisciCampiLibro();
            showAlert("Successo", "Libro aggiunto al catalogo.");

        } catch (NumberFormatException e) {
            showAlert("Errore Formato", "Copie e Anno devono essere numeri interi.");
        } catch (Exception e) {
            showAlert("Errore Business", e.getMessage());
        }
    }
    
    /**
     * @brief Ripulisce i campi di input relativi ai libri dopo un inserimento
     */

    private void pulisciCampiLibro() {
        txtTitolo.clear();
        txtAutore.clear();
        txtAnno.clear();
        txtIsbn.clear();
        txtCopie.clear();
    }

    // --- GESTIONE UTENTI ---
    
    /**
     * Bottone "Aggiungi utente"
     * 
     * Crea un nuovo utente e lo registra tramite UtenteService
     * @param event Evento generato dal click 
     */
    
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
    
    /**
     * @brief Ripulisce i campi relativi all'inserimento di un utente
     */

    private void pulisciCampiUtente() {
        txtNomeUtente.clear();
        txtCognomeUtente.clear();
        txtMatricola.clear();
        txtEmail.clear();
    }

    // --- GESTIONE PRESTITI ---
    
    /**
     * Bottone "Nuovo prestito"
     * 
     * Verifica che siano selezionati un libro ed un utente, poi registra 
     * un nuovo prestito tramite PrestitoService
     * @param event Evento generato del click
     */
    
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
    
    /**
     * @brief Bottone "Restituzione"
     * 
     * Restituisce il libro associato al prestito selezionato
     * Aggiorna la GUI e i dati persistenti
     * 
     * @param event Evento generato dal click
     */

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
    
    /**
     * @brief Mostra la schermata informativa secondaria
     * 
     * @throws IOException In caso di errore nel caricamento della nuova scena
     */
    
    @FXML
    private void handleInfo() throws IOException {
        App.setRoot("secondary");
    }
    
    /**
     * @brief Effettua il logout e torna alla schermata di login
     * 
     * 
     * @throws IOException Se la schermata non può essere caricata
     */

    @FXML
    private void handleLogout() throws IOException {
        App.setRoot("login");
    }
    
    /**
     * @brief Mostra un popup informativo
     * 
     * @param title Titolo della finestra
     * @param content Messaggio da visualizzare
     */

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    // --- GESTIONE MODIFICA LIBRO ---
    
    /**
     * @brief Gestisce l'azione di modifica di un libro presente nel catalogo
     * 
     * Il metodo recupera dall'elenco il libro selezionato, apre una finestra di dialogo
     * con i campi preimpostati e ne permette la modifica
     * Alla conferma aggiorna l'oggetto nel model ed aggiorna la visualizzazione
     * 
     * @param event Evento generato dal click del bottone
     */
    @FXML
    private void handleModificaLibro(ActionEvent event) {
        Libro libro = tabellaLibri.getSelectionModel().getSelectedItem();
        if (libro == null) return;

        // Creiamo una finestra di dialogo custom
        Dialog<Libro> dialog = new Dialog<>();
        dialog.setTitle("Modifica Libro");
        dialog.setHeaderText("Modifica i dati del libro: " + libro.getTitolo());

        // Aggiungiamo i tasti OK e ANNULLA
        ButtonType loginButtonType = new ButtonType("Salva", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Creiamo i campi pre-compilati con i dati attuali
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        TextField editTitolo = new TextField(libro.getTitolo());
        TextField editAutori = new TextField(String.join(",", libro.getAutori())); // Uniamo la lista in stringa
        TextField editIsbn = new TextField(libro.getIsbn());
        TextField editAnno = new TextField(String.valueOf(libro.getAnno()));
        TextField editCopie = new TextField(String.valueOf(libro.getCopieDisponibili()));

        grid.add(new Label("Titolo:"), 0, 0); grid.add(editTitolo, 1, 0);
        grid.add(new Label("Autori:"), 0, 1); grid.add(editAutori, 1, 1);
        grid.add(new Label("ISBN:"), 0, 2);   grid.add(editIsbn, 1, 2);
        grid.add(new Label("Anno:"), 0, 3);   grid.add(editAnno, 1, 3);
        grid.add(new Label("Copie:"), 0, 4);  grid.add(editCopie, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Convertiamo il risultato quando si preme Salva
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                try {
                    // Aggiorniamo l'oggetto LIBRO originale
                    libro.setTitolo(editTitolo.getText());
                    libro.setIsbn(editIsbn.getText());
                    libro.setAnno(Integer.parseInt(editAnno.getText()));
                    libro.setCopie(Integer.parseInt(editCopie.getText()));
                    
                    // Ricostruiamo la lista autori
                    ArrayList<String> nuoviAutori = new ArrayList<>(Arrays.asList(editAutori.getText().split(",")));
                    libro.setAutori(nuoviAutori);
                    
                    return libro;
                } catch (Exception e) {
                    showAlert("Errore", "Dati non validi: " + e.getMessage());
                }
            }
            return null;
        });

        // Mostriamo la finestra e aspettiamo
        dialog.showAndWait().ifPresent(l -> {
            tabellaLibri.refresh(); // Aggiorna la grafica della tabella
            showAlert("Successo", "Libro modificato correttamente.");
        });
    }

    // --- GESTIONE MODIFICA UTENTE ---
    
    /**
     * @brief Gestisce l'azione di modifica di un utente presente nell'elenco
     * 
     * Il metodo recupera dall'elenco l'utente selezionato, apre una finestra di dialogo
     * con i campi preimpostati e ne permette la modifica
     * Alla conferma aggiorna l'oggetto nel model ed aggiorna la visualizzazione
     * 
     * @param event Evento generato dal click del bottone
     */
    @FXML
    private void handleModificaUtente(ActionEvent event) {
        Utente utente = tabellaUtenti.getSelectionModel().getSelectedItem();
        if (utente == null) return;

        Dialog<Utente> dialog = new Dialog<>();
        dialog.setTitle("Modifica Utente");
        dialog.setHeaderText("Modifica i dati di: " + utente.getCognome());

        ButtonType saveBtn = new ButtonType("Salva", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        TextField editNome = new TextField(utente.getNome());
        TextField editCognome = new TextField(utente.getCognome());
        TextField editMatricola = new TextField(utente.getMatricola());
        TextField editEmail = new TextField(utente.getEmail());

        grid.add(new Label("Nome:"), 0, 0);      grid.add(editNome, 1, 0);
        grid.add(new Label("Cognome:"), 0, 1);   grid.add(editCognome, 1, 1);
        grid.add(new Label("Matricola:"), 0, 2); grid.add(editMatricola, 1, 2);
        grid.add(new Label("Email:"), 0, 3);     grid.add(editEmail, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == saveBtn) {
                // Aggiorniamo l'oggetto UTENTE originale
                utente.setNome(editNome.getText());
                utente.setCognome(editCognome.getText());
                utente.setMatricola(editMatricola.getText());
                utente.setEmail(editEmail.getText());
                return utente;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(u -> {
            tabellaUtenti.refresh();
            showAlert("Successo", "Utente modificato correttamente.");
        });
    }
    
    // --- GESTIONE RIMOZIONE LIBRO ---
    /**
     * @brief Gestisce l'azione di cancellazione di un libro presente nell'elenco
     * 
     * Il metodo recupera dall'elenco il libro selezionato, apre una finestra di dialogo
     * per confermare la scelta di cancellazione o meno
     * Alla conferma l'oggetto viene rimosso dall'elenco e viene aggiornata la visualizzazione
     * 
     * @param event Evento generato dal click del bottone
     */
    @FXML
    private void handleRimuoviLibro(ActionEvent event) {
        Libro libro = tabellaLibri.getSelectionModel().getSelectedItem();
        if (libro == null) return;

        if (!chiediConferma("Elimina Libro", "Sei sicuro di voler eliminare '" + libro.getTitolo() + "'?")) {
            return;
        }

        try {
            catalogoService.rimuoviLibro(libro);
            
            observableLibri.remove(libro);
            
            showAlert("Successo", "Libro eliminato dal catalogo.");
        } catch (Exception e) {
            showAlert("Impossibile Eliminare", e.getMessage());
        }
    }

    // --- GESTIONE RIMOZIONE UTENTE ---
    /**
     * @brief Gestisce l'azione di cancellazione di un utente presente nell'elenco
     * 
     * Il metodo recupera dall'elenco l'utente selezionato, apre una finestra di dialogo
     * per confermare la scelta di cancellazione o meno
     * Alla conferma l'oggetto viene rimosso dall'elenco e viene aggiornata la visualizzazione
     * 
     * @param event Evento generato dal click del bottone
     */
    @FXML
    private void handleRimuoviUtente(ActionEvent event) {
        Utente utente = tabellaUtenti.getSelectionModel().getSelectedItem();
        if (utente == null) return;

        if (!chiediConferma("Elimina Utente", "Sei sicuro di voler eliminare " + utente.getCognome() + "?")) {
            return;
        }

        try {
            utenteService.rimuoviUtente(utente);
        
            observableUtenti.remove(utente);
            
            showAlert("Successo", "Utente eliminato correttamente.");
        } catch (Exception e) {
            showAlert("Errore Rimozione", e.getMessage());
        }
    }

    /**
     * @brief Gestisce l'azione di conferma di cancellazione
     * 
     * Il metodo apre una finestra di conferma dell'operazione di cancellazione
     * e permette all'utente di scegliere se confermare o meno l'operazione
     * 
     * @param event Evento generato dal click del bottone
     */
    private boolean chiediConferma(String title, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        
        // Restituisce true se l'utente clicca OK
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}