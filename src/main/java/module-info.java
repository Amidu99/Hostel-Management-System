module lk.d24.hms {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.d24.hms to javafx.fxml;
    exports lk.d24.hms;
    exports lk.d24.hms.controller;
    opens lk.d24.hms.controller to javafx.fxml;
}