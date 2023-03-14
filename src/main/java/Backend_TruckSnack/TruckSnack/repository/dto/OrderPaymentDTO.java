package Backend_TruckSnack.TruckSnack.repository.dto;

import Backend_TruckSnack.TruckSnack.domain.OrderPayment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderPaymentDTO {
    private Long orderPaymentId;
    private List<OrderPayment> foods;


}
