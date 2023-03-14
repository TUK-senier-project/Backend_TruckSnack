package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import Backend_TruckSnack.TruckSnack.domain.OrderPayment;
import Backend_TruckSnack.TruckSnack.service.OrderPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
public class OrderPaymentController {
    private final OrderPaymentService orderPaymentService;
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    public OrderPaymentController(OrderPaymentService orderPaymentService) {
        this.orderPaymentService = orderPaymentService;
    }

    @PostMapping("/orderPayment/{customer_id}/")
    public ResponseEntity order_payment(@PathVariable String customer_id,@RequestBody List<OrderPayment> foods) throws JsonProcessingException {
        // 주문 생성 로직 구현
//      log.info(foods.toString());
        // 이유는 모르겠는데 값을 찾으면 {customer_id} 이런식으로 들어옴 .. 그래서 지우는거 만들어봄
        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(customer_id);
        while (matcher.find()) {
            String match = matcher.group(1);
            System.out.println(match);
            customer_id = match;
        }

        log.info("customer id ={}" , customer_id);

        String json;
        json = objectMapper.writeValueAsString(orderPaymentService.create_orderPayment(foods , customer_id));
        return ResponseEntity.ok(json);
    }
}
