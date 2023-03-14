package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import Backend_TruckSnack.TruckSnack.domain.OrderPayment;
import Backend_TruckSnack.TruckSnack.repository.CustomerOrderPaymentRepository;
import Backend_TruckSnack.TruckSnack.repository.OrderPaymentRepository;
import Backend_TruckSnack.TruckSnack.repository.dto.OrderPaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPaymentService {
    private final OrderPaymentRepository orderPaymentRepository;
    private final CustomerOrderPaymentRepository customerOrderPaymentRepository;
    public void create_orderPayment(List<OrderPayment> foods , String customer_id){
        OrderPaymentDTO orderPaymentDTO = new OrderPaymentDTO();
        orderPaymentDTO.setFoods(foods);
        List<OrderPayment> orderList = orderPaymentDTO.getFoods();
        Long logId = orderPaymentDTO.getOrderPaymentId();
//        log.info("orderPaymentID = {}" , String.valueOf(logId));
//        log.info("orderPayment List={}" ,orderList );
        //루프 실행전 오더 정보를 다 담을 수 있는 총계산사용 오더 페이먼트 받기
        log.info("총계산서 1차 생성시작 .. customer_id >>" ,customer_id);
        CustomerOrderPayment customerOrderPayment;
        customerOrderPayment = customerOrderPaymentRepository.save(
                CustomerOrderPayment.builder()
                        .customerId(customer_id)
                        .build()
        );
        Long customerOrderPaymentSeq = customerOrderPayment.getSeq();
        log.info("생성된 Customer OrderPayment Seq ={}" , customerOrderPaymentSeq);
        log.info("총계산서 1차 생성완료 ..  customer Order Payment 생성완료");
        //루프실행
        for(OrderPayment orderPayment : orderList){
            Long findSeq = orderPayment.getFoodSeq();
            int findQuantity = orderPayment.getQuantity();
            log.info("find FoodSeq  = {}" , findSeq);
            log.info("find Quantity  = {}" , findQuantity);
            orderPaymentRepository.save(
                    OrderPayment.builder()
                            .foodSeq(findSeq)
                            .customerOrderPaymentSeq(customerOrderPaymentSeq)
                            .quantity(findQuantity)
                            .build()
            );
            log.info("저장완료 , 다음루프로 ...");
        }



    }
}
