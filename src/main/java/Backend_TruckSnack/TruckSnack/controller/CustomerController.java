package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.function.CustomerFunction;
import Backend_TruckSnack.TruckSnack.service.CustomerService;
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
public class CustomerController {
    private  final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseBody
    @PostMapping("/customer/login")
    public ResponseEntity register_user(@RequestBody Customer customerData)throws IOException {
        String customer_id = customerData.getId();
        CustomerFunction customerFunction = new CustomerFunction();

        int check_flag = 0; // 성공시 0 실패시 1
        log.info("id={}, password={} , name={} , phone_number={} , location={}"
                , customerData.getId() , customerData.getPassword() , customerData.getName()
                , customerData.getPhone_number() , customerData.getLocation()
        );

        /**
         * 컨트롤러에서 처리할 것들
         * 아이디 : 공백 확인 , 특수문자 확인 , 길이 확인(5~15)
         * 패스워드 : 공백확인,길이확인(20) , 문자가 영+숫 , (한예외처리)
         * 이름 : 공백확인 , 길이 확인(10글자 이내) ,
         * 핸드폰 번호 : 11 글자인지 , 숫자로만 이루어져 있는지
         * 지역 : 50글자 이내인지 확인
         */

        check_flag = customerFunction.customer_id_check(customer_id);


        if (customerService.register_user_service(customerData).equals("Success")) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
