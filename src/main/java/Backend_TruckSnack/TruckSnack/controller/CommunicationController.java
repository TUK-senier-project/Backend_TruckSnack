package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Communication;
import Backend_TruckSnack.TruckSnack.repository.dto.ReviewDTO;
import Backend_TruckSnack.TruckSnack.service.CommunicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Slf4j
@Controller
public class CommunicationController {
    private final ObjectMapper objectMapper;
    private final CommunicationService communicationService;

    public CommunicationController(ObjectMapper objectMapper, CommunicationService communicationService) {
        this.objectMapper = objectMapper;
        this.communicationService = communicationService;
    }

    @ResponseBody
    @PostMapping("/communication/review&grade/")
    public ResponseEntity review_grade(@RequestBody ReviewDTO reviewDTO) throws IOException {
        String return_text;
        log.info("review&Grade : LOG");
        log.info("customer_order_payment \n SEQ : {} \n grade : {} \n review : {} \n customer_id :{}" , reviewDTO.getCommunication().getCustomerOrderPaymentSeq() , reviewDTO.getCommunication().getGrade(), reviewDTO.getCommunication().getReview(), reviewDTO.getCustomer_id());
        return_text = communicationService.review_grade_service(reviewDTO);
        String json = objectMapper.writeValueAsString(return_text);

        return ResponseEntity.ok(json);
    }

}
