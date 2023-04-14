package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.controller.S3Controller;
import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.CustomerRepository;
import Backend_TruckSnack.TruckSnack.repository.dto.CustomerLoginDTO;
import Backend_TruckSnack.TruckSnack.util.ApiResponse;
import Backend_TruckSnack.TruckSnack.util.CustomerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {
    @Autowired
    private EntityManager entityManager;
    private final CustomerRepository customerRepository;

    private final CustomerUtil customerUtil;

    private final S3Controller s3Controller;
    private final S3Service s3Service;

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
        Optional<Customer> customer;
        customer = customerRepository.findByNameAndPhoneNumber(name,phoneNumber);
        String id;
        try {
            id = customer.map(Customer::getId).orElseThrow(() -> new RuntimeException("Customer not found"));
        } catch (RuntimeException e) {
            id = e.getMessage(); // RuntimeException의 오류 메시지를 id 변수에 저장합니다.
        }

        return id;

    }

    public CustomerLoginDTO login_find_data_service(String customer_id) throws IOException {
        Customer customerData = customerRepository.findById(customer_id);
        //customer setting Start
        Customer responseData = new Customer();
        responseData.setId(customerData.getId());
        responseData.setName(customerData.getName());
        responseData.setPhoneNumber(customerData.getPhoneNumber());
        //customer setting end

        // CustomerLoginDTO Setting start
        CustomerLoginDTO customerLoginDTO = new CustomerLoginDTO();
        customerLoginDTO.setCustomer(responseData);

        // CustomerLoginDTO Setting end

        //customer get img start
        if(customerUtil.check_img_url_util(customer_id)){
            log.info("login_find_data_service : True" );
            InputStreamResource inputStreamResource = s3Service.s3_img_customer_main_return_service(customer_id);
            String base64EncodedImage = Base64.getEncoder().encodeToString(inputStreamResource.getInputStream().readAllBytes());
            customerLoginDTO.setBase64EncodedImage(base64EncodedImage);
        }else{
            log.info("login_find_data_service : False" );
            customerLoginDTO.setBase64EncodedImage(null);
        }
        //customer get img end


        return customerLoginDTO;
    }


    public String img_upload_customer_service(MultipartFile multipartFile , String customerId) throws IOException {
        /**
         * 구매자 대표 이미지 업로드 ..
         * CustomerId 가 존재하는지 확인
         * 이미지 업로드 -> S3controller로 다시 보내서 확인해서 올리고
         * S3 url 받으면 put해서 셀러에 저장해주기
         * 확인 후 돌려주기
         */
        String return_log = null;

        if(customerUtil.check_id_util(customerId)){
            log.info("customer_id : True" );
            ResponseEntity<ApiResponse<String>> temp = s3Controller.uploadFile(multipartFile);
            log.info("img_upload_customer_service : {}",temp.getBody().getMessage());
            String s3_url = temp.getBody().getMessage();
            if(merge_customer_s3_url(customerId,s3_url)){
                log.info("Customer_imgUpload success");
                return "customer_imgUpload success : "+s3_url;
            }else{
                log.info("Customer_imgUpload 실패");
                return "Customer_imgUpload 실패";
            }
        }else{
            log.info("customer_id : False" );
            return_log ="Customer_id가 존재하지 않습니다.";
        }

        return return_log;
    }

    public boolean merge_customer_s3_url(String customer_id , String s3_url){
        Customer customer;
        customer = customerRepository.findById(customer_id);
        customer.setCustomerImgS3Url(s3_url);
        entityManager.merge(customer);
        log.info("customer_ s3 img -> 변경완료...");

        return true;
    }

}
