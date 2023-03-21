package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.LikeFood;
import Backend_TruckSnack.TruckSnack.repository.CustomerRepository;
import Backend_TruckSnack.TruckSnack.repository.LikeFoodRepository;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.dto.MyLikeListDTO;
import Backend_TruckSnack.TruckSnack.repository.mapping.MyLikeMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    @Autowired
    private EntityManager entityManager;

    private final LikeFoodRepository likeRepository;
    @Autowired
    private final SellerRepository sellerRepository;
    private final CustomerRepository customerRepository;

    int is_activate = 0 ;
    int is_Disabled = 1;

    public Optional<LikeFood> likeFood_find(String seller_id , String customer_id ){
       return likeRepository.findBySellerIdAndCustomerId(seller_id , customer_id);
    }
    @Transactional
    public String like_seller_service(String seller_id , String customer_id){
        if(seller_id.isEmpty() || customer_id.isEmpty()){
            return "NULL";
        }else{
            Optional<LikeFood> likeFood = likeFood_find(seller_id , customer_id);
            if(likeFood.isPresent()){
                //값이 존재함
                // activate 상태확인 - // 0 , 이미존재  // 1, 다시 활성화
                int check_activate ;
                check_activate = likeFood.get().getActivate();
                if(check_activate == 0){
                    log.info("해당하는 찜하기는 이미 존재합니다.");
                    return "해당 찜하기는 이미 존재";
                }
                else if(check_activate == 1){
                    log.info("찜하기 재선택 -- 활성화..");
                    Long temp_seq;
                    temp_seq = likeFood.get().getSeq();
                    LikeFood set_list = likeRepository.findBySeq(temp_seq);
                    set_list.setActivate(is_activate);
                    entityManager.merge(set_list);
                    log.info("찜하기 재선택 -- 활성화..완료");
                    return "해당 찜하기를 재 활성화 하였습니다.";
                }else{
                    log.info("찜하기 >> 에러");
                    return "에러 -- 관리자에게 문의하십시오";
                }

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
    @Transactional
    public String like_cancel_service(String customer_id , String seller_id){
        log.info("likeCancel Service : {} {}" , customer_id ,seller_id);

        Optional<LikeFood> likeFood = likeFood_find(seller_id,customer_id);
        log.info("test like cancel  : {}" ,String.valueOf(likeFood));
        if(seller_id.isEmpty() || customer_id.isEmpty()){
            return "NULL";
        }else {

            if(likeFood.isPresent()){
                //값이 존재함
                /**
                 * activate를 비활성화 시키면됨 -> 어차피 0이던 1이던 취소는 0이니까 .. 크게 의미없을듯 .. 생각해볼것
                 */
                Long temp_seq = likeFood.get().getSeq();
                LikeFood set_list = likeRepository.findBySeq(temp_seq);
                set_list.setActivate(is_Disabled);
                entityManager.merge(set_list);
                return "취소하였습니다.";


            }else{
                return "찜하지 않은 가계입니다.";
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
            /**
             * activate 필터 적용 시작
             */
            //activate 시작
            myLikeList = myLikeList.stream().filter(s -> s.getActivate() == 0 ).collect(Collectors.toList());
            //activate 종료
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
