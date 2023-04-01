package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.CustomerOrderPaymentRepository;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RecommendService {
    private final CustomerOrderPaymentRepository customerOrderPaymentRepository;

    private final SellerRepository sellerRepository;

    private final RankService rankService;


    public String check_order(String customer_id){

        String seller_id;
        seller_id = customerOrderPaymentRepository.findTopByCustomerIdOrderByIsCreatedDesc(customer_id).getSellerId();

        log.info("test tdst : {}",seller_id);


        if(seller_id != null){
            log.info(" check_order >> {}" , seller_id);
            return seller_id;
        }else{
            log.info(" check_order >> 없다");
            return null;
        }

    }
    public List<FoodListMapping> last_order_familiar_recommend_service(String customer_id){
        /**
         * 마지막 오더 정보 가져오기 -> 내림차순으로 정렬 ->첫번째 데이터를 가지고온다 + customer_id를 조건으로
         * 카테고리 받기 & 해당 카테고리 평점 순 리스트 불러오기
         *
         */
        String seller_id;
        seller_id = check_order(customer_id);
        log.info("{}",seller_id);
        if(seller_id != null){
            int find_category;
            List<FoodListMapping> temp_list;
            find_category = sellerRepository.findById(seller_id).getCategory();

            temp_list = rankService.rank_category_grade_service(find_category);

            if(temp_list != null){
                return temp_list;
            }else {
                log.info("last_order_familiar_recommend_service : no category List");
                return null;
            }
        }else{
            log.info("last_order_familiar_recommend_service : The customer has no orders");
            return null;
        }
    }
}
