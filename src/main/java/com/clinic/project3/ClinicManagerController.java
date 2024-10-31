package com.clinic.project3;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import util.*;
import clinic.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;

/**
 * Controller for the Clinic Manager user interface.
 * This class handles the scheduling, rescheduling, and canceling of appointments,
 * as well as displays providers, patients, and appointment details.
 * The controller is also linked to the FXML view.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class ClinicManagerController {
    // constants
    private static final int COMMAND = 0;

    //n instance variables
    private int NEXTTECHINDEX = 0;
    private boolean scheduleEmpty = true;
    private List<Appointment> appointmentList = new List<Appointment>();
    private List<Provider> providerList = new List<Provider>();
    private List<Technician> technicianList = new List<Technician>();
    private List<Patient> patientList = new List<Patient>();
    private List<Appointment> canceledAppointment = new List<Appointment>();
    private ObservableList<String> doctorInfo = FXCollections.observableArrayList();
    private ObservableList<String> timeslotInfo = FXCollections.observableArrayList();
    private ObservableList<String> serviceInfo = FXCollections.observableArrayList();

    @FXML
    private Button b_pa;

    @FXML
    private Button b_pi;

    @FXML
    private Button b_pl;

    @FXML
    private Button b_po;

    @FXML
    private Button b_pp;

    @FXML
    private TableColumn<Appointment, String> c_appointmentsCol;

    @FXML
    private TableView<Appointment> tv_appointmentsTable;

    @FXML
    private ToggleGroup appType;

    @FXML
    private Button b_cancel;

    @FXML
    private Button b_reschedule;

    @FXML
    private Button b_schedule;

    @FXML
    private ChoiceBox<String> cb_cTimeslot;

    @FXML
    private ChoiceBox<String> cb_newTimeslot;

    @FXML
    private ChoiceBox<String> cb_oldTimeslot;

    @FXML
    private ChoiceBox<String> cb_sProviders; //will edit this to Print Providers only

    @FXML
    private ChoiceBox<String> cb_sServices;

    @FXML
    private ChoiceBox<String> cb_sTimeslot;

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
    private TextArea ta_PC;

    @FXML
    private TextArea ta_PS;

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

    /**
     * Initializes the controller. Loads providers, timeslots, and services for the clinic manager application.
     * Called automatically by JavaFX.
     */
    @FXML
    private void initialize() {
        loadProviders();
        printTechnicians();
        loadTimeslots();
        serviceInfo.add("XRAY");
        serviceInfo.add("ULTRASOUND");
        serviceInfo.add("CATSCAN");
        cb_sServices.setItems(serviceInfo);

        c_appointmentsCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
        tv_appointmentsTable.setItems(FXCollections.observableArrayList());
    }

    /**
     * Parses a date string in the format MM/DD/YYYY and returns the parts as an array.
     *
     * @param dateStr The date string.
     * @return an array with month, day, and year as integers, or null if parsing fails.
     */
    private int[] parseDate(String dateStr) {
        try {
            String[] parts = dateStr.split("/");
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return new int[] { month, day, year };
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves the Radiology type based on the provided service string.
     *
     * @param service The service string.
     * @return the corresponding Radiology enum, or null if found.
     */
    private Radiology getRadiology(String service) {
        if(service.equalsIgnoreCase("XRAY")) return Radiology.XRAY;
        if(service.equalsIgnoreCase("ULTRASOUND")) return Radiology.ULTRASOUND;
        if(service.equalsIgnoreCase("CATSCAN")) return Radiology.CATSCAN;
        else return null;

    }

    /**
     * Checks if a technician is available for a given appointment time and date.
     *
     * @param technician The technician,
     * @param appointment The appointment to check availability against.
     * @return true if the technician is available, otherwise false.
     */
    private boolean isTechnicianAvailable(Technician technician, Appointment appointment) {
        for (Appointment a : appointmentList) {
            if (a.getProvider() != null && a.getProvider().equals(technician)) {
                if (a.getDate().equals(appointment.getDate()) && a.getTimeslot().equals(appointment.getTimeslot())) {
                    return false; //technician is not available
                }
            }
        }
        return true; //technician is available
    }

    /**
     * Checks if a specific room is available at a certain date, timeslot, and location.
     *
     * @param date The date of the appointment.
     * @param timeslot The timeslot of the appointment.
     * @param room The radiology room.
     * @param location The location of the clinic.
     * @return true if the room is available, otherwise false.
     */
    private boolean isRoomAvailable(Date date, Timeslot timeslot, Radiology room, Location location) {
        for (Appointment appointment : appointmentList) {
            Provider provider = providedProvider(appointment);
            if (provider == null) return false;
            // Check if the appointment is an instance of Imaging
            if (appointment instanceof Imaging imagingAppointment) {
                // Compare date, timeslot, and room
                if (imagingAppointment.getDate().equals(date) &&
                        imagingAppointment.getTimeslot().equals(timeslot) &&
                        imagingAppointment.getRoom().equals(room) && provider.getLocation().equals(location)) {

                    return false; // Room is not available
                }
            }
        }
        return true; // Room is available
    }

    /**
     * Checks if a provider is available for a given appointment time and date.
     *
     * @param appointment The appointment to check availability against.
     * @return true if the provider is available, otherwise false.
     */
    private boolean isProviderAvailable(Appointment appointment) {
        for(Appointment a : appointmentList) {
            if(a.getProvider() != null && a.getProvider().equals(appointment.getProvider())) {
                //compare using compareTo
                if(a.compareTo(appointment) == 0) return false; //not available
            }
        }
        return true; //is available
    }

    /**
     * Searches for a technician based on the profile provided.
     *
     * @param profile The profile of the technician to search for.
     * @return the Technician if found, otherwise null.
     */
    private Technician searchTechnician(Profile profile) {
        for(Technician technician : technicianList) {
            if(technician.getProfile().equals(profile)) return technician;
        }
        return null;
    }

    /**
     * Searches for a doctor based on the NPI provided.
     *
     * @param npi the National Provider Identifier of the doctor to search for.
     * @return the Doctor if found, otherwise null.
     */
    private Provider searchDoctor(String npi) {
        for(Provider provider : providerList) {
            if(provider instanceof Doctor) {
                if(((Doctor) provider).getNpi().equalsIgnoreCase(npi)) return provider;
            }
        }
        return null;
    }

    /**
     * Reverses the order of technicians in the technicianList.
     */
    private void reverseTechnicianList() {
        int left = 0;
        int right = technicianList.size() - 1;

        while (left < right) {
            Technician temp = technicianList.get(left);
            technicianList.set(left, technicianList.get(right));
            technicianList.set(right, temp);

            left++;
            right--;
        }
    }

    /**
     * Prints the list of technicians in rotation order to the output text area.
     */
    private void printTechnicians() {
        reverseTechnicianList();
        ta_output.appendText("\nRotation list for the technicians.\n");

        for(Technician technician : technicianList) {
            ta_output.appendText(technician.getProfile().getFullName() + " (" + technician.getLocation().getCity() + ")" + " --> ");
        }
        ta_output.appendText("\n");
    }

    /**
     * Prints the list of scheduled appointments to the output text area.
     */
    private void printAppointmentList() {
        for(Appointment appointment : appointmentList) {
            ta_output.appendText(appointment.toString());
        }
    }

    /**
     * Validates the appointment date string and checks if it meets specific conditions.
     *
     * @param appDateString The appointment date as a string in the format MM/DD/YYYY.
     * @return a valid Date object if the date is correct, otherwise null.
     */
    private Date isDateValid(String appDateString) {
        //extract date
        String[] mmddyyyy = appDateString.split("/");
        Date date = new Date(Integer.parseInt(mmddyyyy[0].trim()), Integer.parseInt(mmddyyyy[1].trim()), Integer.parseInt(mmddyyyy[2].trim()));
        //check if date is valid:
        if(!date.isValid()) {
            ta_output.appendText("\nAppointment Date: " + date.toString() + " is not a valid calendar date.");
            return null;
        }
        if(date.isToday() || date.isBeforeToday()) {
            ta_output.appendText("\nAppointment Date: " + date.toString() + " is today or a date before today.");
            return null;
        }
        if(date.isWeekend()) {
            ta_output.appendText("\nAppointment Date: " + date.toString() + " is Saturday or Sunday.");
            return null;
        }
        if(!date.isIn6Months()) {
            ta_output.appendText("\nAppointment Date: " + date.toString() + " is not within six months.");
            return null;
        }
        return date;
    }

    /**
     * Validates the date of birth string and checks if it meets specific conditions.
     *
     * @param dobString The date of birth as a string in the format MM/DD/YYYY.
     * @return a valid Date object if the date is correct, otherwise null.
     */
    private Date isDOBValid(String dobString) {
        //extract dob
        String[] mmddyyyyDOB = dobString.split("/");
        Date dateDOB = new Date(Integer.parseInt(mmddyyyyDOB[0].trim()), Integer.parseInt(mmddyyyyDOB[1].trim()), Integer.parseInt(mmddyyyyDOB[2].trim()));
        if(!dateDOB.isValid()) {
            ta_output.appendText("\nPatient dob: " + dateDOB.toString() + " is not a valid calendar date.");
            return null;
        }
        if(dateDOB.isToday() || !dateDOB.isBeforeToday()) {
            ta_output.appendText("\nPatient dob: " + dateDOB.toString() + " is today or a date after today.");
            return null;
        }
        return dateDOB;
    }

    /**
     * Finds an available technician and assigns it to the imaging appointment.
     *
     * @param date The date of the appointment.
     * @param timeslot The timeslot of the appointment.
     * @param room The radiology room.
     * @param imagingAppointment The imaging appointment to assign the technician to.
     */
    private void findAvailableTechnician(Date date, Timeslot timeslot, Radiology room, Imaging imagingAppointment) {
        int startingIndex = NEXTTECHINDEX;
        boolean foundAvailable = false;
        if(appointmentList.contains(imagingAppointment)) {
            ta_output.appendText("\n");
            ta_output.appendText(imagingAppointment.getPatient().toString() + " has an existing appointment at the same time slot.");
            imagingAppointment.setRoom(null);
            return;
        }

        do {
            //get current technician based on the nextTechnicianIndex
            Technician currentTechnician = technicianList.get(NEXTTECHINDEX);
            //check if current tech is available at the specified timeslot
            if(isTechnicianAvailable(currentTechnician, imagingAppointment)) {
                //check if room is available
                Location location = currentTechnician.getLocation();
                if(isRoomAvailable(date, timeslot, room, location)) {
                    foundAvailable = true;
                    imagingAppointment.setProvider(currentTechnician);
                    //move to next tech in list:
                    NEXTTECHINDEX = (NEXTTECHINDEX + 1) % technicianList.size();
                    break;
                }
            }
            NEXTTECHINDEX = (NEXTTECHINDEX + 1) % technicianList.size();

        } while(NEXTTECHINDEX != startingIndex);
        if(!foundAvailable) {
            NEXTTECHINDEX = startingIndex;
        }
    }

    /**
     * Finds the provider associated with an appointment.
     *
     * @param appointment The appointment for which to find the provider.
     * @return the associated Provider if found, otherwise null.
     */
    private Provider providedProvider(Appointment appointment) {
        for(Provider provider : providerList) {
            if(appointment.getProvider().getProfile().equals(provider.getProfile())) {
                return provider;
            }
        }
        return null;
    }

    /**
     * Checks if the appointment exists in the list and removes it if found.
     *
     * @param appointment The appointment to search for.
     * @param appDateString The appointment date string.
     * @param timeSlotString The appointment timeslot string.
     * @param firstName The first name of the patient.
     * @param lastName The last name of the patient.
     * @param dobString The date of birth string of the patient.
     * @return 1 if the appointment was found and removed, otherwise 9.
     */
    private int superContains(Appointment appointment, String appDateString, String timeSlotString, String firstName, String lastName, String dobString) {
        for(Appointment a : appointmentList) {
            if(appointment.equals(a) && appointment.getPatient().getProfile().equals(a.getPatient().getProfile())) {
                appointmentList.remove(a);
                ta_output.appendText("\n");
                ta_output.appendText(appDateString + " " + timeSlotString + " " + firstName + " " + lastName + " " + dobString + " - appointment has been canceled.");
                Provider provider = providedProvider(a);
                if(provider == null) return 0;
                provider.subtractCharges(provider.rate());
                canceledAppointment.add(a);
                return 1;

            }
        }
        return 0;
    }

    /**
     * Finds a provider associated with a specific appointment.
     *
     * @param appointment The appointment for which to find the provider.
     * @return the Provider associated with the appointment if found, otherwise null.
     */
    private Person findProvider(Appointment appointment) {
        int index = appointmentList.indexOf(appointment);
        return appointmentList.get(index).getProvider();
    }

    /**
     * Searches for an existing patient in the list by profile or creates a new one.
     *
     * @param profile The profile of the patient to find or create.
     * @return the existing or newly created Patient.
     */
    private Patient findOrCreatePatient(Profile profile) {
        for(Patient patient : patientList) {
            if(patient.getProfile().equals(profile)) {
                return patient;
            }
        }
        //if patient does not exist, create and add it
        Patient patientTBA = new Patient(profile);
        patientList.add(patientTBA);
        return patientTBA;
    }

    /**
     * Calculates the total charges accumulated by a patient.
     *
     * @param patient The patient whose charges to calculate.
     * @return the total charges for the patient.
     */
    private int getTotalCharges(Patient patient) {
        int total = 0;
        Visit currentVisit = patient.getVisitHistory();

        //traverse linked list and sum up charges
        while(currentVisit != null) {
            Appointment appointment = currentVisit.getAppointment();
            if(providedProvider(appointment) == null) return total;
            if(!isAppointmentCanceled(appointment)) {
                total += providedProvider(appointment).rate();
            }
            currentVisit = currentVisit.getNext();
        }
        return total;
    }

    /**
     * Checks if an appointment has been canceled.
     *
     * @param appointment The appointment to check.
     * @return true if the appointment is canceled, otherwise false.
     */
    private boolean isAppointmentCanceled(Appointment appointment) {
        for(Appointment canceledAppt : canceledAppointment) {
            if(canceledAppt.equals(appointment)) return true;
        }
        return false;
    }

    /**
     * Retrieves a Timeslot object based on the given slot number.
     *
     * @param slotNum The number of the desired timeslot (1 to 12).
     * @return the corresponding Timeslot if valid, otherwise null.
     */
    private Timeslot checkTimeslot(int slotNum) {
        try {
            //int slotNum = Integer.parseInt(slotNumString);
            //array to store the 12 slots
            Timeslot[] slots = new Timeslot[12];
            // Fill morning slots (9:00 AM to 11:30 AM)
            int hour = 9;
            int minute = 0;
            for (int i = 0; i < 6; i++) {
                slots[i] = new Timeslot(hour, minute);
                minute += 30;
                if (minute == 60) {
                    hour++;
                    minute = 0;
                }
            }

            // Fill afternoon slots (2:00 PM to 4:30 PM)
            hour = 14; // 2:00 PM in 24-hour format
            minute = 0;
            for (int i = 6; i < 12; i++) {
                slots[i] = new Timeslot(hour, minute);
                minute += 30;
                if (minute == 60) {
                    hour++;
                    minute = 0;
                }
            }

            // Validate slot number
            if (slotNum < 1 || slotNum > 12) {
                return null;
            }

            return slots[slotNum - 1]; //return  corresponding Timeslot

        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Loads timeslots into ChoiceBoxes for selection in the UI.
     */
    private void loadTimeslots() {
        for(int i = 1; i < 13; i++) {
            Timeslot newTimeslot = checkTimeslot(i);
            assert newTimeslot != null;
            timeslotInfo.add(i + " : " + newTimeslot.toString());
        }
        cb_sTimeslot.setItems(timeslotInfo);
        cb_cTimeslot.setItems(timeslotInfo);
        cb_newTimeslot.setItems(timeslotInfo);
        cb_oldTimeslot.setItems(timeslotInfo);
    }

    /**
     * Loads providers data from a file and initializes the list of doctors and technicians.
     */
    private void loadProviders() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("providers.txt");
        if(inputStream == null) {
            ta_output.appendText("\nError: providers.txt file not found.");
            return;
        }
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                if(tokens[0].equalsIgnoreCase("D")) {
                    int[] dobParts = parseDate(tokens[3]);
                    if(dobParts == null) {
                        continue;
                    }
                    String first = tokens[1];
                    String last = tokens[2];
                    Date dob = new Date(dobParts[0], dobParts[1], dobParts[2]);
                    Location location = Location.valueOf(tokens[4].toUpperCase());
                    Specialty specialty = Specialty.valueOf(tokens[5].toUpperCase());
                    String npi = tokens[6];
                    Doctor doctor = new Doctor(new Profile(first, last, dob), location, specialty, npi);
                    providerList.add(doctor);
                    doctorInfo.add(first + " " + last + " " + npi);
                } else if(tokens[0].equalsIgnoreCase("T")) {
                    int[] dobParts = parseDate(tokens[3]);
                    if(dobParts == null) {
                        continue;
                    }
                    String first = tokens[1];
                    String last = tokens[2];
                    Date dob = new Date(dobParts[0], dobParts[1], dobParts[2]);
                    Location location = Location.valueOf(tokens[4].toUpperCase());
                    int ratePerVisit = Integer.parseInt(tokens[5]);
                    Technician technician = new Technician(new Profile(first, last, dob), location, ratePerVisit);
                    providerList.add(technician);
                    technicianList.add(technician);
                }
            }
            Sort.sortProviders(providerList);
            cb_sProviders.setItems(doctorInfo);
            printProviders();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the list of available providers to the output text area.
     */
    private void printProviders() {
        ta_output.appendText("Providers available: \n");
        for(Provider provider : providerList) {
            ta_output.appendText(provider.toString()+"\n");
        }
    }

    /**
     * Converts the list of appointments to an ObservableList based on the filter key.
     *
     * @param key The filter key indicating the type of appointments to include.
     * @return the ObservableList of filtered appointments.
     */
    private ObservableList<Appointment> convertToObservableList(String key) {
        ObservableList<Appointment> observableList = FXCollections.observableArrayList();
        switch(key) {
            case "all":
                for(Appointment appointment : appointmentList) {
                    observableList.add(appointment);
                }
                break;
            case "office":
                for(Appointment appointment: appointmentList) {
                    if(appointment instanceof Imaging) continue;
                    else observableList.add(appointment);
                }
                break;
            case "imaging":
                for(Appointment appointment: appointmentList) {
                    if(appointment instanceof Imaging) observableList.add(appointment);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid sort key");
        }
        return observableList;
    }

    /**
     * Clears all input fields and UI elements after a successful scheduling.
     */
    private void clearBoxesAfterSchedulingSuccesfull() {
        tf_sFirstName.clear();
        tf_rFirstName.clear();
        tf_cFirstName.clear();
        tf_sLastName.clear();
        tf_rLastName.clear();
        tf_cLastName.clear();
        cb_sProviders.getSelectionModel().clearSelection();
        cb_sServices.getSelectionModel().clearSelection();
        cb_sTimeslot.getSelectionModel().clearSelection();
        cb_newTimeslot.getSelectionModel().clearSelection();
        cb_oldTimeslot.getSelectionModel().clearSelection();
        cb_cTimeslot.getSelectionModel().clearSelection();
        dp_sDOB.setValue(null);
        dp_sDate.setValue(null);
        dp_rDOB.setValue(null);
        dp_rDate.setValue(null);
        dp_cDOB.setValue(null);
        dp_cDate.setValue(null);
        appType.selectToggle(null);
    }

    /**
     * Processes input data to create an appointment based on the provided information.
     *
     * @param firstName The first name of the patient.
     * @param lastName The last name of the patient.
     * @param dobString The date of birth of the patient as a string.
     * @param appDateString The date of the appointment as a string.
     * @param timeSlotString The timeslot of the appointment.
     * @return Appointment object if created successfully, otherwise null.
     */
    private Appointment processLine(String firstName, String lastName, String dobString, String appDateString, String timeSlotString) {
        Date date = isDateValid(appDateString);
        if(date == null) return null;

        int timeSlotNum = Integer.parseInt(timeSlotString);
        Timeslot timeslot = checkTimeslot(timeSlotNum);
        if(timeslot == null) {
            return null;
        }

        Date dob = isDOBValid(dobString);
        if(dob == null) return null;

        Person patient = new Person(new Profile(firstName, lastName, dob));

        return new Appointment(date, timeslot, patient, null);
    }

    /**
     * Schedules an office appointment with a specified doctor.
     *
     * @param firstName The first name of the patient.
     * @param lastName The last name of the patient.
     * @param dobString The date of birth of the patient.
     * @param appDateString The date of the appointment.
     * @param timeSlotString The appointment timeslot.
     * @param npi The NPI of the doctor.
     */
    private void handleD(String firstName, String lastName, String dobString, String appDateString, String timeSlotString, String npi) {
        Appointment appointment = processLine(firstName, lastName, dobString, appDateString, timeSlotString);
        if(appointment == null) return;
        //String npi = tokens[6];
        Doctor doctor = (Doctor) searchDoctor(npi);
        if(doctor == null) {
            return;
        }
        appointment.setProvider(doctor);
        Patient patient = findOrCreatePatient(appointment.getPatient().getProfile());
        Visit newVist = new Visit(appointment);
        if(appointmentList.isEmpty()) {
            appointmentList.add(appointment);
            ta_output.appendText("\n");
            ta_output.appendText(appointment.toString() + " booked.");
            doctor.addCharges(doctor.rate());
            patient.addVisit(newVist);
            scheduleEmpty = false;
            return;
        }
        if(isProviderAvailable(appointment) && !fortifiedContains(appointment)) {
            appointmentList.add(appointment);
            ta_output.appendText("\n");
            ta_output.appendText(appointment.toString() + " booked.");
            doctor.addCharges(doctor.rate());
            patient.addVisit(newVist);
            scheduleEmpty = false;
        }
        else {
            if(fortifiedContains(appointment)) {
                ta_output.appendText("\n");
                ta_output.appendText(appointment.getPatient().toString() + " has an existing appointment at the same time slot.");
                return;
            }
            if(!isProviderAvailable(appointment)) {
                ta_output.appendText("\n");
                ta_output.appendText(appointment.getProvider().toString() + " is not available at slot " + timeSlotString);
            }
        }
    }

    /**
     * Checks if an appointment already exists in the appointment list with the same patient,
     * timeslot, and date.
     *
     * @param appointment The appointment to check.
     * @return true if the appointment exists, otherwise false.
     */
    private boolean fortifiedContains(Appointment appointment) {
        if(appointmentList.contains(appointment)) return true;
        else {
            for(Appointment a : appointmentList) {
                if(appointment.getPatient().getProfile().equals(a.getPatient().getProfile()) &&
                    appointment.getTimeslot().equals(a.getTimeslot()) &&
                        appointment.getDate().equals(a.getDate())) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Schedules an imaging appointment by assigning an available technician and room.
     *
     * @param firstName The first name of the patient.
     * @param lastName The last name of the patient.
     * @param dobString The date of birth of the patient.
     * @param appDateString The date of the appointment.
     * @param timeSlotString The appointment timeslot.
     * @param service The radiology service type.
     */
    private void handleT(String firstName, String lastName, String dobString, String appDateString, String timeSlotString, String service) {
        Appointment appointment = processLine(firstName, lastName, dobString, appDateString, timeSlotString);
        if(appointment == null) return;
        if(fortifiedContains(appointment)) {
            ta_output.appendText("\n");
            ta_output.appendText(appointment.getPatient().toString() + " has an existing appointment at the same time slot.");
            return;
        }
        Radiology radiologyRoom = getRadiology(service);
        if(radiologyRoom == null) {
            return;
        }
        //proceed to assign a technician
        Imaging imagingApp = new Imaging(appointment.getDate(), appointment.getTimeslot(),
                appointment.getPatient(), appointment.getProvider(), radiologyRoom);

        findAvailableTechnician(appointment.getDate(), appointment.getTimeslot(), radiologyRoom, imagingApp);
        if(imagingApp.getRoom() == null) return;
        if(imagingApp.getProvider() == null) {
            ta_output.appendText("\n");
            ta_output.appendText("Cannot find an available technician at all locations for " + radiologyRoom + " at slot " + timeSlotString);
            return;
        }
        Visit newVisit = new Visit(imagingApp);
        Patient patient = findOrCreatePatient(imagingApp.getPatient().getProfile());
        Technician technician = (Technician) searchTechnician(imagingApp.getProvider().getProfile());
        appointmentList.add(imagingApp);
        ta_output.appendText("\n");
        ta_output.appendText(imagingApp.toString() + " booked.");
        //money stuff
        assert technician != null;
        technician.addCharges(technician.rate());
        patient.addVisit(newVisit);
        scheduleEmpty = false;
    }

    /**
     * Reschedules an office appointment for a patient with a specified provider.
     *
     * @param firstName The first name of the patient.
     * @param lastName The last name of the patient.
     * @param dobString The date of birth of the patient.
     * @param oldTimeslotString The old timeslot of the appointment.
     * @param newTimeslotString The new timeslot for the appointment.
     * @param appDateString The date of the appointment.
     */
    private void handleReschedule(String firstName, String lastName, String dobString, String oldTimeslotString, String newTimeslotString, String appDateString) {
        //only for office appointments, imaging appointments can't be rescheduled
        Appointment tempAppointment = processLine(firstName, lastName, dobString, appDateString, oldTimeslotString);
        if(tempAppointment == null) return;
        if(!appointmentList.contains(tempAppointment)) {
            ta_output.appendText("\n");
            ta_output.appendText(tempAppointment.getDate().toString() +
                    " " + tempAppointment.getTimeslot() + " " +
                    tempAppointment.getPatient().toString() + " does not exist.");
            return;
        }
        Timeslot timeslot = checkTimeslot(Integer.parseInt(newTimeslotString));
        if(timeslot == null) {
            return;
        }
        Date newDate = isDateValid(appDateString);
        if(newDate == null) return;
        Person provider = findProvider(tempAppointment);
        tempAppointment.setProvider(provider);
        if(provider == null) return;
        Appointment newAppointment = new Appointment(newDate, timeslot, tempAppointment.getPatient(), provider);
        if(fortifiedContains(newAppointment)) {
            ta_output.appendText("\n");
            ta_output.appendText(newAppointment.getPatient() + " has an existing appointment at the same time slot.");
            return;
        }
        if(!isProviderAvailable(newAppointment)) {
            ta_output.appendText("\n");
            ta_output.appendText(provider.toString() + " is not available at slot " + newTimeslotString);
            return;
        }
        appointmentList.remove(tempAppointment);
        appointmentList.add(newAppointment);
        ta_output.appendText("\nRescheduled to " + newAppointment.toString());
    }

    /**
     * Cancels an existing appointment based on the provided information.
     *
     * @param firstName The first name of the patient.
     * @param lastName The last name of the patient.
     * @param dobString The date of birth of the patient.
     * @param appDateString The date of the appointment.
     * @param timeSlotString The appointment timeslot.
     */
    private void handleCancel(String firstName, String lastName, String dobString, String appDateString, String timeSlotString) {
        Appointment appointment = processLine(firstName, lastName, dobString, appDateString, timeSlotString);

        if(appointment == null) return;
        if(superContains(appointment, appDateString, timeSlotString, firstName, lastName, dobString) == 0) {
            ta_output.appendText("\n");
            ta_output.appendText(appDateString + " " + timeSlotString + " " + firstName + " " + lastName + " " + dobString + " - appointment does not exist.");
        }
    }

    /**
     * Handles scheduling an appointment based on the UI input.
     *
     * @param event the ActionEvent triggered by the schedule button.
     */
    @FXML
    void onScheduleButtonClicked(ActionEvent event) {
        String firstName = tf_sFirstName.getText().trim();
        if(firstName.trim().isEmpty()) {
            ta_output.appendText("\nPlease enter first name");
            return;
        }
        String lastName = tf_sLastName.getText().trim();
        if(lastName.trim().isEmpty()) {
            ta_output.appendText("\nPlease enter last name");
            return;
        }
        if(dp_sDOB.getValue() == null) {
            ta_output.appendText("\nPlease choose a DOB");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String dobString = dp_sDOB.getValue().format(formatter);
        if(dp_sDate.getValue() == null) {
            ta_output.appendText("\nPlease choose an appointment date");
            return;
        }
        String appDateString = dp_sDate.getValue().format(formatter);
        String timeslotFullString = cb_sTimeslot.getValue();
        if(timeslotFullString == null) {
            ta_output.appendText("\nPlease select a timeslot from the list.");
            return;
        }
        String[] timeSlotString = timeslotFullString.split(" ");
        RadioButton selectedRadioButton = (RadioButton) appType.getSelectedToggle();
        if(selectedRadioButton == null) {
            ta_output.appendText("\nPlease select either Office or Imaging as appointment type.");
            return;
        }
        if(selectedRadioButton == r_office) {
            String selectedProvider = cb_sProviders.getValue();
            if(selectedProvider == null) {
                ta_output.appendText("\nPlease select a provider from the list.");
                return;
            }
            String[] npi = selectedProvider.split(" ");
            handleD(firstName, lastName, dobString, appDateString, timeSlotString[0], npi[2]);
            clearBoxesAfterSchedulingSuccesfull();
            return;
        }
        if(selectedRadioButton == r_imaging) {
            String selectedService = cb_sServices.getValue();
            if(selectedService == null) {
                ta_output.appendText("\nPlease select a service from the list.");
                return;
            }
            handleT(firstName, lastName, dobString, appDateString, timeSlotString[0], selectedService);
            clearBoxesAfterSchedulingSuccesfull();
        }
    }

    /**
     * Handles sorting and displaying appointments by Date-Time-Provider.
     */
    @FXML
    void onDateTimeProviderButton() {
        if(appointmentList.isEmpty() || providerList.isEmpty() || scheduleEmpty) {
            tv_appointmentsTable.setItems(FXCollections.observableArrayList());
            ta_output.appendText("\nCannot display appointment list because there are no appointments.\n");
            return;
        }
        //sort appointment list by date/time/provider
        Sort.sortAppointments(appointmentList, providerList, "PA");
        tv_appointmentsTable.setItems(convertToObservableList("all"));
    }

    /**
     * Handles sorting and displaying appointments by County-Date-Time.
     */
    @FXML
    void onCountyDateTimeButton() {
        if(appointmentList.isEmpty() || providerList.isEmpty() || scheduleEmpty) {
            tv_appointmentsTable.setItems(FXCollections.observableArrayList());
            ta_output.appendText("\nCannot display appointment list because there are no appointments.\n");
            return;
        }
        Sort.sortAppointments(appointmentList, providerList, "PL");
        tv_appointmentsTable.setItems(convertToObservableList("all"));
    }

    /**
     * Handles sorting and displaying appointments by Patient-Date-Time.
     */
    @FXML
    void onPatientDateTimeButton() {
        if(appointmentList.isEmpty() || providerList.isEmpty() || scheduleEmpty) {
            tv_appointmentsTable.setItems(FXCollections.observableArrayList());
            ta_output.appendText("\nCannot display appointment list because there are no appointments.\n");
            return;
        }
        Sort.sortAppointments(appointmentList, providerList, "PP");
        tv_appointmentsTable.setItems(convertToObservableList("all"));
    }

    /**
     * Handles sorting and displaying only office appointments.
     */
    @FXML
    void onOfficeOnlyButton() {
        if(appointmentList.isEmpty() || providerList.isEmpty() || scheduleEmpty) {
            tv_appointmentsTable.setItems(FXCollections.observableArrayList());
            ta_output.appendText("\nCannot display appointment list because there are no appointments.\n");
            return;
        }
        Sort.sortAppointments(appointmentList, providerList, "PL");
        tv_appointmentsTable.setItems(convertToObservableList("office"));

    }

    /**
     * Handles sorting and displaying only imaging appointments.
     */
    @FXML
    void onImagingOnlyButton() {
        if(appointmentList.isEmpty() || providerList.isEmpty() || scheduleEmpty) {
            tv_appointmentsTable.setItems(FXCollections.observableArrayList());
            ta_output.appendText("\nCannot display appointment list because there are no appointments.\n");
            return;
        }
        Sort.sortAppointments(appointmentList, providerList, "PL");
        tv_appointmentsTable.setItems(convertToObservableList("imaging"));
    }

    /**
     * Displays provider credit amounts in a sorted order.
     */
    @FXML
    void onPCButtonClicked() {
        ta_PC.clear();
        if(appointmentList.isEmpty() || providerList.isEmpty()) {
            //tv_appointmentsTable.setItems(FXCollections.observableArrayList());
            ta_output.appendText("\nSchedule calendar is empty.\n");
            return;
        }
        ta_PC.appendText("\n** Credit amount ordered by provider. **\n");
        int i = 1;
        for(Provider provider : providerList) {
            ta_PC.appendText("\n("+i+") " + provider.getProfile() + " [credit amount: $" + provider.getTotalCharges() + ".00]\n");
            i++;
        }
        ta_PC.appendText("\n** end of list **\n");
    }

    /**
     * Displays billing statements ordered by patient.
     */
    @FXML
    void onPSButtonClicked() {
        ta_PS.clear();
        if(appointmentList.isEmpty() || providerList.isEmpty() || patientList.isEmpty()) {
            ta_output.appendText("\nSchedule calendar is empty.\n");
            return;
        }

        Sort.sortPatients(patientList);
        ta_PS.appendText("\n** Billing statement ordered by patient. **");
        int i = 1;
        for(Patient patient : patientList) {
            int totalCharges = getTotalCharges(patient);
            ta_PS.appendText("\n("+i+") " + patient.getProfile() + " [due: $" + totalCharges + ".00]\n");
            i++;
        }
        scheduleEmpty = true;
    }

    /**
     * Clears various UI elements and refreshes the screen.
     */
    @FXML
    void refreshButton() {
        //clear table
        tv_appointmentsTable.setItems(FXCollections.observableArrayList());
        //clear pc text area and ps text area
        ta_PC.clear();
        ta_PS.clear();
    }

    /**
     * Handles rescheduling of an appointment based on user input.
     *
     * @param event the ActionEvent triggered by the reschedule button.
     */
    @FXML
    void onRescheduleButtonClicked(ActionEvent event) {
        String firstName = tf_rFirstName.getText().trim();
        if(firstName.trim().isEmpty()) {
            ta_output.appendText("\nPlease enter first name");
            return;
        }
        String lastName = tf_rLastName.getText().trim();
        if(lastName.trim().isEmpty()) {
            ta_output.appendText("\nPlease enter last name");
            return;
        }
        if(dp_rDOB.getValue() == null) {
            ta_output.appendText("\nPlease choose a DOB");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String dobString = dp_rDOB.getValue().format(formatter);
        if(dp_rDate.getValue() == null) {
            ta_output.appendText("\nPlease choose an appointment date");
            return;
        }
        String appDateString = dp_rDate.getValue().format(formatter);
        String oldTimeslotFullString = cb_oldTimeslot.getValue();
        if(oldTimeslotFullString == null) {
            ta_output.appendText("\nPlease select your old timeslot from the list.");
            return;
        }
        String[] oldTimeSlotString = oldTimeslotFullString.split(" ");
        String newTimeslotFullString = cb_newTimeslot.getValue();
        if(newTimeslotFullString == null) {
            ta_output.appendText("\nPlease select a new timeslot from the list.");
            return;
        }
        String[] newTimeSlotString = newTimeslotFullString.split(" ");
        handleReschedule(firstName, lastName, dobString, oldTimeSlotString[0], newTimeSlotString[0], appDateString);
        clearBoxesAfterSchedulingSuccesfull();
    }

    /**
     * Handles cancellation of an appointment based on user input.
     *
     * @param event the ActionEvent triggered by the cancel button.
     */
    @FXML
    void onCancelButtonClicked(ActionEvent event) {
        String firstName = tf_cFirstName.getText().trim();
        if(firstName.trim().isEmpty()) {
            ta_output.appendText("\nPlease enter first name");
            return;
        }
        String lastName = tf_cLastName.getText().trim();
        if(lastName.trim().isEmpty()) {
            ta_output.appendText("\nPlease enter last name");
            return;
        }
        if(dp_cDOB.getValue() == null) {
            ta_output.appendText("\nPlease choose a DOB");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String dobString = dp_cDOB.getValue().format(formatter);
        if(dp_cDate.getValue() == null) {
            ta_output.appendText("\nPlease choose an appointment date");
            return;
        }
        String appDateString = dp_cDate.getValue().format(formatter);
        String timeslotFullString = cb_cTimeslot.getValue();
        if(timeslotFullString == null) {
            ta_output.appendText("\nPlease select a timeslot from the list.");
            return;
        }
        String[] timeSlotString = timeslotFullString.split(" ");
        handleCancel(firstName, lastName, dobString, appDateString, timeSlotString[0]);
        clearBoxesAfterSchedulingSuccesfull();
    }

}
