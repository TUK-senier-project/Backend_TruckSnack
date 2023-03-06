package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.function.CustomerFunction;
import Backend_TruckSnack.TruckSnack.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @PostMapping("/customer/register")
    public ResponseEntity register_customer(@RequestBody Customer customerData)throws IOException {
        CustomerFunction customerFunction = new CustomerFunction();

        int check_flag = 0; // 성공시 0 실패시 1
        log.info("id={}, password={} , name={} , phone_number={} , location={}"
                , customerData.getId() , customerData.getPassword() , customerData.getName()
                , customerData.getPhone_number() , customerData.getLocation()
        );

        check_flag =customerFunction.customer_register_check(
                customerData.getId(),customerData.getPassword(),customerData.getName()
                ,customerData.getPhone_number() , customerData.getLocation()
        );
        if(check_flag == 0){
            log.info("회원가입이 시작됩니다.");
            if (customerService.register_customer_service(customerData).equals("Success")) {
                log.info("회원가입이 성공하였습니다.");
                return new ResponseEntity(HttpStatus.CREATED);
            }
        }
        else{
            log.info("회원가입을 위한 값 오류 확인");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @PostMapping("/customer/login")
    public ResponseEntity login_customer(@RequestBody Customer customerData)throws IOException{
        CustomerFunction customerFunction = new CustomerFunction();

        int check_flag = 0; // 성공시 0 실패시 1
        log.info("id={}, password={}"
                , customerData.getId() , customerData.getPassword()
        );

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
