package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Communication;
import Backend_TruckSnack.TruckSnack.service.CommunicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    //private ObjectMapper objectMapper;
    private final CommunicationService communicationService;

    public CommunicationController(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @ResponseBody
    @PostMapping("/communication/review&grade/")
    public ResponseEntity review_grade(@RequestBody Communication communicationData) throws IOException {
        log.info("review&Grade : LOG");
        log.info("customer_order_payment \n SEQ : {} \n grade : {} \n review : {} " , communicationData.getCustomerOrderPaymentSeq(),communicationData.getGrade() , communicationData.getReview());
        communicationService.review_grade_service(communicationData);



        return null;
    }

}
