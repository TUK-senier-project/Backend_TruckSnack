package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.controller.S3Controller;
import Backend_TruckSnack.TruckSnack.domain.Food;
import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.FoodRepository;
import Backend_TruckSnack.TruckSnack.util.ApiResponse;
import Backend_TruckSnack.TruckSnack.util.SellerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FoodService {
    private final FoodRepository foodRepository;
    private final S3Controller s3Controller;
    public String upload_food_service(String foodName , int price, int sellerSeq  , MultipartFile multipartFile)throws IOException {

        ResponseEntity<ApiResponse<String>> temp = s3Controller.uploadFile(multipartFile);
        log.info("img_upload_food_service : {}",temp.getBody().getMessage());
        String s3_url = temp.getBody().getMessage();

        foodRepository.save(
                Food.builder()
                        .foodName(foodName)
                        .price(price)
                        .sellerSeq((long) sellerSeq)
                        .foodImgS3Img(s3_url)
                        .build()
        );
        return "Success";
    }


}
