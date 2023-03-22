package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Communication;
import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import Backend_TruckSnack.TruckSnack.repository.CommunicationRepositroy;
import Backend_TruckSnack.TruckSnack.repository.CustomerOrderPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunicationService {

    private final CustomerOrderPaymentRepository customerOrderPaymentRepository;
    private final CommunicationRepositroy communicationRepositroy;
    public String review_grade_service(@RequestBody Communication communicationData)throws IOException {
        /**
         * 리뷰 & 평점
         * 1. customer_orderPayment_seq 가 존재 하는지 & Order_STATE 가 4번인지
         * 2. 평점을 기존 셀러 평점에 가중하여 sum 하는 것  (베지리안)
         * 3. 리뷰와 평점 정보에 대해 저장
         */
        if(check_orderPayment_seq(communicationData.getSeq())){
            log.info("리뷰 & 평점 Start");
            String get_review = communicationData.getReview();
            Double get_grade = communicationData.getGrade();
            //save start
            communicationRepositroy.save(
                Communication.builder()
                        .customerOrderPaymentSeq(communicationData.getCustomerOrderPaymentSeq())
                        .review(get_review)
                        .grade(get_grade)
                        .build()
            );
            //save End
        }
        else {
            log.info("주문서 확인결과 ... 문제 발생");
            return "some Error about Customer_order_payment - please , contact me";
        }


        return "Success";
    }

    public boolean check_orderPayment_seq(Long orderPayment_seq){
        CustomerOrderPayment temp_customerOrderPayment ;
        temp_customerOrderPayment = customerOrderPaymentRepository.findBySeq(orderPayment_seq);

        if ((temp_customerOrderPayment.toString() != null) || (temp_customerOrderPayment.getOrderState() != 4)) {
            log.info("없는 주문 or 주문이 완료되지 않음");
            return false;
        }else{
            log.info("확인되었습니다");
            return true;
        }

    }



}
