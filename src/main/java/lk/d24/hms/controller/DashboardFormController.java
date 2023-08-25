package lk.d24.hms.controller;

import animatefx.animation.FadeInUp;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.d24.hms.bo.BOFactory;
import lk.d24.hms.bo.custom.DashboardBO;
import lk.d24.hms.util.Clock;
import lk.d24.hms.util.Navigation;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private PieChart genderChart;

    @FXML
    private BarChart<String,Integer> roomBarChart;

    @FXML
    private Label lblClock;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblUser;

    public static double maleCount = 5;
    public static double femaleCount = 4;
    public static double acCount = 5;
    public static double acFoodCount = 7;
    public static double nonACCount = 3;
    public static double nonACFoodCount = 9;

    static DashboardBO dashboardBO = (DashboardBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DASHBOARD);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.setClock(lblClock);
        lblDate.setText("  "+new SimpleDateFormat("EEEE - dd/MM/yyyy").format(Calendar.getInstance().getTime()));
        loadCurrentValues();
        setPieChart();
        setBarChart();
    }

    public void displayUsername(String username) {
        lblUser.setText("Hi "+username);
    }

    public static void loadCurrentValues() {
        maleCount = dashboardBO.getGenderCount("male");
        femaleCount = dashboardBO.getGenderCount("female");
    }

    private void setPieChart() {
        ObservableList<PieChart.Data> genderChartData = FXCollections.observableArrayList(
                new PieChart.Data("Male", maleCount),
                new PieChart.Data("Female", femaleCount));
        genderChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " students: ", data.pieValueProperty())));
        genderChart.getData().addAll(genderChartData);
        genderChartData.get(0).getNode().setStyle("-fx-pie-color: #E74C3C");
        genderChartData.get(1).getNode().setStyle("-fx-pie-color: #FFD700;");
    }

    private void setBarChart() {
        int acCount = (int) DashboardFormController.acCount;
        int acFoodCount = (int) DashboardFormController.acFoodCount;
        int nonACCount = (int) DashboardFormController.nonACCount;
        int nonACFoodCount = (int) DashboardFormController.nonACFoodCount;
        XYChart.Series<String, Integer> ac = new XYChart.Series<>();
        XYChart.Series<String, Integer> acFood = new XYChart.Series<>();
        XYChart.Series<String, Integer> nonAc = new XYChart.Series<>();
        XYChart.Series<String, Integer> nonAcFood = new XYChart.Series<>();
        ac.setName("AC Rooms");
        acFood.setName("AC+Food Rooms");
        nonAc.setName("Non AC Rooms");
        nonAcFood.setName("Non AC+Food Rooms");
        ac.getData().add(new XYChart.Data("AC",acCount));
        acFood.getData().add(new XYChart.Data("AC+Food",acFoodCount));
        nonAc.getData().add(new XYChart.Data("Non AC",nonACCount));
        nonAcFood.getData().add(new XYChart.Data("Non AC+Food",nonACFoodCount));
        roomBarChart.getData().addAll(ac,acFood,nonAc,nonAcFood);
    }

    public void btnRoomsOnAction(ActionEvent actionEvent) {
    }

    public void btnStudentsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.setWindow(root, "student_form.fxml");
    }

    public void btnReservationOnAction(ActionEvent actionEvent) {
    }

    public void btnPaymentsOnAction(ActionEvent actionEvent) {
    }

    public void btnSettingsOnAction(ActionEvent actionEvent) {
    }

    public void btnSignOutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to logout?");
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        if (alert.showAndWait().get() == ButtonType.OK) {
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login_form.fxml")));
            new FadeInUp(anchorPane).play();
            Scene scene = new Scene(anchorPane);
            Stage logWindow = (Stage) root.getScene().getWindow();
            logWindow.setScene(scene);
            logWindow.setTitle("Welcome to D24 Hostel Management");
            logWindow.centerOnScreen();
        }
    }
}