package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import Backend_TruckSnack.TruckSnack.domain.OrderPayment;
import Backend_TruckSnack.TruckSnack.repository.CustomerOrderPaymentRepository;
import Backend_TruckSnack.TruckSnack.repository.FoodRepository;
import Backend_TruckSnack.TruckSnack.repository.OrderPaymentRepository;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.dto.OrderPaymentDTO;
import Backend_TruckSnack.TruckSnack.repository.mapping.OrderListDetailMapping;
import Backend_TruckSnack.TruckSnack.repository.mapping.OrderListMapping;
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
    private final FoodRepository foodRepository;
    private final SellerRepository sellerRepository;

    public List<OrderListDetailMapping>order_list_detail_service(Long customer_orderPayment_seq){
        List<OrderListDetailMapping> detail_list;
        detail_list = orderPaymentRepository.findByCustomerOrderPaymentSeq(customer_orderPayment_seq);
        if(detail_list.isEmpty()){
            log.info("관련된 리스트가 없다.");
            return null;
        }else {
            log.info("리스트를 찾았습니다.");
            return detail_list;
        }
    }

    public List<OrderListMapping>order_list_service(String seller_id){
        List<OrderListMapping> order_list;
        System.out.println(seller_id);
        order_list = customerOrderPaymentRepository.findBySellerId(seller_id);

        if(order_list.isEmpty()){
            log.info("오더 리스트가 없다.");
            return null;
        }
        else{
            log.info("오더 리스트 존재");
            return order_list;
        }
    }

    public CustomerOrderPayment create_orderPayment(List<OrderPayment> foods , String customer_id){
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
        int customer_total_price = 0; // total price 총 합계
        Long find_seller_seq = null; // 셀러 번호
        String save_seller_id;
        for(OrderPayment orderPayment : orderList){
            Long findSeq = orderPayment.getFoodSeq();
            int findQuantity = orderPayment.getQuantity();
            int total_pirce;
            find_seller_seq =foodRepository.findBySeq(findSeq).getSellerSeq();
            log.info("find FoodSeq  = {}" , findSeq);
            log.info("find Quantity  = {}" , findQuantity);
            total_pirce = calc_totalPrice(findSeq , findQuantity);
            log.info("Calc TotalPrice  = {}" , total_pirce); // 각푸드별로의 토탈 프라이스임
            orderPaymentRepository.save(
                    OrderPayment.builder()
                            .foodSeq(findSeq)
                            .totalPrice(total_pirce)
                            .customerOrderPaymentSeq(customerOrderPaymentSeq)
                            .quantity(findQuantity)
                            .build()
            );
            customer_total_price += total_pirce;
            log.info("저장완료 , 다음루프로 ...");
            log.info("현재까지의 총합계 : {}" , customer_total_price);
        }

        log.info("주문 정보 집계 완료 ... 총합계 : {}" , customer_total_price);
        log.info("find seller_Seq  = {}" , find_seller_seq);
        log.info("고객 총주문수에 총 합계 저장중..");
        customerOrderPayment.setOrderTotalPrice(customer_total_price);
        //총 주문 저장용 아이디 찾기
        save_seller_id = find_by_sellerId(find_seller_seq);
        customerOrderPayment.setSellerId(save_seller_id);
        //db에 저장
        customerOrderPaymentRepository.save(customerOrderPayment);
        log.info("주문서 작성완료...");
        return customerOrderPayment;
    }

    public int calc_totalPrice(Long foodSeq , int quantity){
        int total_price= -1;
        int foodPrice;
        foodPrice = foodRepository.findBySeq(foodSeq).getPrice();
        total_price = foodPrice * quantity;
        return total_price;
    }

    public String find_by_sellerId(Long seller_seq){
        return sellerRepository.findBySeq(seller_seq).getId();
    }



}
