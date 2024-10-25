package com.clinic.project3;

import util.*;
import clinic.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;

public class ClinicManagerController {

    @FXML
    private ToggleGroup appType;

    @FXML
    private Button b_cancel;

    @FXML
    private Button b_reschedule;

    @FXML
    private Button b_schedule;

    @FXML
    private ChoiceBox<?> cb_cTimeslot;

    @FXML
    private ChoiceBox<?> cb_newTimeslot;

    @FXML
    private ChoiceBox<?> cb_oldTimeslot;

    @FXML
    private ChoiceBox<String> cb_sProviders; //will edit this to Print Providers only

    @FXML
    private ChoiceBox<?> cb_sServices;

    @FXML
    private ChoiceBox<?> cb_sTimeslot;

    @FXML
    private DatePicker dp_cDOB;

    @FXML
    private DatePicker dp_cDate;

    @FXML
    private DatePicker dp_rDOB;

    @FXML
    private DatePicker dp_rDate;

    @FXML
    private DatePicker dp_sDOB;

    @FXML
    private DatePicker dp_sDate;

    @FXML
    private Label labelA;

    @FXML
    private Label labelB;

    @FXML
    private Label labelC;

    @FXML
    private Label labelD;

    @FXML
    private Label labelE;

    @FXML
    private Label labelF;

    @FXML
    private Label labelG;

    @FXML
    private RadioButton r_imaging;

    @FXML
    private RadioButton r_office;

    @FXML
    private Tab t_cancelTab;

    @FXML
    private Tab t_rescheduleTab;

    @FXML
    private Tab t_scheduleTab;

    @FXML
    private TextArea ta_output;

    @FXML
    private TextField tf_cFirstName;

    @FXML
    private TextField tf_cLastName;

    @FXML
    private TextField tf_rFirstName;

    @FXML
    private TextField tf_rLastName;

    @FXML
    private TextField tf_sFirstName;

    @FXML
    private TextField tf_sLastName;

    @FXML
    private void initialize() {
        loadProviders();
    }

    private void loadProviders() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("providers.txt");
        if(inputStream == null) {
            System.out.println("Error: providers.txt file not found.");
            return;
        }
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                cb_sProviders.getItems().add(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onScheduleButtonClicked(ActionEvent event) {
        String firstName = tf_sFirstName.getText().trim();
        if(firstName.trim().isEmpty()) {
            ta_output.appendText("Please enter first name\n");
            return;
        }
        System.out.println("First Name: " + firstName);

        String lastName = tf_sLastName.getText().trim();
        if(lastName.trim().isEmpty()) {
            ta_output.appendText("Please enter last name\n");
            return;
        }
        System.out.println("Last Name: " + lastName);

        if(dp_sDOB.getValue() == null) {
            ta_output.appendText("Please choose a DOB\n");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String dobString = dp_sDOB.getValue().format(formatter);
        System.out.println("Selected date of birth: " + dobString);

        if(dp_sDate.getValue() == null) {
            ta_output.appendText("Please choose an appointment date\n");
            return;
        }
        String appDateString = dp_sDate.getValue().format(formatter);
        System.out.println("Appointment date: " + appDateString);

        RadioButton selectedRadioButton = (RadioButton) appType.getSelectedToggle();
        if(selectedRadioButton == null) {
            ta_output.appendText("Please select either Office or Imaging as appointment type.\n");
            return;
        }
        System.out.println("Selected option: " + selectedRadioButton.getText());


    }

}
