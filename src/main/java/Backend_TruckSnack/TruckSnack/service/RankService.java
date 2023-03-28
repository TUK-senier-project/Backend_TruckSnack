package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.repository.CustomerOrderPaymentRepository;
import Backend_TruckSnack.TruckSnack.repository.FoodListRepository;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.dto.RankCategoryNumberOfOrdersDTO;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import Backend_TruckSnack.TruckSnack.repository.mapping.RankCategoryMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RankService {
    private final FoodListRepository foodListRepository;
    private final SellerRepository sellerRepository;
    private final CustomerOrderPaymentRepository customerOrderPaymentRepository;

    public List<FoodListMapping> rank_category_order_number_service(int category_number){
        /**
         * 카테고리 - 주문수
         * 해당 카테고리에 셀러들을 조회 - 리스트
         * 주문수와 아이디를 매핑 하는 리스트 작성(DTO)
         * 정렬
         *
         */
        List<RankCategoryMapping> order_id_list;
        List<RankCategoryNumberOfOrdersDTO> order_number_list = new ArrayList<RankCategoryNumberOfOrdersDTO>();
        order_id_list = sellerRepository.findByCategory(category_number);
        int temp_number_of_orders;

        for(int i=0; i<order_id_list.size(); i++){
            temp_number_of_orders = customerOrderPaymentRepository.countBySellerId(order_id_list.get(i).getId());
            log.info("test : id : {} , count : {}",order_id_list.get(i).getId() , temp_number_of_orders);

            //DTO setting
            RankCategoryNumberOfOrdersDTO rankCategoryNumberOfOrdersDTO = new RankCategoryNumberOfOrdersDTO(); // 객체 생성
            rankCategoryNumberOfOrdersDTO.setId(order_id_list.get(i).getId());
            rankCategoryNumberOfOrdersDTO.setNumberOfOrders(temp_number_of_orders);
            log.info("dto setting");

            //List setting
            if (!order_number_list.contains(rankCategoryNumberOfOrdersDTO)) {
                order_number_list.add(i, rankCategoryNumberOfOrdersDTO);
            }
//            log.info("지금 들어가는 정보들");
//
//            order_number_list.forEach(item ->{
//                log.info("id : {} , 주문 수 : {}" , item.getId() , item.getNumberOfOrders());
//            });
        }

        log.info("주문수 많은 순으로 정렬 시작");
        order_number_list.forEach(item ->{
            log.info("id : {} , 주문 수 : {}" , item.getId() , item.getNumberOfOrders());
        });

        Collections.sort(order_number_list, new Comparator<RankCategoryNumberOfOrdersDTO>() {
            @Override
            public int compare(RankCategoryNumberOfOrdersDTO o1, RankCategoryNumberOfOrdersDTO o2) {
                // 주문수가 많은 순으로 정렬
                return o2.getNumberOfOrders() - o1.getNumberOfOrders();
            }
        });

        /**
         * 리턴용 리스트 작성
         */
        List<FoodListMapping> return_list = new ArrayList<>();
        //정렬 완료
        log.info("주문수 많은 순으로 정렬 완료 - 리턴용 리스트 작성 시작");
        order_number_list.forEach(item ->{
            FoodListMapping temp;
            log.info("id : {} , 주문 수 : {}" , item.getId() , item.getNumberOfOrders());
            temp=  foodListRepository.findById(item.getId());
            return_list.add(temp);

        });

        return return_list;
    }

    public List<FoodListMapping> rank_category_grade_service(int categoryNumber){
        /**
         * 카테고리 - 평점
         * 일단 매핑 - id , grade
         * 정렬 - compare
         */
        List<RankCategoryMapping> seller_list;

        seller_list= sellerRepository.findByCategory(categoryNumber);
        System.out.println("정렬전");
        seller_list.forEach(item -> {
            System.out.println(item.getId() +"  "+ item.getGrade());

        });
        System.out.println("정렬후 - 평점이 높은 순");
        Collections.sort(seller_list, new Comparator<RankCategoryMapping>() {
            @Override
            public int compare(RankCategoryMapping o1, RankCategoryMapping o2) {
                return Double.compare(o2.getGrade(),o1.getGrade());
            }
        });
        /**
         * 리턴용 리스트 작성
         */
        List<FoodListMapping> return_list = new ArrayList<>();
        //정렬 완료
        log.info("주문수 많은 순으로 정렬 완료 - 리턴용 리스트 작성 시작");
        seller_list.forEach(item ->{
            FoodListMapping temp;
            log.info("id : {} , 주문 수 : {}" , item.getId() , item.getGrade());
            temp=  foodListRepository.findById(item.getId());
            return_list.add(temp);

        });

        return return_list;
    }
}
