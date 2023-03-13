package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {
    private final SellerRepository sellerRepository;

    public String register_seller_service(@RequestBody Seller sellerData)throws IOException{
        sellerRepository.save(
                Seller.builder()
                        .id(sellerData.getId())
                        .password(sellerData.getPassword())
                        .businessName(sellerData.getBusinessName())
                        .content(sellerData.getContent())
                        .category(sellerData.getCategory())
                        .deadline(sellerData.getDeadline())
                        .phoneNumber(sellerData.getPhoneNumber())
                        .location(sellerData.getLocation())
                        .build()
        );
        return "Success";
    }

    public boolean register_seller_id_check(String id){
        Seller seller = sellerRepository.findById(id);
        if(seller != null && seller.getId().equals(id)){
            return false;
        }
        else{
            return true; //아이디 없음
        }
    }

    public boolean login_seller_service(String id , String password)throws IOException{
        Seller seller = sellerRepository.findById(id);
        if(seller !=null && seller.getPassword().equals(password)){
            return true ; //로그인 성공
        }
        else {
            return false;
        }
    }
    
    public String id_find_seller_service(String bussinessName , String phoneNumber){
        return sellerRepository.findByBusinessNameAndPhoneNumber(bussinessName,phoneNumber).getId();
    }

    public int seq_find_seller_service(String id){
        return Math.toIntExact(sellerRepository.findById(id).getSeq());
    }
}
