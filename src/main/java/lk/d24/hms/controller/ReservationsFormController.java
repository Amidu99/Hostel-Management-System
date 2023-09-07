package lk.d24.hms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.d24.hms.bo.BOFactory;
import lk.d24.hms.bo.custom.ReservationBO;
import lk.d24.hms.dto.ReservationDTO;
import lk.d24.hms.dto.RoomDTO;
import lk.d24.hms.dto.StudentDTO;
import lk.d24.hms.dto.tm.ReservationTM;
import lk.d24.hms.util.Clock;
import lk.d24.hms.util.Navigation;
import lk.d24.hms.util.RegExPatterns;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import static lk.d24.hms.controller.DashboardFormController.*;

public class ReservationsFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<ReservationTM> tblReservation;

    @FXML
    private TableColumn<?, ?> colResID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colStuID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colRoomID;

    @FXML
    private TableColumn<?, ?> colPayStatus;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TextField txtStudentID;

    @FXML
    private TextField txtName;

    @FXML
    private Label lblNextResID;

    @FXML
    private Label lblInvalidDate;

    @FXML
    private Label lblInvalidResID;

    @FXML
    private Label lblInvalidStudentID;

    @FXML
    private Label lblInvalidRoomID;

    @FXML
    private Label lblRoomFullAlert;

    @FXML
    private JFXComboBox<String> cmbPayment;

    @FXML
    private JFXComboBox<String> cmbRoomID;

    @FXML
    private Label lblClock;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtResID;

    @FXML
    private TextField txtRoomType;

    @FXML
    private TextField txtKeyMoney;

    ReservationBO reservationBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        setDefaultWarnings();
        loadPaymentStatus();
        loadRoomIDs();
        getAll();
        lblNextResID.setText(reservationBO.getNextReservationID());
        txtResID.setText(lblNextResID.getText());
        Clock.setClock(lblClock);
    }

    private void setCellValueFactory() {
        colResID.setCellValueFactory(new PropertyValueFactory<>("reservation_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStuID.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRoomID.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        colPayStatus.setCellValueFactory(new PropertyValueFactory<>("payment_status"));
    }

    private void getAll() {
        ObservableList<ReservationTM> obList = FXCollections.observableArrayList();
        List<ReservationDTO> reservationList = reservationBO.getAllReservations();
        for(ReservationDTO reservationDTO : reservationList) {
            obList.add(new ReservationTM(
                    reservationDTO.getReservation_id(),
                    reservationDTO.getDate(),
                    reservationDTO.getStudentDTO().getStudent_id(),
                    reservationDTO.getStudentDTO().getName(),
                    reservationDTO.getRoomDTO().getRoom_id(),
                    reservationDTO.getPayment_status()
            ));
        }
        tblReservation.setItems(obList);
    }

    private void loadPaymentStatus() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add(0,"Paid");
        obList.add(1,"Not Paid");
        cmbPayment.setItems(obList);
    }

    private void loadRoomIDs() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        if(nonACCount > nonACFilled){
            obList.add(obList.size(),"RM-1324");
        }else{ lblRoomFullAlert.setText(lblRoomFullAlert.getText()+" \n*Non AC rooms are fully booked.");}
        if(nonACFoodCount > nonACFoodFilled){
            obList.add(obList.size(),"RM-5467");
        }else{ lblRoomFullAlert.setText(lblRoomFullAlert.getText()+" \n*Non AC+Food rooms are fully booked.");}
        if(acCount > acFilled){
            obList.add(obList.size(),"RM-7896");
        }else{ lblRoomFullAlert.setText(lblRoomFullAlert.getText()+" \n*AC rooms are fully booked.");}
        if(acFoodCount > acFoodFilled) {
            obList.add(obList.size(), "RM-0093");
        }else{ lblRoomFullAlert.setText(lblRoomFullAlert.getText()+" \n*AC+Food rooms are fully booked.");}
        cmbRoomID.setItems(obList);
    }

    private void setDefaultWarnings() {
        lblInvalidResID.setVisible(false);
        lblInvalidStudentID.setVisible(false);
        lblInvalidRoomID.setVisible(false);
        lblInvalidDate.setVisible(false);
    }

    private void resetFields(){
        txtName.clear();
        txtDate.setValue(null);
        cmbPayment.setValue(null);
        cmbRoomID.setValue(null);
        txtStudentID.clear();
        txtDate.requestFocus();
        txtName.clear();
        txtKeyMoney.clear();
        txtRoomType.clear();
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        tblReservation.getSelectionModel().clearSelection();
        lblNextResID.setText(reservationBO.getNextReservationID());
        txtResID.setText(lblNextResID.getText());
        getAll();
        resetFields();
        setDefaultWarnings();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        setDefaultWarnings();
        if(!txtResID.getText().isEmpty() && txtDate.getValue() != null && !txtStudentID.getText().isEmpty() && !txtName.getText().isEmpty() && cmbPayment.getValue() != null && cmbRoomID.getValue() != null && !txtRoomType.getText().isEmpty()){
            boolean isResIDMatched = RegExPatterns.getReservationNoPattern().matcher(txtResID.getText()).matches();
            boolean isStudentIDMatched = RegExPatterns.getStudentIDPattern().matcher(txtStudentID.getText()).matches();
            boolean isRoomIDMatched = RegExPatterns.getRoomNoPattern().matcher(cmbRoomID.getValue()).matches();
            String reservation_id;
            LocalDate date;
            String payment_status;
            if(isResIDMatched) {
                if (isStudentIDMatched) {
                    if (isRoomIDMatched) {
                        reservation_id = txtResID.getText();
                        date = txtDate.getValue();
                        payment_status = cmbPayment.getValue();
                        StudentDTO studentDTO = reservationBO.getStudent(txtStudentID.getText());
                        RoomDTO roomDTO = reservationBO.getRoom(cmbRoomID.getValue());
                        boolean isSaved = reservationBO.saveReservation(new ReservationDTO(reservation_id, date, studentDTO, roomDTO, payment_status));
                        if (isSaved) {
                            new Alert(Alert.AlertType.INFORMATION, "Reservation added successfully..").showAndWait();
                            btnResetOnAction(event);
                        }else { new Alert(Alert.AlertType.ERROR, "Oops Something went wrong..\nReservation not added.").showAndWait();}
                    }else{ setDefaultWarnings(); lblInvalidRoomID.setVisible(true); cmbRoomID.requestFocus(); }
                }else{ setDefaultWarnings(); lblInvalidStudentID.setVisible(true); txtStudentID.requestFocus(); }
            }else{ setDefaultWarnings(); lblInvalidResID.setVisible(true); txtResID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Fields cannot be empty.").show(); }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        setDefaultWarnings();
        if(!txtResID.getText().isEmpty() && txtDate.getValue() != null && !txtStudentID.getText().isEmpty() && !txtName.getText().isEmpty() && cmbPayment.getValue() != null && cmbRoomID.getValue() != null && !txtRoomType.getText().isEmpty()){
            boolean isResIDMatched = RegExPatterns.getReservationNoPattern().matcher(txtResID.getText()).matches();
            boolean isStudentIDMatched = RegExPatterns.getStudentIDPattern().matcher(txtStudentID.getText()).matches();
            boolean isRoomIDMatched = RegExPatterns.getRoomNoPattern().matcher(cmbRoomID.getValue()).matches();
            String reservation_id;
            LocalDate date;
            String payment_status;
            if(isResIDMatched) {
                if (isStudentIDMatched) {
                    if (isRoomIDMatched) {
                        reservation_id = txtResID.getText();
                        date = txtDate.getValue();
                        payment_status = cmbPayment.getValue();
                        StudentDTO studentDTO = reservationBO.getStudent(txtStudentID.getText());
                        RoomDTO roomDTO = reservationBO.getRoom(cmbRoomID.getValue());
                        boolean isUpdated = reservationBO.updateReservation(new ReservationDTO(reservation_id, date, studentDTO, roomDTO, payment_status));
                        if (isUpdated) {
                            new Alert(Alert.AlertType.INFORMATION, "Reservation updated successfully..").showAndWait();
                            btnResetOnAction(event);
                        }else { new Alert(Alert.AlertType.ERROR, "Oops Something went wrong..\nReservation not updated.").showAndWait();}
                    }else{ setDefaultWarnings(); lblInvalidRoomID.setVisible(true); cmbRoomID.requestFocus(); }
                }else{ setDefaultWarnings(); lblInvalidStudentID.setVisible(true); txtStudentID.requestFocus(); }
            }else{ setDefaultWarnings(); lblInvalidResID.setVisible(true); txtResID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Fields cannot be empty.").show(); }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        btnDelete.setOnAction((e) -> {
            String reservation_id = txtResID.getText();
            if(!reservation_id.isEmpty()){
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean deleted = reservationBO.deleteReservation(reservation_id);
                    if(deleted){
                        new Alert(Alert.AlertType.INFORMATION, "Reservation deleted successfully.").showAndWait();
                        btnResetOnAction(event);
                    }else{new Alert(Alert.AlertType.ERROR, "Oops something went wrong..:\nPlease check Reservation_ID").show();}
                }
            }else{ new Alert(Alert.AlertType.ERROR, "Reservation_ID cannot be empty:\nPlease enter the Reservation_ID").show(); }
        });
    }

    @FXML
    void tblReservationOnAction(MouseEvent event) {
        tblReservation.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtResID.setText(newSelection.getReservation_id());
                txtDate.setValue(newSelection.getDate());
                txtStudentID.setText(newSelection.getStudent_id());
                txtName.setText(newSelection.getName());
                cmbRoomID.setValue(newSelection.getRoom_id());
                cmbPayment.setValue(newSelection.getPayment_status());
            }
        });
    }

    @FXML
    void txtDateOnAction(ActionEvent event) {
        txtStudentID.requestFocus();
    }

    @FXML
    void txtResIDOnAction(ActionEvent event) {
        txtDate.requestFocus();
    }

    @FXML
    void txtRoomIDOnAction(ActionEvent event) {
        setDefaultWarnings();
        if(cmbRoomID.getValue()!=null) {
            boolean isRoomIDMatched = RegExPatterns.getRoomNoPattern().matcher(cmbRoomID.getValue()).matches();
            String room_id;
            if (isRoomIDMatched) {
                room_id = cmbRoomID.getValue();
                RoomDTO roomDTO = reservationBO.getRoom(room_id);
                if (roomDTO != null) {
                    txtRoomType.setText(roomDTO.getType());
                    txtKeyMoney.setText(String.valueOf(roomDTO.getKey_money()));
                    cmbPayment.requestFocus();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Oops something went wrong..\nCheck the Room ID is available.").show();
                }
            } else {
                setDefaultWarnings();
                lblInvalidRoomID.setVisible(true);
                cmbRoomID.requestFocus();
            }
        }
    }

    @FXML
    void txtStudentIDOnAction(ActionEvent event) {
        setDefaultWarnings();
        if(!txtStudentID.getText().isEmpty()){
            boolean isStudentIDMatched = RegExPatterns.getStudentIDPattern().matcher(txtStudentID.getText()).matches();
            String student_id;
            if(isStudentIDMatched) {
                student_id = txtStudentID.getText();
                StudentDTO studentDTO = reservationBO.getStudent(student_id);
                if (studentDTO != null){
                    txtName.setText(studentDTO.getName());
                    cmbRoomID.requestFocus();
                }else{ new Alert(Alert.AlertType.ERROR, "Oops something went wrong..\nCheck the Student ID is available.").show(); }
            }else{ setDefaultWarnings(); lblInvalidStudentID.setVisible(true); txtStudentID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Fields cannot be empty.").show(); }
    }

    public void btnSearchClickOnAction(MouseEvent mouseEvent) {
        resetFields();
        setDefaultWarnings();
        if(!txtResID.getText().isEmpty()){
            boolean isResIDMatched = RegExPatterns.getReservationNoPattern().matcher(txtResID.getText()).matches();
            if(isResIDMatched){
                String reservation_id = txtResID.getText();
                ReservationDTO reservationDTO = reservationBO.searchReservation(reservation_id);
                if(reservationDTO!=null){
                    txtDate.setValue(reservationDTO.getDate());
                    txtStudentID.setText(reservationDTO.getStudentDTO().getStudent_id());
                    txtName.setText(reservationDTO.getStudentDTO().getName());
                    cmbRoomID.setValue(reservationDTO.getRoomDTO().getRoom_id());
                    txtRoomType.setText(reservationDTO.getRoomDTO().getType());
                    txtKeyMoney.setText(String.valueOf(reservationDTO.getRoomDTO().getKey_money()));
                    cmbPayment.setValue(reservationDTO.getPayment_status());
                }else { new Alert(Alert.AlertType.INFORMATION, "Oops no reservation details..\nCheck the Reservation ID").showAndWait();}
            }else{ setDefaultWarnings(); lblInvalidResID.setVisible(true); txtResID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Reservation ID cannot be empty.").show(); }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.navigateToDashBoard(root);
    }
}