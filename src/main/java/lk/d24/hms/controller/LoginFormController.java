package lk.d24.hms.controller;

import animatefx.animation.JackInTheBox;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.d24.hms.bo.BOFactory;
import lk.d24.hms.bo.custom.LoginBO;
import lk.d24.hms.dto.UserDTO;
import java.io.IOException;

public class LoginFormController {

    @FXML
    private AnchorPane signInPane;

    @FXML
    private AnchorPane signUpPane;

    @FXML
    private ImageView imgView;

    @FXML
    private ImageView imgHide;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtPassShow;

    @FXML
    private PasswordField txtPass;

    @FXML
    private Label lblHint;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUserID;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnCreate;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXButton btnSignUp;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    public void btnSignInOnAction(ActionEvent actionEvent) {
        signUpPane.setVisible(false);
        signInPane.setVisible(true);
        btnSignUp.setDisable(false);
        btnSignIn.setDisable(true);
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        txtUserID.setText(loginBO.getNextID());
        lblHint.setVisible(false);
        signInPane.setVisible(false);
        signUpPane.setVisible(true);
        btnSignIn.setDisable(false);
        btnSignUp.setDisable(true);
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        resetSignIn();
        String username = txtUser.getText();
        String password = txtPass.getText();
        if(!username.isEmpty() && !password.isEmpty()){
            boolean verified = loginBO.verifyLogin(username,password);
            if(verified){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
                Parent root = loader.load();
                DashboardFormController dashboardFormController = loader.getController();
                dashboardFormController.displayUsername(username);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                new JackInTheBox(root).play();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("D24 Hostel Management");
                stage.centerOnScreen();
                stage.setResizable(false);
            }else{
                new Alert(Alert.AlertType.ERROR, "Login Failed :\nInvalid Username or Password!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Oops! Try again..\nUsername & Password cannot be empty.").show();
        }
    }

    public void lblForgotPassOnMouseClicked(MouseEvent mouseEvent) {
        lblHint.setVisible(false);
        String username = txtUser.getText();
        if (!username.isEmpty()) {
            String hint = loginBO.getPassword(username);
            if (hint != null) {
                txtUser.setStyle("-fx-background-radius: 25; -fx-border-color: #506EB4; -fx-border-radius: 25;");
                lblHint.setText("Your password : " + hint);
                lblHint.setVisible(true);
            } else {
                txtUser.setStyle("-fx-background-radius: 25; -fx-border-color: #FF0000FF; -fx-border-radius: 25;");
                new Alert(Alert.AlertType.ERROR, "Oops! Try again..\nInvalid Username..").show();
                txtUser.requestFocus();
            }
        } else {
            txtUser.setStyle("-fx-background-radius: 25; -fx-border-color: #FF0000FF; -fx-border-radius: 25;");
            new Alert(Alert.AlertType.ERROR, "Oops! Try again..\nUsername cannot be empty..").show();
            txtUser.requestFocus();
        }
    }

    public void btnCreateOnAction(ActionEvent actionEvent) {
        String id = txtUserID.getText();
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        if(!id.isEmpty() && !username.isEmpty() && !password.isEmpty()){
            boolean isAdded = loginBO.saveUser(new UserDTO(id, username, password));
            if (isAdded){
                new Alert(Alert.AlertType.INFORMATION, "You have successfully create the user account\nLogin with your Username & Password").showAndWait();
                restSignUp();
                resetSignIn();
                btnSignInOnAction(actionEvent);
            } else {
                new Alert(Alert.AlertType.ERROR, "Oops! something went wrong.").show();
                restSignUp();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Oops! Try again..\nFields cannot be empty.").show();
        }
        restSignUp();
    }

    public void imgViewOnClicked(MouseEvent mouseEvent) {
        imgView.setVisible(false);
        txtPass.setVisible(false);
        txtPassShow.setText(txtPass.getText());
        imgHide.setVisible(true);
        txtPassShow.setVisible(true);
    }

    public void imgHideOnClicked(MouseEvent mouseEvent) {
        imgHide.setVisible(false);
        txtPassShow.setVisible(false);
        imgView.setVisible(true);
        txtPass.setVisible(true);
    }

    public void resetSignIn() {
        txtPassShow.setVisible(false);
        imgHide.setVisible(false);
        imgView.setVisible(true);
        txtPass.setVisible(true);
        lblHint.setText("");
        lblHint.setVisible(false);
        txtUser.setStyle("-fx-background-radius: 25; -fx-border-color: #506EB4; -fx-border-radius: 25;");
    }

    public void restSignUp() {
        txtUserName.clear();
        txtPassword.clear();
        txtUserID.setText(loginBO.getNextID());
    }

    public void txtUserOnAction(ActionEvent actionEvent) {
        txtPass.requestFocus();
    }

    public void txtPassOnAction(ActionEvent actionEvent) {
        btnLogin.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        btnCreate.requestFocus();
    }

    public void txtPassShowOnAction(ActionEvent actionEvent) {
        btnLogin.requestFocus();
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }
}