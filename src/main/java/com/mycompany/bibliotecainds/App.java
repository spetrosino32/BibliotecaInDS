/**
 * @file App.java
 * @brief Classe principale che avvia l'applicazione JavaFX
 * 
 * Questa classe è il punto di ingresso del programma
 * Si occupa di caricare la prima interfaccia grafica (FXML) e mostrare la finestra
*/
package com.mycompany.bibliotecainds;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @class App
 * @brief Gestisce l'avvio dell'app JavaFX
 * 
 * Contiene di metodi per caricare i file FXML e cambiare la schermata visualizzata
 */
public class App extends Application {

    private static Scene scene;
/**
 * @brief Metodo eseguito all'avvio dell'app.
 * 
 * Carica la scena iniziale e la mostra nella finestra.
 * 
 * @param stage Finestra principale dell'applicazione.
 * @throws IOException Se il file FXML non viene trovato.
 */
    @Override
    public void start(Stage stage) throws IOException {

        com.mycompany.bibliotecainds.data.Archivio.carica("archivio.dat");

        scene = new Scene(loadFXML("login"), 640, 480); 
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        com.mycompany.bibliotecainds.data.Archivio.salva("archivio.dat");
        super.stop();
    }
/**
 * @brief Cambia la schermata attuale con un'altra.
 * 
 * @param fxml Nome del file FXML da caricare (senza estensione).
 * @throws IOException Se il file non esiste o non può essere caricato.
 */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
/**
 * @brief Carica un file FXML e restituisce il layout grafico.
 * 
 * @param fxml Nome del file FXML (senza .fxml).
 * @return L'interfaccia grafica da mostrare.
 * @throws IOException Se il file non è disponibile.
 */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
/**
 * @brief Punto di ingresso dell'applicazione.
 * 
 * Avvia JavaFX.
 */
    public static void main(String[] args) {
        launch();
    }

}