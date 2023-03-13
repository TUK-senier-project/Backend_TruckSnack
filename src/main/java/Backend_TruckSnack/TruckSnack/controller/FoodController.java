package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Food;
import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.service.FoodService;
import Backend_TruckSnack.TruckSnack.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Slf4j
@Controller
public class FoodController {
    private final SellerService sellerService;
    private final FoodService foodService;

    public FoodController(SellerService sellerService, FoodService foodService) {
        this.sellerService = sellerService;
        this.foodService = foodService;
    }

    @PostMapping("/food/{sellerId}/upload-food")
    @ResponseBody
    public ResponseEntity upload_food(@RequestBody Food foodData , @PathVariable String sellerId) throws IOException {

        log.info("upload_food >>sellerId={}, foodName={} , price={}" ,sellerId ,foodData.getFoodName(),foodData.getPrice());

        if(sellerService.register_seller_id_check(sellerId)){
            log.info("upload_food >> 아아디가 없습니다");
        }else{
            log.info("upload_food >> 아아디가 확인되었습니다");
            int getSeq;
            getSeq = sellerService.seq_find_seller_service(sellerId);
            log.info(String.valueOf(getSeq));
            if(foodService.upload_food_service(foodData , getSeq).equals("Success")){
                log.info("upload_food >> 업로드에 성공하였습니다.");
                return new ResponseEntity(HttpStatus.CREATED);
            }
            else {
                log.info("upload_food >> 업로드에 실패");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
