package lk.d24.hms.util;

import animatefx.animation.Pulse;
import animatefx.animation.SlideInLeft;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Navigation {
    public static void navigateToDashBoard(AnchorPane root) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(Navigation.class.getResource("/view/dashboard_form.fxml")));
        new Pulse(anchorPane).play();
        Scene scene = new Scene(anchorPane);
        Stage dashboard = (Stage) root.getScene().getWindow();
        dashboard.setScene(scene);
        dashboard.setTitle("D24 Hostel Management");
        dashboard.centerOnScreen();
        dashboard.setResizable(false);
    }

    public static void setWindow(AnchorPane root, String fxml) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(Navigation.class.getResource("/view/"+fxml)));
        new SlideInLeft(anchorPane).play();
        Scene scene = new Scene(anchorPane);
        Stage window = (Stage) root.getScene().getWindow();
        window.setScene(scene);
        window.centerOnScreen();
        window.setResizable(false);
        switch (fxml) {
            case "rooms_form.fxml" -> window.setTitle("D24 Rooms Manage");
            case "student_form.fxml" -> window.setTitle("D24 Students Manage");
            case "reservations_form.fxml" -> window.setTitle("D24 Reservations Manage");
            case "payments_form.fxml" -> window.setTitle("D24 Payments Manage");
            case "settings_form.fxml" -> window.setTitle("D24 User Manage");
            default -> window.setTitle("D24 Hostel Management");
        }
    }
}