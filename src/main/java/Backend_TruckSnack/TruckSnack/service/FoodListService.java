package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.repository.FoodListRepository;
import Backend_TruckSnack.TruckSnack.repository.FoodRepository;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.dto.FoodDetailDTO;
import Backend_TruckSnack.TruckSnack.repository.dto.FoodSellerListDTO;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodDetailMapping;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import Backend_TruckSnack.TruckSnack.util.SellerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodListService {
    private final FoodListRepository foodListRepository;
    private final SellerRepository sellerRepository;
    private final FoodRepository foodRepository;

    private final SellerUtil sellerUtil;

    private final S3Service s3Service;

    public List<FoodListMapping> get_total_seller_service(int time){
        List<FoodListMapping> total_list;

        total_list = foodListRepository.findByDeadlineGreaterThan(time);

        return total_list;
    }
//    public List<FoodSellerListDTO> get_foodList_service(int category) throws IOException {
//        List<FoodListMapping> seller_list = null;
//        List<FoodSellerListDTO> return_seller_list = new ArrayList<>();
//        String checkCategory = String.valueOf(category);
//        String check_url, temp_seller_id;
//        InputStreamResource inputStreamResource;
//        String base64EncodedImage;
//
//        if (checkCategory != null) {
//            seller_list = foodListRepository.findByCategory(category);
//        }
//
//        // 객체 생성 및 초기화
//        FoodSellerListDTO foodSellerListDTO = new FoodSellerListDTO();
//
//        for (int i = 0; i < seller_list.size(); i++) {
//            check_url = seller_list.get(i).getSellerImgS3Url();
//            // setting
//            foodSellerListDTO.setBusinessName(seller_list.get(i).getBusinessName());
//            foodSellerListDTO.setPhoneNumber(seller_list.get(i).getPhoneNumber());
//            foodSellerListDTO.setLocation(seller_list.get(i).getLocation());
//            foodSellerListDTO.setGrade(seller_list.get(i).getGrade());
//            foodSellerListDTO.setDeadline(seller_list.get(i).getDeadline());
//
//            if (check_url != null) {
//                temp_seller_id = seller_list.get(i).getId();
//
//                // 이미지 캐싱
//                if (imageCache.containsKey(temp_seller_id)) {
//                    base64EncodedImage = imageCache.get(temp_seller_id);
//                } else {
//                    inputStreamResource = s3Service.s3_img_seller_main_return_service(temp_seller_id);
//                    base64EncodedImage = Base64.getEncoder().encodeToString(inputStreamResource.getInputStream().readAllBytes());
//                    imageCache.put(temp_seller_id, base64EncodedImage);
//                }
//
//                foodSellerListDTO.setBase64Img(base64EncodedImage);
//            } else {
//                foodSellerListDTO.setBase64Img(null);
//            }
//
//            // list add
//            return_seller

    public List<FoodSellerListDTO> get_foodList_service(int category) throws IOException {
        List<FoodListMapping> seller_list = null;
        List<FoodSellerListDTO> return_seller_list = new ArrayList<>();
        String checkCategory = String.valueOf(category);

        if(checkCategory != null) {
            seller_list = foodListRepository.findByCategory(category);
        }

        for (FoodListMapping seller : seller_list) {
            FoodSellerListDTO foodSellerListDTO = new FoodSellerListDTO();
            String check_url = seller.getSellerImgS3Url();
            String temp_seller_id = seller.getId();

            // setting
            foodSellerListDTO.setSellerId(seller.getId());
            foodSellerListDTO.setBusinessName(seller.getBusinessName());
            foodSellerListDTO.setPhoneNumber(seller.getPhoneNumber());
            foodSellerListDTO.setLocation(seller.getLocation());
            foodSellerListDTO.setGrade(seller.getGrade());
            foodSellerListDTO.setDeadline(seller.getDeadline());

            if (check_url != null) {
                InputStreamResource inputStreamResource = s3Service.s3_img_seller_main_return_service(temp_seller_id);
                String base64EncodedImage = Base64.getEncoder().encodeToString(inputStreamResource.getInputStream().readAllBytes());
                foodSellerListDTO.setBase64Img(base64EncodedImage);
            } else {
                foodSellerListDTO.setBase64Img(null);
            }

            // list add
            return_seller_list.add(foodSellerListDTO);
        }

        return return_seller_list;
    }


    public List<FoodDetailDTO> get_foodDetail_service(String sellerId) throws IOException {
        /**
        * 정리 : food_list : 이미지까지 묶인 리스트 , foodDetailList : db에 저장되어있는 필요정보 + url 불러오기
         *
         */

        //seller seq 조회
        Long sellerSeq;
        if(sellerUtil.check_id_util(sellerId)){
            sellerSeq = sellerRepository.findById(sellerId).getSeq();
        }
        else{
            return null;
        }
        System.out.println("sellerSeq = " + sellerSeq);

        //food 조회
        List<FoodDetailMapping> foodDetailList;
        foodDetailList = foodRepository.findBySellerSeq(sellerSeq);
        if(foodDetailList.isEmpty()){
            log.info("푸드 정보없음");
        }
        else{
            log.info("푸드 정보있음");
            List<FoodDetailDTO> food_list = new ArrayList<>();
            for(int i =1; i<foodDetailList.size() ; i ++){
                FoodDetailDTO foodDetailDTO = new FoodDetailDTO();
                foodDetailDTO.setFoodName(foodDetailList.get(i).getFoodName());
                foodDetailDTO.setPrice(foodDetailList.get(i).getPrice());
                foodDetailDTO.setFoodSeq(foodDetailList.get(i).getSeq());

                String s3Url=foodDetailList.get(i).getFoodImgS3URL();
                if(s3Url != null){
                    log.info("푸드 - 사진 있음");
                    InputStreamResource inputStreamResource = s3Service.s3_img_food_return_service(foodDetailList.get(i).getSeq());
                    String base64EncodedImage = Base64.getEncoder().encodeToString(inputStreamResource.getInputStream().readAllBytes());
                    foodDetailDTO.setBase64Img(base64EncodedImage);

                }
                else{
                    log.info("푸드 - 사진 없음");
                    foodDetailDTO.setBase64Img(null);
                }

                food_list.add(foodDetailDTO);

            }

            return food_list;
        }
        log.info("푸드 리스트 서비스 파트 : 푸드정보없음");
        return null;
    }

}
