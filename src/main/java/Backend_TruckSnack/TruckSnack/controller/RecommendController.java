package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.domain.OrderPayment;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import Backend_TruckSnack.TruckSnack.service.RecommendService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class RecommendController {
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @PostMapping("/recommend/last_order_familiar_recommend/")
    @ResponseBody
    public ResponseEntity last_order_familiar_recommend_controller(@RequestBody Customer customerData) throws IOException {
        log.info("recommend controller test : {}",customerData.getId());
        List<FoodListMapping> list;
        list = recommendService.last_order_familiar_recommend_service(customerData.getId());

        if(list != null){
            String json = objectMapper.writeValueAsString(list);
            return ResponseEntity.ok(json);
        }else {
            return ResponseEntity.ok("There is no order record or the seller list could not be retrieved from the category.");
        }

    }
}
