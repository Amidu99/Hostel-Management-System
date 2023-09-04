package lk.d24.hms.bo.custom;

import lk.d24.hms.bo.SuperBO;
import lk.d24.hms.dto.ReservationDTO;
import java.util.List;

public interface PaymentsBO extends SuperBO {
    List<ReservationDTO> getAllPendingPayments();
}