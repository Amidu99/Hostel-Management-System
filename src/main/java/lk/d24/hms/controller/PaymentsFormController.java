package lk.d24.hms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.d24.hms.bo.BOFactory;
import lk.d24.hms.bo.custom.PaymentsBO;
import lk.d24.hms.dto.ReservationDTO;
import lk.d24.hms.dto.tm.PaymentsTM;
import lk.d24.hms.util.Clock;
import lk.d24.hms.util.Navigation;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentsFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblClock;

    @FXML
    private TableView<PaymentsTM> tblPayments;

    @FXML
    private TableColumn<?, ?> colResID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colRoomID;

    @FXML
    private TableColumn<?, ?> colKeyMoney;

    @FXML
    private TableColumn<?, ?> colStuID;

    @FXML
    private TableColumn<?, ?> colName;

    PaymentsBO paymentsBO = (PaymentsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.navigateToDashBoard(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        Clock.setClock(lblClock);
    }

    private void setCellValueFactory() {
        colResID.setCellValueFactory(new PropertyValueFactory<>("reservation_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRoomID.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colStuID.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void getAll() {
        ObservableList<PaymentsTM> obList = FXCollections.observableArrayList();
        List<ReservationDTO> roomList = paymentsBO.getAllPendingPayments();
        for(ReservationDTO reservationDTO : roomList) {
            obList.add(new PaymentsTM(
                    reservationDTO.getReservation_id(),
                    reservationDTO.getDate(),
                    reservationDTO.getRoomDTO().getRoom_id(),
                    reservationDTO.getRoomDTO().getKey_money(),
                    reservationDTO.getStudentDTO().getStudent_id(),
                    reservationDTO.getStudentDTO().getName()
            ));
        }
        tblPayments.setItems(obList);
    }
}