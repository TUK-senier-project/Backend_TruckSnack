package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.function.CustomerFunction;
import Backend_TruckSnack.TruckSnack.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
public class CustomerController {
    private  final CustomerService customerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseBody
    @PostMapping("/customer/register")
    public ResponseEntity register_customer(@RequestBody Customer customerData)throws IOException {
        CustomerFunction customerFunction = new CustomerFunction();

        int check_flag = 0; // 성공시 0 실패시 1
        log.info("Customer Register >>  id={}, password={} , name={} , phone_number={} , location={}"
                , customerData.getId() , customerData.getPassword() , customerData.getName()
                , customerData.getPhoneNumber() , customerData.getLocation()
        );

        check_flag =customerFunction.customer_register_check(
                customerData.getId(),customerData.getPassword(),customerData.getName()
                ,customerData.getPhoneNumber() , customerData.getLocation()
        );
        if(check_flag == 0){
            log.info("Customer Register >> 회원가입이 시작됩니다.");
            if (customerService.register_customer_service(customerData).equals("Success")) {
                log.info("Customer Register >> 회원가입이 성공하였습니다.");
                return new ResponseEntity(HttpStatus.CREATED);
            }
        }
        else{
            log.info("Customer Register >> 회원가입을 위한 값 오류 확인");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @ResponseBody
    @PostMapping("/customer/register-check")
    public ResponseEntity register_customer_idCheck(@RequestBody Customer customerData)throws IOException{
        log.info("Customer Register-idCheck >> id={}",customerData.getId());

        if(customerService.register_idCheck_customer_service(customerData.getId())){
//            log.info("Customer Register-idCheck >> 아이디 중복 없음... response Json 생성중");
//            Customer responseData = new Customer();
//            responseData.setId(customerData.getId());
//
            String json = objectMapper.writeValueAsString(customerData.getId());
            log.info("Customer Register-idCheck >> 아이디 중복 없음... response Json 완료");

            return ResponseEntity.ok(json);
        }
        else {
            log.info("Customer Register-idCheck >> 중복아이디 확인 ... ");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @ResponseBody
    @PostMapping("/customer/login")
    public ResponseEntity login_customer(@RequestBody Customer customerData)throws IOException{
        log.info("Customer Login >> id={}, password={}"
                , customerData.getId() , customerData.getPassword()
        );

        if(customerService.login_customer_service(customerData.getId() , customerData.getPassword())){
            log.info("Customer Login >> 로그인 성공... response Json 생성중");
            Customer responseData = new Customer();
            responseData.setId(customerData.getId());

            String json = objectMapper.writeValueAsString(responseData);
            return ResponseEntity.ok(json);
        }else{
            log.info("Customer Login >> 로그인 실패...");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PostMapping("/customer/idfind")
    @ResponseBody
    public ResponseEntity find_id_customer(@RequestBody Customer customerData) throws IOException {
        log.info("Customer Register-find_id >> name={} , phoneNumber={}",customerData.getName() , customerData.getPhoneNumber());
        String find_id_result = String.valueOf(customerService.id_find_customer_service(customerData.getName() , customerData.getPhoneNumber()));
        log.info(find_id_result);
        String json = objectMapper.writeValueAsString(find_id_result);
        return ResponseEntity.ok(json);
    }
    @ResponseBody
    @PostMapping("/customer/imgUpload/{customerId}/")
    public ResponseEntity img_upload_seller(@RequestParam("images") MultipartFile multipartFile , @PathVariable String customerId)throws IOException{
        log.info("img_upload_customer : customerId : {}",customerId);
        String return_msg = customerService.img_upload_customer_service(multipartFile , customerId);
        String json = objectMapper.writeValueAsString(return_msg);
        return ResponseEntity.ok(json);
    }

}
