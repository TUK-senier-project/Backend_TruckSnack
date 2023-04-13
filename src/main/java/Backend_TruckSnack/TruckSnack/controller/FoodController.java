package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Food;
import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.service.FoodService;
import Backend_TruckSnack.TruckSnack.service.SellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
public class FoodController {
    private final SellerService sellerService;
    private final FoodService foodService;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public FoodController(SellerService sellerService, FoodService foodService) {
        this.sellerService = sellerService;
        this.foodService = foodService;
    }

    @PostMapping("/food/{sellerId}/upload-food")
    @ResponseBody
    public ResponseEntity upload_food(@RequestParam("foodName") String foodName, @RequestParam("price") int price ,@RequestParam("images") MultipartFile multipartFile, @PathVariable String sellerId) throws IOException {

        log.info("upload_food >>sellerId={}, foodName={} , price={}" ,sellerId ,foodName,price);

        if(sellerService.register_seller_id_check(sellerId)){
            log.info("upload_food >> 아아디가 없습니다");
        }else{
            log.info("upload_food >> 아아디가 확인되었습니다");
            int getSeq;
            getSeq = sellerService.seq_find_seller_service(sellerId);
            log.info(String.valueOf(getSeq));

            if(foodService.upload_food_service(foodName,price , getSeq, multipartFile).equals("Success")){
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

    @ResponseBody
    @PostMapping("/food/imgUpload/{sellerId}/")
    public ResponseEntity img_upload_food(@RequestParam("images") MultipartFile multipartFile , @PathVariable String sellerId)throws IOException{
        log.info("img_upload_food : sellerId : {}",sellerId);
        String return_msg = sellerService.img_upload_seller_service(multipartFile ,sellerId);
        String json = objectMapper.writeValueAsString(return_msg);
        return ResponseEntity.ok(json);
    }
}
