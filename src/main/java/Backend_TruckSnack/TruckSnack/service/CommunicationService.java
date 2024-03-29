package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Communication;
import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.*;
import Backend_TruckSnack.TruckSnack.repository.dto.ReviewDTO;
import Backend_TruckSnack.TruckSnack.util.CustomerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommunicationService {
    @Autowired
    private EntityManager entityManager;
    private final CustomerOrderPaymentRepository customerOrderPaymentRepository;
    private final SellerRepository sellerRepository;
    private final CommunicationRepositroy communicationRepositroy;
    private final CustomerRepository customerRepository;
    private final RatingRepository ratingRepository;

    private final CustomerUtil customerUtil;
    public String review_grade_service(@RequestBody ReviewDTO reviewDTO)throws IOException {
        log.info("review & grade Start");
        /**
         * 리뷰 & 평점
         * 1. customer_orderPayment_seq 가 존재 하는지 & Order_STATE 가 4번인지
         * 2. 평점을 기존 셀러 평점에 가중하여 sum 하는 것  (베지리안)
         * 3. 리뷰와 평점 정보에 대해 저장
         */
        log.info("OrderPayment SEQ : {}" , reviewDTO.getCommunication().getCustomerOrderPaymentSeq());
        if(check_orderPayment_seq(reviewDTO.getCommunication().getCustomerOrderPaymentSeq())){
            log.info("리뷰 & 평점 Start");
            String get_review = reviewDTO.getCommunication().getReview();
            double get_grade = reviewDTO.getCommunication().getGrade();
            double set_grade;
            int set_total_vote;
            String customer_id = reviewDTO.getCustomer_id();
            String seller_id;
            String customer_nickName;
            customer_nickName = customerRepository.findById(reviewDTO.getCustomer_id()).getName();
            seller_id = find_seller_id(reviewDTO.getCommunication().getCustomerOrderPaymentSeq());

            if(customerUtil.check_id_util(customer_id)){
                //save start
                communicationRepositroy.save(
                        Communication.builder()
                                .customerOrderPaymentSeq(reviewDTO.getCommunication().getCustomerOrderPaymentSeq())
                                .review(get_review)
                                .grade(get_grade)
                                .customerNickName(customer_nickName)
                                .sellerId(seller_id)
                                .build()
                );
                //save End
                // setting start
                set_grade = calc_grade(reviewDTO.getCommunication().getCustomerOrderPaymentSeq() , get_grade);

                // setting End

                if(merge_seller_grade(seller_id , set_grade)){
                    //성공
                    log.info("seller grade merge success");
                    return "review&grade complete";
                }else {
                    //실패
                    log.info("seller grade merge fail");
                    return "please , some Error";
                }
            }
            else{
                log.info("주문서 확인결과 ... customer_id 문제 발생");
                return "please , customer_id ERROR";
            }


        }
        else {
            log.info("주문서 확인결과 ... 문제 발생");
            return "some Error about Customer_order_payment - please , contact me";
        }
    }

    public boolean check_orderPayment_seq(Long orderPayment_seq){
        CustomerOrderPayment temp_customerOrderPayment ;
        temp_customerOrderPayment = customerOrderPaymentRepository.findBySeq(orderPayment_seq);

        if ((temp_customerOrderPayment == null) || (temp_customerOrderPayment.getOrderState() != 4)) {
            log.info("없는 주문 or 주문이 완료되지 않음");
            return false;
        }else{
            log.info("확인되었습니다");
            return true;
        }

    }

    public double calc_grade(Long orderPayment_seq , double grade){
        Seller seller;
        String seller_id ;
        double before_grade ,get_grade ,after_grade , return_grade;
        int total_vote;
        int category_number;
        get_grade = grade;

        seller_id = customerOrderPaymentRepository.findBySeq(orderPayment_seq).getSellerId();
        seller = sellerRepository.findById(seller_id);
        before_grade = seller.getGrade();
        category_number = seller.getCategory();
        total_vote = customerOrderPaymentRepository.countBySellerId(seller_id);


        after_grade = calc_grade_gro(before_grade , get_grade , total_vote , category_number);

        return_grade = after_grade;

        return return_grade;
    }

    public double calc_grade_gro(double before_grade , double get_grade , int total_vote , int category_number){
        double after_grade = 0;
        log.info("calc_grade_gro : info : before_grade : {} get_grade : {} total_vote : {} " , before_grade , get_grade , total_vote);
        // 로직
        /**
         * Bayesian 평점 계산
         * weighted rating (WR) = (v ÷ (v+m)) × R + (m ÷ (v+m)) × C
         * - R은 해당 아이템의 평균 평점 - 이후 평점
         * - v는 해당 아이템에 대한 투표 수 - 해당 가계에 대한 투표수 - total_vote+1
         * - m은 투표를 고려하는 최소 투표 수 - 10 //test 필요
         * - C는 전체 아이템의 평균 평점 // 이전 평점
         * - n 반올림 숫자 if 1이면 2번째 자리에서 반올림
         */
        int n = 1;
        double v = total_vote+1;
        double m = 10;
        double R = get_grade;
        double C = before_grade;
//        double R = before_grade+get_grade/2;
//        double C = ratingRepository.findByCategory(category_number).getServiceGrade();
        double WR;
        //Test Start
        log.info("(WR) = (v ÷ (v+m)) × R + (m ÷ (v+m)) × C");
        WR = (v / (v+m)) * R + (m / (v+m)) * C;
        log.info("{} = ({} ÷ ({}+{})) × {} + ({} ÷ ({}+{})) × {}",WR, v , v,m,R,m,v,m,C);
        after_grade = Math.round(WR * Math.pow(10, n)) / Math.pow(10, n);
        log.info("반환 값 : 결정된 평점 : {}",after_grade);
        //Test End


        // 로직
        return after_grade;
    }

    public String find_seller_id(Long customer_orderPayment_seq){
        String seller_id;
        seller_id = customerOrderPaymentRepository.findBySeq(customer_orderPayment_seq).getSellerId();
        return seller_id;
    }

    public int find_total_vote(String seller_id){
        int return_vote;
        return_vote = customerOrderPaymentRepository.countBySellerId(seller_id);
        return return_vote;
    }

    public boolean merge_seller_grade(String seller_id , double get_grade){
        Seller seller;
        seller = sellerRepository.findById(seller_id);
        seller.setGrade(get_grade);
        entityManager.merge(seller);
        log.info("seller_ 평점 업데이트 완료...");

        return true;
    }



}
