package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.CommunicationRepositroy;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.mapping.ReviewListMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {
    private final SellerRepository sellerRepository;
    private final CommunicationRepositroy communicationRepositroy;
    private static double grade_sect = 0.0;


    public List<ReviewListMapping> reviewList_seller_service(String seller_id){
        List<ReviewListMapping> Review_list;
        Review_list = communicationRepositroy.findBySellerId(seller_id);

        return Review_list;
    }

    public String register_seller_service(@RequestBody Seller sellerData)throws IOException{
        sellerRepository.save(
                Seller.builder()
                        .id(sellerData.getId())
                        .password(sellerData.getPassword())
                        .businessName(sellerData.getBusinessName())
                        .content(sellerData.getContent())
                        .category(sellerData.getCategory())
                        .deadline(sellerData.getDeadline())
                        .grade(grade_sect)
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
        Optional<Seller> seller;
        seller =sellerRepository.findByBusinessNameAndPhoneNumber(bussinessName,phoneNumber);
        String id;
        try {
            id = seller.map(Seller::getId).orElseThrow(() -> new RuntimeException("Seller not found"));
        } catch (RuntimeException e) {
            id = e.getMessage(); // RuntimeException의 오류 메시지를 id 변수에 저장합니다.
        }

        return id;

    }

    public int seq_find_seller_service(String id){
        return Math.toIntExact(sellerRepository.findById(id).getSeq());
    }
}
