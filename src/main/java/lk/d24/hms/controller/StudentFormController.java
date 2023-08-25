package lk.d24.hms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.d24.hms.bo.BOFactory;
import lk.d24.hms.bo.custom.StudentBO;
import lk.d24.hms.dto.StudentDTO;
import lk.d24.hms.dto.tm.StudentTM;
import lk.d24.hms.util.Navigation;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

public class StudentFormController {
    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnBack;

    @FXML
    private TableView<StudentTM> tblStudent;

    @FXML
    private TableColumn<?, ?> colStuID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colBirthday;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TextField txtStudentID;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private Label lblNextStudentID;

    @FXML
    private JFXComboBox<?> cmbGender;

    @FXML
    private Label lblInvalidStudentID;

    @FXML
    private Label lblInvalidAddress;

    @FXML
    private Label lblInvalidName;

    @FXML
    private Label lblInvalidTel;

    @FXML
    private Label lblClock;

    @FXML
    private DatePicker txtBirthday;

    @FXML
    private Label lblInvalidBirthday;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);

    private void setCellValueFactory() {
        colStuID.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void setDefaultWarnings(){
        lblInvalidStudentID.setVisible(false);
        lblInvalidName.setVisible(false);
        lblInvalidBirthday.setVisible(false);
        lblInvalidTel.setVisible(false);
        lblInvalidAddress.setVisible(false);
    }

    private void getAll() {
            ObservableList<StudentTM> obList = FXCollections.observableArrayList();
            List<StudentDTO> studentList = studentBO.getAllStudents();
            for(StudentDTO studentDTO : studentList) {
                obList.add(new StudentTM(
                        studentDTO.getStudent_id(),
                        studentDTO.getName(),
                        studentDTO.getBirthday(),
                        studentDTO.getGender(),
                        studentDTO.getContact(),
                        studentDTO.getAddress()
                ));
            }
            tblStudent.setItems(obList);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigateToDashBoard(root);
    }

    public void txtStudentIDOnAction(ActionEvent actionEvent) {

    }

    public void txtAddressOnAction(ActionEvent actionEvent) {
    }

    public void txtNameOnAction(ActionEvent actionEvent) {
    }

    public void txtTelOnAction(ActionEvent actionEvent) {
    }

    public void txtBirthdayOnAction(ActionEvent actionEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void tblStudentOnAction(MouseEvent mouseEvent) {
    }
}