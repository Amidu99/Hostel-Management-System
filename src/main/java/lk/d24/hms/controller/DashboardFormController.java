package lk.d24.hms.controller;

import animatefx.animation.FadeInDown;
import com.jfoenix.controls.JFXTextArea;
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
    private PieChart paymentChart;

    @FXML
    private BarChart<String,Integer> roomBarChart;

    @FXML
    private BarChart<String,Integer> reservedRoomBarChart;

    @FXML
    private JFXTextArea lblAlerts;

    @FXML
    private Label lblClock;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblTotStudents;

    @FXML
    private Label lblTotRooms;

    @FXML
    private Label lblWaitingPay;

    @FXML
    private Label lblAcCount;

    @FXML
    private Label lblAcFoodCount;

    @FXML
    private Label lblNonAcCount;

    @FXML
    private Label lblNonAcFoodCount;

    public static int maleCount;
    public static int femaleCount;
    public static int acCount;
    public static int acFoodCount;
    public static int nonACCount;
    public static int nonACFoodCount;
    public static int acFilled;
    public static int acFoodFilled;
    public static int nonACFilled;
    public static int nonACFoodFilled;
    public static int paidPayments;
    public static int waitingPayments;

    static DashboardBO dashboardBO = (DashboardBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DASHBOARD);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.setClock(lblClock);
        lblDate.setText("  "+new SimpleDateFormat("EEEE - dd/MM/yyyy").format(Calendar.getInstance().getTime()));
        loadCurrentGenderValues();
        loadTotalRoomCounts();
        loadBookedRoomCounts();
        loadPaymentCounts();
        setPieChart();
        setBarChart();
        setTotLabels();
        setAlertLabel();
        setFreeRoomCounts();
    }

    public void displayUsername(String username) {
        lblUser.setText("Hi "+username+",");
    }

    private void loadTotalRoomCounts() {
        acCount = (int) dashboardBO.getRoomTypeCount("RM-7896");
        acFoodCount = (int) dashboardBO.getRoomTypeCount("RM-0093");
        nonACCount = (int) dashboardBO.getRoomTypeCount("RM-1324");
        nonACFoodCount = (int) dashboardBO.getRoomTypeCount("RM-5467");
    }

    private void loadBookedRoomCounts() {
        acFilled = dashboardBO.getRoomTypeFilledCount("RM-7896");
        acFoodFilled = dashboardBO.getRoomTypeFilledCount("RM-0093");
        nonACFilled = dashboardBO.getRoomTypeFilledCount("RM-1324");
        nonACFoodFilled = dashboardBO.getRoomTypeFilledCount("RM-5467");
    }

    private void loadPaymentCounts() {
        waitingPayments = dashboardBO.getPaymentCounts("Not Paid");
        lblWaitingPay.setText(String.valueOf(waitingPayments));
        paidPayments = dashboardBO.getPaymentCounts("Paid");
    }

    public static void loadCurrentGenderValues() {
        maleCount = (int) dashboardBO.getGenderCount("male");
        femaleCount = (int) dashboardBO.getGenderCount("female");
    }

    private void setPieChart() {
        ObservableList<PieChart.Data> genderChartData = FXCollections.observableArrayList(
            new PieChart.Data("Male", maleCount),
            new PieChart.Data("Female", femaleCount));
        genderChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " : ", data.pieValueProperty())));
        genderChart.getData().addAll(genderChartData);
        genderChart.startAngleProperty().setValue(330);
        genderChartData.get(0).getNode().setStyle("-fx-pie-color: #E74C3C");
        genderChartData.get(1).getNode().setStyle("-fx-pie-color: #FFD700;");

        ObservableList<PieChart.Data> paymentChartData = FXCollections.observableArrayList(
                new PieChart.Data("Paid", paidPayments),
                new PieChart.Data("Not-paid", waitingPayments));
        paymentChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " : ", data.pieValueProperty())));
        paymentChart.getData().addAll(paymentChartData);
        paymentChart.startAngleProperty().setValue(30);
        paymentChartData.get(0).getNode().setStyle("-fx-pie-color: #2EB82E");
        paymentChartData.get(1).getNode().setStyle("-fx-pie-color: #ff1919;");
    }

    private void setBarChart() {
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

        XYChart.Series<String, Integer> acFilled1 = new XYChart.Series<>();
        XYChart.Series<String, Integer> acFoodFilled1 = new XYChart.Series<>();
        XYChart.Series<String, Integer> nonAcFilled1 = new XYChart.Series<>();
        XYChart.Series<String, Integer> nonAcFoodFilled1 = new XYChart.Series<>();
        acFilled1.setName("AC Filled");
        acFoodFilled1.setName("AC+Food Filled");
        nonAcFilled1.setName("Non AC Filled");
        nonAcFoodFilled1.setName("Non AC+Food Filled");
        acFilled1.getData().add(new XYChart.Data("AC Filled",acFilled));
        acFoodFilled1.getData().add(new XYChart.Data("AC+Food Filled", DashboardFormController.acFoodFilled));
        nonAcFilled1.getData().add(new XYChart.Data("Non AC Filled",nonACFilled));
        nonAcFoodFilled1.getData().add(new XYChart.Data("Non AC+Food Filled",nonACFoodFilled));
        reservedRoomBarChart.getData().addAll(acFilled1,acFoodFilled1,nonAcFilled1,nonAcFoodFilled1);
    }

    private void setTotLabels() {
        lblTotStudents.setText(maleCount+femaleCount+"");
        lblTotRooms.setText(acCount+acFoodCount+nonACCount+nonACFoodCount+"");
    }

    private void setAlertLabel(){
        if(acCount==acFilled){
            lblAlerts.setText(lblAlerts.getText()+"\n*RM-7896 Rooms are fully booked");
            lblAlerts.setStyle("-fx-text-fill: #ff1919;");
        }
        if(acFoodCount==acFoodFilled){
            lblAlerts.setText(lblAlerts.getText()+"\n*RM-0093 Rooms are fully booked");
            lblAlerts.setStyle("-fx-text-fill: #ff1919;");
        }
        if(nonACCount==nonACFilled){
            lblAlerts.setText(lblAlerts.getText()+"\n*RM-1324 Rooms are fully booked");
            lblAlerts.setStyle("-fx-text-fill: #ff1919;");
        }
        if(nonACFoodCount==nonACFoodFilled){
            lblAlerts.setText(lblAlerts.getText()+"\n*RM-5467 Rooms are fully booked");
            lblAlerts.setStyle("-fx-text-fill: #ff1919;");
        }
    }

    private void setFreeRoomCounts() {
        lblAcCount.setText(acCount-acFilled+"");
        lblAcFoodCount.setText(acFoodCount-acFoodFilled+"");
        lblNonAcCount.setText(nonACCount-nonACFilled+"");
        lblNonAcFoodCount.setText(nonACFoodCount-nonACFoodFilled+"");
    }

    public void btnRoomsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.setWindow(root, "rooms_form.fxml");
    }

    public void btnStudentsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.setWindow(root, "student_form.fxml");
    }

    public void btnReservationOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.setWindow(root, "reservations_form.fxml");
    }

    public void btnPaymentsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.setWindow(root, "payments_form.fxml");
    }

    public void btnSettingsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.setWindow(root, "settings_form.fxml");
    }

    public void btnSignOutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to logout?");
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        if (alert.showAndWait().get() == ButtonType.OK) {
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login_form.fxml")));
            new FadeInDown(anchorPane).play();
            Scene scene = new Scene(anchorPane);
            Stage logWindow = (Stage) root.getScene().getWindow();
            logWindow.setScene(scene);
            logWindow.setTitle("Welcome to D24 Hostel Management");
            logWindow.centerOnScreen();
        }
    }
}