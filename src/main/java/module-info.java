module com.clinic.project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.clinic.project3 to javafx.fxml;
    exports com.clinic.project3;
}