package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.function.SellerFunction;
import Backend_TruckSnack.TruckSnack.repository.dto.SellerLoginDTO;
import Backend_TruckSnack.TruckSnack.repository.mapping.ReviewListMapping;
import Backend_TruckSnack.TruckSnack.service.SellerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class SellerController {
    private final SellerService sellerService;
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public SellerController(SellerService sellerService){
        this.sellerService= sellerService;
    }

    @ResponseBody
    @GetMapping("/seller/review/{seller_id}")
    public ResponseEntity review_seller(@PathVariable String seller_id) throws JsonProcessingException {
        log.info("Seller review : id = {}" , seller_id);
        List<ReviewListMapping> reviewList;
        reviewList = sellerService.reviewList_seller_service(seller_id);

        String json = objectMapper.writeValueAsString(reviewList);
        return ResponseEntity.ok(json);
    }


    @ResponseBody
    @PostMapping("/seller/register")
    public ResponseEntity register_seller(@RequestBody Seller sellerData) throws IndexOutOfBoundsException, IOException {
        SellerFunction sellerFunction = new SellerFunction();
        int check_flag = 0;

        log.info("Seller Register >> id = {} , businessName = {} , password={} , content = {} , category= {} , deadline={}," +
                "phoneNumber ={} , location= {}", sellerData.getId() , sellerData.getBusinessName(),sellerData.getPassword(),
                sellerData.getContent(), sellerData.getCategory() , sellerData.getDeadline() , sellerData.getPhoneNumber() , sellerData.getLocation());

        check_flag = sellerFunction.seller_register_check(sellerData.getId() , sellerData.getPassword(),sellerData.getBusinessName(),
                sellerData.getContent(),sellerData.getCategory(),sellerData.getPhoneNumber(),sellerData.getLocation() );

        if(check_flag ==0){
            log.info("Seller Register >> 회원가입이 시작됩니다.");
            if(sellerService.register_seller_service(sellerData).equals("Success")){
                log.info("Seller Register >> 회원가입이 성공하였습니다.");
                return new ResponseEntity(HttpStatus.CREATED);
            }
        }else{
            log.info("Seller Register >> 회원가입 값 확인중 에러 확인");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping("/seller/register-check")
    public ResponseEntity register_seller_idcheck(@RequestBody Seller sellerData)throws IOException{
        log.info("Seller Register id-check >> id={}",sellerData.getId());
        if(sellerService.register_seller_id_check(sellerData.getId())){
            String json = objectMapper.writeValueAsString(sellerData.getId());
            log.info("Seller Register id-check >> id 증복 없음..response");
            return ResponseEntity.ok(json);
        }
        else {
            log.info("Seller Register id-check >> id 증복 있음..response");
            return ResponseEntity.ok("this_id_already");
        }
    }

    @ResponseBody
    @PostMapping("/seller/login")
    public ResponseEntity login_seller(@RequestBody Seller sellerData)throws IOException{
        log.info("Seller login >> id={} , password={}",sellerData.getId() , sellerData.getPassword());

        if(sellerService.login_seller_service(sellerData.getId(),sellerData.getPassword())){
            log.info("Seller Login >> 로그인 성공 ... response json 생성중");
            SellerLoginDTO sellerLoginDTO = sellerService.login_find_data_service(sellerData.getId());
            String json = objectMapper.writeValueAsString(sellerLoginDTO);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json);
        }else {
            log.info("Seller Login >> 로그인 실패 ... response json 생성중");
            return ResponseEntity.ok("login fail");
        }

    }

    @ResponseBody
    @PostMapping("/seller/idfind")
    public ResponseEntity find_id_seller(@RequestBody Seller sellerData)throws IOException{
        log.info("seller Register-find_id >>businessName={} , phoneNumber={}",sellerData.getBusinessName() , sellerData.getPhoneNumber());
        String find_id_result = String.valueOf(sellerService. id_find_seller_service(sellerData.getBusinessName() , sellerData.getPhoneNumber()));
        log.info(find_id_result);
        String json = objectMapper.writeValueAsString(find_id_result);
        return ResponseEntity.ok(json);
    }

    @ResponseBody
    @PostMapping("/seller/imgUpload/{sellerId}/")
    public ResponseEntity img_upload_seller(@RequestParam("images") MultipartFile multipartFile ,@PathVariable String sellerId)throws IOException{
        log.info("img_upload_seller : sellerId : {}",sellerId);
        String return_msg = sellerService.img_upload_seller_service(multipartFile ,sellerId);
        String json = objectMapper.writeValueAsString(return_msg);
        return ResponseEntity.ok(json);
    }


}
