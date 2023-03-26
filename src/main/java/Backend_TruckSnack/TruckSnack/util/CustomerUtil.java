package Backend_TruckSnack.TruckSnack.util;

import Backend_TruckSnack.TruckSnack.repository.CustomerRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerUtil {
    @Autowired
    CustomerRepository customerRepository;
    public boolean check_id_util(String customer_id){
        log.info("check_id_util : customer_id : {}" , customer_id);
        if(customerRepository.existsById(customer_id)){
            log.info("check_id_util : true");
            return true;
        }else{
            log.info("check_id_util : false");
            return false;
        }
    }
}
