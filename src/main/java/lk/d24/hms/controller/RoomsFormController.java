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
import lk.d24.hms.bo.custom.RoomBO;
import lk.d24.hms.dto.RoomDTO;
import lk.d24.hms.dto.tm.RoomTM;
import lk.d24.hms.util.Clock;
import lk.d24.hms.util.Navigation;
import lk.d24.hms.util.RegExPatterns;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RoomsFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtRoomID;

    @FXML
    private TextField txtKeyMoney;

    @FXML
    private TextField txtRoomType;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblInvalidRoomID;

    @FXML
    private Label lblInvalidAmount;

    @FXML
    private Label lblInvalidType;

    @FXML
    private Label lblInvalidQty;

    @FXML
    private TableView<RoomTM> tblRooms;

    @FXML
    private TableColumn<?, ?> colRoomID;

    @FXML
    private TableColumn<?, ?> colRoomType;

    @FXML
    private TableColumn<?, ?> colKeyMoney;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private Label lblClock;

    RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        setDefaultWarnings();
        getAll();
        Clock.setClock(lblClock);
    }

    private void setCellValueFactory() {
        colRoomID.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void getAll() {
        ObservableList<RoomTM> obList = FXCollections.observableArrayList();
        List<RoomDTO> roomList = roomBO.getAllRooms();
        for(RoomDTO roomDTO : roomList) {
            obList.add(new RoomTM(
                roomDTO.getRoom_id(),
                roomDTO.getType(),
                roomDTO.getKey_money(),
                roomDTO.getQty()
            ));
        }
        tblRooms.setItems(obList);
    }

    private void setDefaultWarnings() {
        lblInvalidRoomID.setVisible(false);
        lblInvalidType.setVisible(false);
        lblInvalidAmount.setVisible(false);
        lblInvalidQty.setVisible(false);
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        tblRooms.getSelectionModel().clearSelection();
        getAll();
        setDefaultWarnings();
        txtRoomID.clear();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        txtRoomID.requestFocus();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        setDefaultWarnings();
        if(!txtRoomID.getText().isEmpty() && !txtRoomType.getText().isEmpty() && !txtKeyMoney.getText().isEmpty() && !txtQty.getText().isEmpty()){
            boolean isRoomIDMatched = RegExPatterns.getRoomNoPattern().matcher(txtRoomID.getText()).matches();
            boolean isTypeMatched = RegExPatterns.getTypePattern().matcher(txtRoomType.getText()).matches();
            boolean isKeyMoneyMatched = RegExPatterns.getAmountPattern().matcher(txtKeyMoney.getText()).matches();
            boolean isQtyMatched = RegExPatterns.getQtyPattern().matcher(txtQty.getText()).matches();
            String room_id;
            String type;
            double key_money;
            int qty;
            if(isRoomIDMatched) {
                if (isTypeMatched) {
                    if (isKeyMoneyMatched) {
                        if (isQtyMatched){
                            room_id = txtRoomID.getText();
                            type = txtRoomType.getText();
                            key_money = Double.parseDouble(txtKeyMoney.getText());
                            qty = Integer.parseInt(txtQty.getText());
                            boolean added = roomBO.addRoom(new RoomDTO(room_id, type, key_money, qty));
                            if (added) {
                                new Alert(Alert.AlertType.INFORMATION, "Room type added successfully..").showAndWait();
                            btnResetOnAction(event);
                            }else { new Alert(Alert.AlertType.ERROR, "Oops Something went wrong..\nRoom type not added.").showAndWait();}
                        }else{ setDefaultWarnings(); lblInvalidQty.setVisible(true); txtQty.requestFocus(); }
                    }else{ setDefaultWarnings(); lblInvalidAmount.setVisible(true); txtKeyMoney.requestFocus(); }
                }else{ setDefaultWarnings(); lblInvalidType.setVisible(true); txtRoomType.requestFocus(); }
            }else{ setDefaultWarnings(); lblInvalidRoomID.setVisible(true); txtRoomID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Fields cannot be empty.").show(); }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        setDefaultWarnings();
        if(!txtRoomID.getText().isEmpty() && !txtRoomType.getText().isEmpty() && !txtKeyMoney.getText().isEmpty() && !txtQty.getText().isEmpty()){
            boolean isRoomIDMatched = RegExPatterns.getRoomNoPattern().matcher(txtRoomID.getText()).matches();
            boolean isTypeMatched = RegExPatterns.getTypePattern().matcher(txtRoomType.getText()).matches();
            boolean isKeyMoneyMatched = RegExPatterns.getAmountPattern().matcher(txtKeyMoney.getText()).matches();
            boolean isQtyMatched = RegExPatterns.getQtyPattern().matcher(txtQty.getText()).matches();
            String room_id;
            String type;
            double key_money;
            int qty;
            if(isRoomIDMatched) {
                if (isTypeMatched) {
                    if (isKeyMoneyMatched) {
                        if (isQtyMatched){
                            room_id = txtRoomID.getText();
                            type = txtRoomType.getText();
                            key_money = Double.parseDouble(txtKeyMoney.getText());
                            qty = Integer.parseInt(txtQty.getText());
                            boolean updated = roomBO.updateRoom(new RoomDTO(room_id, type, key_money, qty));
                            if (updated) {
                                new Alert(Alert.AlertType.INFORMATION, "Room type updated successfully..").showAndWait();
                                btnResetOnAction(event);
                            }else { new Alert(Alert.AlertType.ERROR, "Oops Something went wrong..\nRoom type not updated.").showAndWait();}
                        }else{ setDefaultWarnings(); lblInvalidQty.setVisible(true); txtQty.requestFocus(); }
                    }else{ setDefaultWarnings(); lblInvalidAmount.setVisible(true); txtKeyMoney.requestFocus(); }
                }else{ setDefaultWarnings(); lblInvalidType.setVisible(true); txtRoomType.requestFocus(); }
            }else{ setDefaultWarnings(); lblInvalidRoomID.setVisible(true); txtRoomID.requestFocus(); }
        }else{ new Alert(Alert.AlertType.ERROR, "Fields cannot be empty.").show(); }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        btnDelete.setOnAction((e) -> {
            String room_id = txtRoomID.getText();
            if(!room_id.isEmpty()){
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean deleted = roomBO.deleteRoom(room_id);
                    if(deleted){
                        new Alert(Alert.AlertType.INFORMATION, "Room type deleted successfully.").showAndWait();
                        btnResetOnAction(event);
                    }else{new Alert(Alert.AlertType.ERROR, "Invalid Room_ID:\nPlease insert a valid Room_ID").show();}
                }
            }else{ new Alert(Alert.AlertType.ERROR, "Field cannot be empty:\nPlease enter the Room_ID").show(); }
        });
    }

    @FXML
    void tblRoomsOnClick(MouseEvent event) {
        tblRooms.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtRoomID.setText(newSelection.getRoom_id());
                txtRoomType.setText(newSelection.getType());
                txtKeyMoney.setText(String.valueOf(newSelection.getKey_money()));
                txtQty.setText(String.valueOf(newSelection.getQty()));
            }
        });
    }

    @FXML
    void txtRoomIDOnAction(ActionEvent event) {
        txtRoomType.requestFocus();
    }

    @FXML
    void txtRoomTypeOnAction(ActionEvent event) {
        txtKeyMoney.requestFocus();
    }

    @FXML
    void txtKeyMoneyOnAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnReset.requestFocus();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.navigateToDashBoard(root);
    }
}