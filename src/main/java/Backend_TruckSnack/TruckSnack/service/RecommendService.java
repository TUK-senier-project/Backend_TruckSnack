package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.CustomerOrderPaymentRepository;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import Backend_TruckSnack.TruckSnack.util.CategoryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RecommendService {
    private final CustomerOrderPaymentRepository customerOrderPaymentRepository;

    private final SellerRepository sellerRepository;

    private final RankService rankService;
    private final CategoryUtil categoryUtil  = new CategoryUtil();

    private final int max_category_number = categoryUtil.max_category_number();

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


    public List<FoodListMapping> last_order_new_recommend_service(String customer_id){
        String seller_id;
        seller_id = check_order(customer_id);
        log.info("{}",seller_id);
        if(seller_id != null){
            int find_category;
            List<FoodListMapping> temp_list;
            find_category = sellerRepository.findById(seller_id).getCategory();
            /**
             * 정해진 값이니까 리스트 보다는 배열로 .. 더빠를테니까
             * 배열에 max값으로 값들을 저장하고
             * 0으로 저장되는 0 하고 카테고리 번호를 삭제하는 로직으로 처리한 뒤
             * 다시 정리된 배열에서 랜덤값을 뽑아서
             * 평점순으로 리턴해주기
             */
            //List<Integer> nums = new ArrayList<>();
            log.info("test : max :{}",max_category_number);

            int[] random_array = new int[max_category_number+1];
            // 카테고리 번호를제외하고 저장한뒤
            for (int i = 1; i <= max_category_number; i++) {
                if (i != find_category) {
                    log.info("저장되는 값 , {}", i);
                    random_array[i] = i;
                }
            }
            // 0인 값들은 모두 삭제
            int index = 0;
            for (int i = 0; i < random_array.length; i++) {
                if (random_array[i] != 0) {
                    random_array[index] = random_array[i];
                    index++;
                }
            }

            random_array= Arrays.copyOf(random_array, index);


            //test -> 이러면 결국 0 번 하고 선택된 카테고리번호 하고 0 2개가 사라지니까 원래 max에서 -1 한 크기가됨
            for (int i = 0; i < max_category_number-1; i++) {
                log.info("{} random array : {}", i,random_array[i]);
            }

            //랜덤 카테고리 값 선택
            Random rand = new Random();
            int randomIndex = rand.nextInt(random_array.length);
            int randomNumber = random_array[randomIndex];
            log.info("last_order_new_recommend_service : random category : {}",randomNumber);
            temp_list = rankService.rank_category_grade_service(randomNumber);

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
