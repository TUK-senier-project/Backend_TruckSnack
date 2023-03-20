package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.LikeFood;
import Backend_TruckSnack.TruckSnack.repository.CustomerRepository;
import Backend_TruckSnack.TruckSnack.repository.LikeFoodRepository;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.dto.MyLikeListDTO;
import Backend_TruckSnack.TruckSnack.repository.mapping.MyLikeInfoMapping;
import Backend_TruckSnack.TruckSnack.repository.mapping.MyLikeMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    @Autowired
    LikeFoodRepository likeRepository;
    @Autowired
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

    public MyLikeListDTO my_like_service(String customer_id){
        List<MyLikeMapping> myLikeList;

        myLikeList = likeRepository.findByCustomerId(customer_id);
        String temp_seller_id;
        String set_seller_id;
        String set_business_name;

        List<String> myLike_seller_id_list = new ArrayList<String >();

        List<String> myLike_businessName_list= new ArrayList<String >();

        for(int i =0; i<myLikeList.size(); i++){
            temp_seller_id = myLikeList.get(i).getSellerId();
            log.info("my Like List >> {} : {}",i,temp_seller_id);

            set_seller_id = temp_seller_id;
            set_business_name= sellerRepository.findById(set_seller_id).getBusinessName();

            log.info("test : {}" , set_business_name);
            myLike_seller_id_list.add(i , set_seller_id);
            myLike_businessName_list.add(i , set_business_name);
        }

        log.info("셀러아이디 리스트");
        myLike_seller_id_list.forEach(n -> System.out.println(n));
        log.info("비지니스 네임");
        myLike_businessName_list.forEach(n -> System.out.println(n));
        log.info("리턴용 리스트");
        MyLikeListDTO return_list = my_like_info_list_create(myLike_seller_id_list , myLike_businessName_list);

        System.out.println(return_list.getMyLike_seller_id_list());
        System.out.println(return_list.getMyLike_businessName_list());
        return return_list;
    }

    public MyLikeListDTO my_like_info_list_create(List<String> myLike_seller_id_list , List<String> myLike_businessName_list){
        MyLikeListDTO myLikeListDTO = new MyLikeListDTO();
        myLikeListDTO.setMyLike_seller_id_list(myLike_seller_id_list);
        myLikeListDTO.setMyLike_businessName_list(myLike_businessName_list);

        return myLikeListDTO;
    }


}
