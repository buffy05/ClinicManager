package com.clinic.project3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClinicManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome bitch");
    }
}