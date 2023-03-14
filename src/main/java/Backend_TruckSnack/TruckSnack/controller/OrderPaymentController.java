package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.OrderPayment;
import Backend_TruckSnack.TruckSnack.service.OrderPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@Controller
public class OrderPaymentController {
    private final OrderPaymentService orderPaymentService;

    public OrderPaymentController(OrderPaymentService orderPaymentService) {
        this.orderPaymentService = orderPaymentService;
    }

    @PostMapping("/{customer_id}/orderPayment")
    public ResponseEntity order_payment(@RequestBody List<OrderPayment> foods , @PathVariable String customer_id) {
        // 주문 생성 로직 구현
//      log.info(foods.toString());
        log.info("customer id ={}" , customer_id);
        orderPaymentService.create_orderPayment(foods , customer_id);
        return null;
    }
}
