package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Communication;
import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import Backend_TruckSnack.TruckSnack.repository.mapping.OrderListMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class CommunicationController {
    private ObjectMapper objectMapper;

    @ResponseBody
    @PostMapping("/communication/review&grade/")
    public ResponseEntity review_grade(@RequestBody Communication communicationData) throws JsonProcessingException {
        log.info("review&Grade : LOG");
        log.info("customer_order_payment SEQ : {} \n grade : {} \n review : {} " , communicationData.getCustomerOrderPaymentSeq(),communicationData.getGrade() , communicationData.getReview());
        return null;
    }

}
