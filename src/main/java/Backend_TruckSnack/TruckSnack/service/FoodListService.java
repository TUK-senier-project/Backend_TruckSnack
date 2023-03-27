package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.repository.FoodListRepository;
import Backend_TruckSnack.TruckSnack.repository.FoodRepository;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodDetailMapping;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodListService {
    private final FoodListRepository foodListRepository;
    private final SellerRepository sellerRepository;
    private final FoodRepository foodRepository;

    public List<FoodListMapping> get_total_seller_service(int time){
        List<FoodListMapping> total_list;

        total_list = foodListRepository.findByDeadlineGreaterThan(time);

        return total_list;
    }

    public List<FoodListMapping> get_foodList_service(int category){
            List<FoodListMapping> seller_list = null;
            String checkCategory = String.valueOf(category);
            if(checkCategory != null){
                seller_list = foodListRepository.findByCategory(category);
            }

            return seller_list;
    }

    public List<FoodDetailMapping> get_foodDetail_service(String sellerId){
        /**
         * 해야할것
         * sellerId로 sellerSeq 조회 - sellerRepository에 있음
         * 조회한 seq로 foodRepository에서 조회
         * 해당 리스트를 반환
         */

        //seller seq 조회
        Long sellerSeq;
        sellerSeq = sellerRepository.findById(sellerId).getSeq();
        System.out.println("sellerSeq = " + sellerSeq);

        //food 조회
        List<FoodDetailMapping> foodDetailList;
        foodDetailList = foodRepository.findBySellerSeq(sellerSeq);
        if(foodDetailList.isEmpty()){
            log.info("푸드 정보없음");
        }
        else{
            log.info("푸드 정보있음");
            return foodDetailList;
        }
        log.info("푸드 리스트 서비스 파트 : 이거 왜나옴?");
        return foodDetailList;
    }

}
