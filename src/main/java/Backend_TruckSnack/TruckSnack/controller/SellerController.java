package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.function.SellerFunction;
import Backend_TruckSnack.TruckSnack.service.SellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Slf4j
@Controller
public class SellerController {
    private final SellerService sellerService;
    private ObjectMapper objectMapper = new ObjectMapper();
    public SellerController(SellerService sellerService){
        this.sellerService= sellerService;
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
            Seller responseData = new Seller();
            responseData.setId(sellerData.getId());

            String json = objectMapper.writeValueAsString(responseData);
            return ResponseEntity.ok(json);
        }else {
            log.info("Seller Login >> 로그인 실패 ... response json 생성중");
            return ResponseEntity.ok("login fail");
        }
    }
}
