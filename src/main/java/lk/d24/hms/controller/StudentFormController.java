package lk.d24.hms.controller;

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
import lk.d24.hms.bo.custom.StudentBO;
import lk.d24.hms.dto.StudentDTO;
import lk.d24.hms.dto.tm.StudentTM;
import lk.d24.hms.util.Clock;
import lk.d24.hms.util.Navigation;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import lk.d24.hms.util.RegExPatterns;

public class StudentFormController implements Initializable {
    @FXML
    private AnchorPane root;

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
    private JFXComboBox<String> cmbGender;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        setDefaultWarnings();
        loadGenderTypes();
        getAll();
        lblNextStudentID.setText(studentBO.getNextStudentID());
        txtStudentID.setText(lblNextStudentID.getText());
        Clock.setClock(lblClock);
    }

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

    private void loadGenderTypes() {
        ObservableList <String> obList = FXCollections.observableArrayList();
        obList.add(0,"Male");
        obList.add(1,"Female");
        cmbGender.setItems(obList);
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        tblStudent.getSelectionModel().clearSelection();
        lblNextStudentID.setText(studentBO.getNextStudentID());
        txtStudentID.setText(lblNextStudentID.getText());
        getAll();
        setDefaultWarnings();
        resetFields();
    }

    private void resetFields(){
        txtName.clear();
        txtBirthday.setValue(null);
        cmbGender.setValue(null);
        txtTel.clear();
        txtAddress.clear();
        txtName.requestFocus();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        setDefaultWarnings();
        if(!txtStudentID.getText().isEmpty() && !txtName.getText().isEmpty() && txtBirthday.getValue() != null && cmbGender.getValue() != null && !txtTel.getText().isEmpty() && !txtAddress.getText().isEmpty()){
            boolean isStudentIDMatched = RegExPatterns.getStudentIDPattern().matcher(txtStudentID.getText()).matches();
            boolean isNameMatched = RegExPatterns.getNamePattern().matcher(txtName.getText()).matches();
            boolean isBirthdayMatched = RegExPatterns.birthdayPattern(txtBirthday.getValue());
            boolean isTelMatched = RegExPatterns.getTelPattern().matcher(txtTel.getText()).matches();
            boolean isAddressMatched = RegExPatterns.getAddressPattern().matcher(txtAddress.getText()).matches();
            String student_id;
            String name;
            LocalDate birthday;
            String gender;
            String contact;
            String address;
            if(isStudentIDMatched) {
                if (isNameMatched) {
                    if (isBirthdayMatched) {
                        if (isTelMatched) {
                            if (isAddressMatched) {
                                student_id = txtStudentID.getText();
                                name = txtName.getText();
                                birthday = txtBirthday.getValue();
                                gender = cmbGender.getValue();
                                contact = txtTel.getText();
                                address = txtAddress.getText();
                                boolean isSaved = studentBO.saveStudent(new StudentDTO(student_id, name, birthday, gender, contact, address));
                                if (isSaved) {
                                    new Alert(Alert.AlertType.INFORMATION, "Student added successfully..").showAndWait();
                                    btnResetOnAction(actionEvent);
                                }else { new Alert(Alert.AlertType.ERROR, "Oops Something went wrong..\nStudent not added.").showAndWait();}
                            }else{ setDefaultWarnings(); lblInvalidAddress.setVisible(true); txtAddress.requestFocus(); }
                        }else{ setDefaultWarnings(); lblInvalidTel.setVisible(true); txtTel.requestFocus(); }
                    }else{ setDefaultWarnings(); lblInvalidBirthday.setVisible(true); txtBirthday.requestFocus(); }
                }else{ setDefaultWarnings(); lblInvalidName.setVisible(true); txtName.requestFocus(); }
            }else{ setDefaultWarnings(); lblInvalidStudentID.setVisible(true); txtStudentID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Fields cannot be empty.").show(); }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        setDefaultWarnings();
        if(!txtStudentID.getText().isEmpty() && !txtName.getText().isEmpty() && txtBirthday.getValue() != null && cmbGender.getValue() != null && !txtTel.getText().isEmpty() && !txtAddress.getText().isEmpty()){
            boolean isStudentIDMatched = RegExPatterns.getStudentIDPattern().matcher(txtStudentID.getText()).matches();
            boolean isNameMatched = RegExPatterns.getNamePattern().matcher(txtName.getText()).matches();
            boolean isBirthdayMatched = RegExPatterns.birthdayPattern(txtBirthday.getValue());
            boolean isTelMatched = RegExPatterns.getTelPattern().matcher(txtTel.getText()).matches();
            boolean isAddressMatched = RegExPatterns.getAddressPattern().matcher(txtAddress.getText()).matches();
            String student_id;
            String name;
            LocalDate birthday;
            String gender;
            String contact;
            String address;
            if(isStudentIDMatched) {
                if (isNameMatched) {
                    if (isBirthdayMatched) {
                        if (isTelMatched) {
                            if (isAddressMatched) {
                                student_id = txtStudentID.getText();
                                name = txtName.getText();
                                birthday = txtBirthday.getValue();
                                gender = cmbGender.getValue();
                                contact = txtTel.getText();
                                address = txtAddress.getText();
                                boolean isUpdated = studentBO.updateStudent(new StudentDTO(student_id, name, birthday, gender, contact, address));
                                if (isUpdated) {
                                    new Alert(Alert.AlertType.INFORMATION, "Student updated successfully..").showAndWait();
                                    btnResetOnAction(actionEvent);
                                }else { new Alert(Alert.AlertType.ERROR, "Oops Something went wrong..\nStudent not updated.").showAndWait();}
                            }else{ setDefaultWarnings(); lblInvalidAddress.setVisible(true); txtAddress.requestFocus(); }
                        }else{ setDefaultWarnings(); lblInvalidTel.setVisible(true); txtTel.requestFocus(); }
                    }else{ setDefaultWarnings(); lblInvalidBirthday.setVisible(true); txtBirthday.requestFocus(); }
                }else{ setDefaultWarnings(); lblInvalidName.setVisible(true); txtName.requestFocus(); }
            }else{ setDefaultWarnings(); lblInvalidStudentID.setVisible(true); txtStudentID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Fields cannot be empty.").show(); }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        btnDelete.setOnAction((e) -> {
            String student_id = txtStudentID.getText();
            if(!student_id.isEmpty()){
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean deleted = studentBO.deleteStudent(student_id);
                    if(deleted){
                        new Alert(Alert.AlertType.INFORMATION, "Student deleted successfully.").showAndWait();
                        btnResetOnAction(actionEvent);
                    }else{new Alert(Alert.AlertType.ERROR, "Invalid Student_ID:\nPlease insert a valid Student_ID").show();}
                }
            }else{ new Alert(Alert.AlertType.ERROR, "Field cannot be empty:\nPlease enter the Student_ID").show(); }
        });
    }

    public void tblStudentOnAction(MouseEvent mouseEvent) {
        tblStudent.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtStudentID.setText(newSelection.getStudent_id());
                txtName.setText(newSelection.getName());
                txtBirthday.setValue(newSelection.getBirthday());
                cmbGender.setValue(newSelection.getGender());
                txtTel.setText(newSelection.getContact());
                txtAddress.setText(newSelection.getAddress());
            }
        });
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigateToDashBoard(root);
    }

    public void txtStudentIDOnAction(ActionEvent actionEvent) {
        txtName.requestFocus();
    }

    public void txtAddressOnAction(ActionEvent actionEvent) {
        btnReset.requestFocus();
    }

    public void txtNameOnAction(ActionEvent actionEvent) {
        txtBirthday.requestFocus();
    }

    public void txtTelOnAction(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void txtBirthdayOnAction(ActionEvent actionEvent) {
        cmbGender.requestFocus();
    }

    public void btnSearchClickOnAction(MouseEvent mouseEvent) {
        resetFields();
        setDefaultWarnings();
        if(!txtStudentID.getText().isEmpty()){
            boolean isStudentIDMatched = RegExPatterns.getStudentIDPattern().matcher(txtStudentID.getText()).matches();
            if(isStudentIDMatched){
                String student_id = txtStudentID.getText();
                StudentDTO studentDTO = studentBO.searchStudent(student_id);
                if(studentDTO!=null){
                    txtName.setText(studentDTO.getName());
                    txtBirthday.setValue(studentDTO.getBirthday());
                    txtAddress.setText(studentDTO.getAddress());
                    txtTel.setText(studentDTO.getContact());
                    cmbGender.setValue(studentDTO.getGender());
                }else { new Alert(Alert.AlertType.INFORMATION, "Oops no student details..\nCheck the Student ID").showAndWait();}
            }else{ setDefaultWarnings(); lblInvalidStudentID.setVisible(true); txtStudentID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Student ID cannot be empty.").show(); }
    }
}