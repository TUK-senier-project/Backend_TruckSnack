package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Food;
import Backend_TruckSnack.TruckSnack.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class FoodController {
    private final SellerService sellerService;

    public FoodController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/food/{sellerId}/upload-food")
    @ResponseBody
    public ResponseEntity upload_food(@RequestBody Food foodData , @PathVariable String sellerId){
        int getSeq;
        log.info("upload_food >>sellerId={}, foodName={} , price={}" ,sellerId ,foodData.getFoodName(),foodData.getPrice());
        getSeq = sellerService.seq_find_seller_service(sellerId);
        log.info(String.valueOf(getSeq));
        if(sellerService.register_seller_id_check(sellerId)){
            log.info("upload_food >> 아아디가 확인되었습니다");
        }else{

        }
        return null;
    }
}
