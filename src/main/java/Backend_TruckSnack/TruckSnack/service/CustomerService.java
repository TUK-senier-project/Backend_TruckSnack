package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public String register_customer_service(@RequestBody Customer customerData)throws IOException{
        customerRepository.save(
                Customer.builder()
                        .id(customerData.getId())
                        .password(customerData.getPassword())
                        .name(customerData.getName())
                        .phoneNumber(customerData.getPhoneNumber())
                        .location(customerData.getLocation())
                        .build()
        );
        return "Success";
    }

    public boolean register_idCheck_customer_service(String id)throws IOException{
        Customer customer = customerRepository.findById(id);
        if(customer !=null && customer.getId().equals(id)){
            return false; //아이디가 있는경우
        }
        else{
            return true; //아이디가 없는 경우
        }
    }

    public boolean login_customer_service(String id , String password)throws IOException{
        Customer customer = customerRepository.findById(id);
        if(customer !=null&& customer.getPassword().equals(password)){
            return true;
        }
        else {
            return false;
        }

    }

    public String id_find_customer_service(String name , String phoneNumber)throws IOException{
        String customer_id_find_result;
        System.out.println(name);
        System.out.println("++++"+phoneNumber);
        customer_id_find_result = String.valueOf(customerRepository.findByNameAndPhoneNumber(name, phoneNumber));

        if(customer_id_find_result != null){
            return customer_id_find_result;
        }
        else{
            return "NotFound";
        }

    }
}
