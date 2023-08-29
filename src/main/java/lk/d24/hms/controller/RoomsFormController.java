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
import java.net.URL;
import java.util.List;
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
    private JFXButton btnBack;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

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

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void tblRoomsOnClick(MouseEvent event) {

    }

    @FXML
    void txtRoomIDOnAction(ActionEvent event) {

    }

    @FXML
    void txtRoomTypeOnAction(ActionEvent event) {

    }

    @FXML
    void txtKeyMoneyOnAction(ActionEvent event) {

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }
}