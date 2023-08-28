package lk.d24.hms.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.d24.hms.bo.custom.UserBO;
import lk.d24.hms.dto.UserDTO;
import lk.d24.hms.dto.tm.UserTM;
import lk.d24.hms.util.Clock;
import lk.d24.hms.util.Navigation;
import lk.d24.hms.util.RegExPatterns;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private Label lblNextUserID;

    @FXML
    private Label lblInvalidUserID;

    @FXML
    private Label lblInvalidUsername;

    @FXML
    private Label lblInvalidPassword;

    @FXML
    private Label lblClock;

    @FXML
    private TextField txtUserID;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private TableView<UserTM> tblUsers;

    @FXML
    private TableColumn<?, ?> colUserID;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableColumn<?, ?> colPassword;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        setDefaultWarnings();
        getAll();
        lblNextUserID.setText(userBO.getNextUserID());
        txtUserID.setText(lblNextUserID.getText());
        Clock.setClock(lblClock);
    }

    private void setCellValueFactory() {
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void getAll() {
        ObservableList<UserTM> obList = FXCollections.observableArrayList();
        List<UserDTO> userList = userBO.getAllUsers();
        for(UserDTO userDTO : userList) {
            obList.add(new UserTM(
                    userDTO.getUserID(),
                    userDTO.getUsername(),
                    userDTO.getPassword()
            ));
        }
        tblUsers.setItems(obList);
    }

    private void setDefaultWarnings() {
        lblInvalidUserID.setVisible(false);
        lblInvalidUsername.setVisible(false);
        lblInvalidPassword.setVisible(false);
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        tblUsers.getSelectionModel().clearSelection();
        lblNextUserID.setText(userBO.getNextUserID());
        txtUserID.setText(lblNextUserID.getText());
        getAll();
        setDefaultWarnings();
        txtUsername.clear();
        txtPassword.clear();
        txtUsername.requestFocus();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        setDefaultWarnings();
        if(!txtUserID.getText().isEmpty() && !txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()){
            boolean isUserIDMatched = RegExPatterns.getUserIDPattern().matcher(txtUserID.getText()).matches();
            boolean isUserNameMatched = RegExPatterns.getUsernamePattern().matcher(txtUsername.getText()).matches();
            boolean isPasswordMatched = RegExPatterns.getPasswordPattern().matcher(txtPassword.getText()).matches();
            String userID;
            String username;
            String password;
            if(isUserIDMatched) {
                if (isUserNameMatched) {
                    if (isPasswordMatched) {
                        userID = txtUserID.getText();
                        username = txtUsername.getText();
                        password = txtPassword.getText();
                        boolean added = userBO.saveUser(new UserDTO(userID, username, password));
                        if (added) {
                            new Alert(Alert.AlertType.INFORMATION, "User added successfully..").showAndWait();
                            btnResetOnAction(event);
                        }else { new Alert(Alert.AlertType.ERROR, "Oops Something went wrong..\nUser not added.").showAndWait();}
                    }else{ setDefaultWarnings(); lblInvalidPassword.setVisible(true); txtPassword.requestFocus(); }
                }else{ setDefaultWarnings(); lblInvalidUsername.setVisible(true); txtUsername.requestFocus(); }
            }else{ setDefaultWarnings(); lblInvalidUserID.setVisible(true); txtUserID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Fields cannot be empty.").show(); }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        setDefaultWarnings();
        if(!txtUserID.getText().isEmpty() && !txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()){
            boolean isUserIDMatched = RegExPatterns.getUserIDPattern().matcher(txtUserID.getText()).matches();
            boolean isUserNameMatched = RegExPatterns.getUsernamePattern().matcher(txtUsername.getText()).matches();
            boolean isPasswordMatched = RegExPatterns.getPasswordPattern().matcher(txtPassword.getText()).matches();
            String userID;
            String username;
            String password;
            if(isUserIDMatched) {
                if (isUserNameMatched) {
                    if (isPasswordMatched) {
                        userID = txtUserID.getText();
                        username = txtUsername.getText();
                        password = txtPassword.getText();
                        boolean updated = userBO.updateUser(new UserDTO(userID, username, password));
                        if (updated) {
                            new Alert(Alert.AlertType.INFORMATION, "User updated successfully..").showAndWait();
                            btnResetOnAction(event);
                        }else { new Alert(Alert.AlertType.ERROR, "Oops Something went wrong..\nUser not updated.").showAndWait();}
                    }else{ setDefaultWarnings(); lblInvalidPassword.setVisible(true); txtPassword.requestFocus(); }
                }else{ setDefaultWarnings(); lblInvalidUsername.setVisible(true); txtUsername.requestFocus(); }
            }else{ setDefaultWarnings(); lblInvalidUserID.setVisible(true); txtUserID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Fields cannot be empty.").show(); }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        btnDelete.setOnAction((e) -> {
            String userID = txtUserID.getText();
            if(!userID.isEmpty()){
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean deleted = userBO.deleteUser(userID);
                    if(deleted){
                        new Alert(Alert.AlertType.INFORMATION, "User deleted successfully.").showAndWait();
                        btnResetOnAction(event);
                    }else{new Alert(Alert.AlertType.ERROR, "Invalid User_ID:\nPlease insert a valid User_ID").show();}
                }
            }else{ new Alert(Alert.AlertType.ERROR, "Field cannot be empty:\nPlease enter the User_ID").show(); }
        });
    }

    @FXML
    void tblUserOnClick(MouseEvent event) {
        tblUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtUserID.setText(newSelection.getUserID());
                txtUsername.setText(newSelection.getUsername());
                txtPassword.setText(newSelection.getPassword());
            }
        });
    }

    @FXML
    void txtUserIDOnAction(ActionEvent event) {
        txtUsername.requestFocus();
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnReset.requestFocus();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.navigateToDashBoard(root);
    }
}