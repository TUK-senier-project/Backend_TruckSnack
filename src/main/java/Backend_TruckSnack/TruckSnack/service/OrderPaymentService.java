package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.OrderPayment;
import Backend_TruckSnack.TruckSnack.repository.dto.OrderPaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPaymentService {

    public void create_orderPayment(List<OrderPayment> foods){
        OrderPaymentDTO orderPaymentDTO = new OrderPaymentDTO();
        orderPaymentDTO.setFoods(foods);
        List<OrderPayment> logFoods = orderPaymentDTO.getFoods();
        Long logId = orderPaymentDTO.getOrderPaymentId();
        log.info("orderPaymentID = {}" , String.valueOf(logId));
        log.info("orderPayment List={}" ,logFoods );
    }
}
