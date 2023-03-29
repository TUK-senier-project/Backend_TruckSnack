package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.repository.dto.RankCategoryNumberOfOrdersDTO;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import Backend_TruckSnack.TruckSnack.repository.mapping.RankCategoryMapping;
import Backend_TruckSnack.TruckSnack.service.RankService;
import Backend_TruckSnack.TruckSnack.util.CategoryUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class RankController {
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final RankService rankService;
    private final CategoryUtil categoryUtil;

    public RankController(RankService rankService, CategoryUtil categoryUtil) {
        this.rankService = rankService;
        this.categoryUtil = categoryUtil;
    }

    @GetMapping("/rank/category/{type}&{categoryNumber}/")
    public ResponseEntity category_rank(@PathVariable String type , @PathVariable int categoryNumber) throws JsonProcessingException {
        /**
         * 유형 : numberOfOrders , grade , reorder
         * category Number : 1~Max_category
         */

        /**
         * category 번호 체크
         * 타입체크
         * 알맞는 정보 찾아서 리스트 받기
         * 리턴
         */
        String json = null;

        if(categoryUtil.check_this_category_number(categoryNumber)){
            List<FoodListMapping> list = new ArrayList<>();

                switch (type){
                    case "numberOfOrders" :
                        list = rankService.rank_category_order_number_service(categoryNumber);
                        break;
                    case "reorder" :
                        list = rankService.rank_category_reorder_service(categoryNumber);
                        break;
                    case "grade":
                        list = rankService.rank_category_grade_service(categoryNumber);
                    default:
                        // 위의 모든 case문에 해당하지 않을 때 수행할 작업
                        json = objectMapper.writeValueAsString("타입에러 - 확인하세요");
                        break;

                }

            json = objectMapper.writeValueAsString(list);
        }else{
            return (ResponseEntity) ResponseEntity.badRequest();
        }

        return ResponseEntity.ok(json);
    }
}
