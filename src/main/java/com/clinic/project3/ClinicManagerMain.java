package com.clinic.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main entry point for the Clinic Manager JavaFX application.
 * This class initializes and displays the main GUI window.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class ClinicManagerMain extends Application {
    /**
     * Starts the JavaFX application by setting up the primary stage.
     * Loads the FXML layout, sets the scene, and displays the stage.
     *
     * @param stage The primary stage for this JavaFX application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMain.class.getResource("clinic-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Clinic Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch();
    }
}