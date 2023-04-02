package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.domain.OrderPayment;
import Backend_TruckSnack.TruckSnack.repository.dto.RecommendOrderDataDTO;
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

    @PostMapping("/recommend/orderData/")
    @ResponseBody
    public ResponseEntity last_order_recommend_controller(@RequestBody RecommendOrderDataDTO recommendOrderDataDTO) throws IOException {
        String customer_id = recommendOrderDataDTO.getCustomerId();
        String select_service = recommendOrderDataDTO.getSelectService();
        log.info("recommend controller customer_id: {} , select Service : {}",customer_id ,select_service);

        String json;
        List<FoodListMapping> list;

        switch (select_service) {
            case "familiar" :
                list = recommendService.last_order_familiar_recommend_service(customer_id);
                break;
            case "new" :
                list = recommendService.last_order_new_recommend_service(customer_id);
                break;
            default:
                return ResponseEntity.ok("selectService 를 정확히 입력하세요");

        }
        if(list != null){
            json = objectMapper.writeValueAsString(list);
            return ResponseEntity.ok(json);
        }else {
            return ResponseEntity.ok("There is no order record or the seller list could not be retrieved from the category.");
        }



    }
}
