package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Admin;
import Backend_TruckSnack.TruckSnack.repository.AdminRepositroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {
    private final AdminRepositroy adminRepositroy;

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
                            .build()
            );
            return "success";
        }

    }

    public String adminLogin(String id , String password){
        if(adminRepositroy.existsById(id)){
            Optional<Admin> admin = adminRepositroy.findById(id);
            if(admin.get().getPassword().equals(password)){
                return "success";
            }else{
                return "password wrong";
            }
        }else{
            return "id not exists";
        }
    }
}
