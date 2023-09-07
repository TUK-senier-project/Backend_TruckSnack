package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Admin;
import Backend_TruckSnack.TruckSnack.domain.Communication;
import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.repository.AdminRepositroy;
import Backend_TruckSnack.TruckSnack.repository.CommunicationRepositroy;
import Backend_TruckSnack.TruckSnack.repository.CustomerRepository;
import Backend_TruckSnack.TruckSnack.repository.mapping.ReviewListMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {
    private final AdminRepositroy adminRepositroy;
    private final CommunicationRepositroy communicationRepositroy;
    private final CustomerRepository customerRepository;

    public List<Customer> userBlackList(){
        List<Customer> list = customerRepository.findByIsDeletedTrue();
        return list;
    }
    public boolean userBlackListSub(String userId) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findById(userId));

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setDeleted(true);
            customerRepository.save(customer);
            return true;
        } else {
            return false;
        }
    }


    public List<ReviewListMapping> adminReviewCheck(String seller_id){
       List<ReviewListMapping> list =  communicationRepositroy.findBySellerIdAndIsDeleted(seller_id , false);
        return list;
    }

    public List<Admin> adminList(){
        List<Admin> list = adminRepositroy.findAll();
        return list;
    }

    public boolean approvalAdmin(String id) {
        Optional<Admin> adminOptional = adminRepositroy.findById(id);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            admin.setCheckSome(true); // Set the checkSome field to true
            adminRepositroy.save(admin);
            return true;
        } else {
            return false;
        }
    }


    public String adminSignUp(Admin admin){
        if(adminRepositroy.existsById(admin.getId())){
            //존재
            return "admin already exists";
        }else{
            //없음
            adminRepositroy.save(
                    Admin.builder()
                            .id(admin.getId())
                            .password(admin.getPassword())
                            .nickName(admin.getNickName())
                            .checkSome(false)
                            .build()
            );
            return "success";
        }

    }

    public String adminLogin(String id , String password){
        if(adminRepositroy.existsById(id)){
            Optional<Admin> admin = adminRepositroy.findById(id);
            if(admin.get().getPassword().equals(password) && admin.get().isCheckSome()){
                return "success";
            }else{
                return "password wrong";
            }
        }else{
            return "id not exists";
        }
    }

    @Transactional
    public boolean adminDeletedReview(Long seq) {
        try {
            Communication review = communicationRepositroy.findById(seq).orElse(null);

            if (review != null) {
                review.setDeleted(true);
                communicationRepositroy.save(review);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
