package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.LikeFood;

import Backend_TruckSnack.TruckSnack.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class LikeController {
    private final LikeService likeSerive;
    public LikeController(LikeService likeSerive) {
        this.likeSerive = likeSerive;
    }

    @ResponseBody
    @PostMapping("/like/like_seller")
    public ResponseEntity like_seller(@RequestBody LikeFood likeData){
        String customer_id , seller_id;
        customer_id = likeData.getCustomerId();
        seller_id = likeData.getSellerId();
        log.info("customer_id :{} , seller_id : {}" , customer_id , seller_id);
        String save_result = likeSerive.like_seller_service(seller_id , customer_id);
        if(save_result.equals("찜하기 완료")){
            return ResponseEntity.ok("찜하기 완료");
        }else{
            return ResponseEntity.ok(save_result);
        }

    }
}
