package Backend_TruckSnack.TruckSnack.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderPaymentService {
    @Autowired
    private  EntityManager entityManager;
    private final OrderPaymentRepository orderPaymentRepository;
    private final CustomerOrderPaymentRepository customerOrderPaymentRepository;
    private final FoodRepository foodRepository;
    private final SellerRepository sellerRepository;

    /**
     * Order_State : 주문에대한 상태
     *0 : 생성중…
     * 생성중일때의 상태
     * 1 : 주문확인 대기중
     * 주문확인이 요청되기전 리스트에만 나와있는상태
     * 2: 주문취소
     * 주문을 취소한 경우
     * 3: 주문확인
     * 주문확인에 대한 요청이 확인되면
     * 4: 완료
     * 손님이 음식을 받아 완료 된 경우
     */
    private final int order_state_create = 0;
    private final int order_state_waiting = 1;
    private final int order_state_cancel = 2;
    private final int order_state_check = 3;
    private final int order_state_complete = 4;
    private String return_text = "SERVER ERROR - PLEASE CONNECT - me";

    private int get_order_state = 10;
    private CustomerOrderPayment customerOrderPayment;

    public String order_check(Long order_seq){
        /**
         * 해야할것
         * 주문번호가 유효한지 - waiting state 인지 (커스토머 오더에서)
         * 셋팅해서 번호를 3번으로 state-check 상태로 변환
         * 반환
         */
        get_order_state = find_by_orderState(order_seq);

        switch (get_order_state){
            case 0:
                return_text = "maybe create Error : please tell me";
                break;
            case 1 :
                log.info("orderSeq : {} -- is check",order_seq );
                merge_entity_content(order_state_check, order_seq);
                return_text ="orderSeq : "+order_seq+" .. check";
                break;
            case 2:
                return_text = "this order cancel";
                break;
            case 3:
                return_text = "this order already check";
                break;
            case 4:
                return_text = "this order complete";
                break;
            default:
                return_text = "server ERROR";
                break;

        }

        return return_text;

    }

    public String order_cancel(Long order_seq){

        log.info(String.valueOf(order_seq));
        get_order_state = find_by_orderState(order_seq);
        log.info(String.valueOf(get_order_state));

        switch (get_order_state){
            case 0:
                return_text = "maybe create Error : please tell me";
                break;
            case 1:
                log.info("orderSeq : {} -- is check",order_seq );
                merge_entity_content(order_state_cancel , order_seq);
                return_text = "Your order has been cancelled.";
                break;
            case 2:
                return_text = "Your order already cancelled.";
                break;
            case 3:
                String seller_phoneNumber; String seller_id;
                seller_id = customerOrderPaymentRepository.findBySeq(order_seq).getSellerId();
                log.info("order Cancel  : seller-id : {}" , seller_id);
                seller_phoneNumber = sellerRepository.findById(seller_id).getPhoneNumber();
                return_text = "Your order has already been placed - please contact us directly at this number : "+seller_phoneNumber;
                break;
            case 4:
                return_text = "Your order already complete";
                break;
            default:
                return_text = "server ERROR";
                break;
        }
        return return_text;
    }

    public String order_complete(Long order_seq){
        log.info(String.valueOf(order_seq));
        get_order_state = find_by_orderState(order_seq);
        log.info(String.valueOf(get_order_state));

        switch (get_order_state){
            case 0:
                return_text = "maybe create Error : please tell me";
                break;
            case 1:
                return_text = "Please, confirm your order first";
                break;
            case 2:
                return_text = "this order cancel";
                break;
            case 3:
                merge_entity_content(order_state_complete , order_seq);
                return_text = "order complete";
                break;
            case 4:
                return_text = "Your order already complete";
                break;
            default:
                return_text = "server ERROR";
                break;
        }
        return return_text;

    }

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
                        .orderState(order_state_create)
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
        customerOrderPayment.setOrderState(order_state_waiting);
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


    public int find_by_orderState(Long order_seq){
        CustomerOrderPayment customerOrderPayment;
        //log.info(String.valueOf(order_seq));
        customerOrderPayment = customerOrderPaymentRepository.findBySeq(order_seq);
        return customerOrderPayment.getOrderState();
    }

    public void merge_entity_content(int state , Long order_seq){
            CustomerOrderPayment customerOrderPayment;
            customerOrderPayment = customerOrderPaymentRepository.findBySeq(order_seq);
            customerOrderPayment.setOrderState(state);
            entityManager.merge(customerOrderPayment);

    }

}
