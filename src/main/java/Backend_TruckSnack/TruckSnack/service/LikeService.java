package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.LikeFood;
import Backend_TruckSnack.TruckSnack.repository.CustomerRepository;
import Backend_TruckSnack.TruckSnack.repository.LikeFoodRepository;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    @Autowired
    LikeFoodRepository likeRepository;
    SellerRepository sellerRepository;
    CustomerRepository customerRepository;
    public String like_seller_service(String seller_id , String customer_id){
        if(seller_id.isEmpty() || customer_id.isEmpty()){
            return "NULL";
        }else{
            Optional<LikeFood> likeFood = likeRepository.findBySellerIdAndCustomerId(seller_id , customer_id);
            if(likeFood.isPresent()){
                //값이 존재함
                log.info("해당하는 찜하기는 이미 존재합니다.");
                return "해당 찜하기는 이미 존재";
            }
            else{
                //값이 존재안함
                log.info("찜하기를 시작합니다.");
                likeRepository.save(
                        LikeFood.builder()
                                .sellerId(seller_id)
                                .customerId(customer_id)
                                .build()
                );
                return "찜하기 완료";
            }

        }

    }
}
