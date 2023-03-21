package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.LikeFood;

import Backend_TruckSnack.TruckSnack.repository.dto.MyLikeListDTO;
import Backend_TruckSnack.TruckSnack.service.LikeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private ObjectMapper objectMapper = new ObjectMapper();
    private String customer_id , seller_id;
    @ResponseBody
    @PostMapping("/like/like_seller")
    public ResponseEntity like_seller(@RequestBody LikeFood likeData){

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

    @ResponseBody
    @PostMapping("/like/like_cancel_seller")
    public ResponseEntity like_cancel_seller(@RequestBody LikeFood likeData){
        customer_id = likeData.getCustomerId();
        seller_id = likeData.getSellerId();
        log.info("customer_id :{} , seller_id : {}" , customer_id , seller_id);

        String save_result = likeSerive.like_cancel_service(seller_id , customer_id);
        if(save_result.equals("취소하였습니다.")){
            return ResponseEntity.ok("취소하였습니다.");
        }else{
            return ResponseEntity.ok(save_result);
        }

    }

    @ResponseBody
    @PostMapping("/like/my_like/")
    public ResponseEntity<String> my_like(@RequestBody LikeFood likeData) throws JsonProcessingException {
        String customer_id;
        MyLikeListDTO myLikeListDTO;

        customer_id = likeData.getCustomerId();
        log.info("customer_id :{}" , customer_id);
        myLikeListDTO= likeSerive.my_like_service(customer_id);

        String json = objectMapper.writeValueAsString(myLikeListDTO);
        System.out.println(json);


        return ResponseEntity.ok(json);

    }



}
