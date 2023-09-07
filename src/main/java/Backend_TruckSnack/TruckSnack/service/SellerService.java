package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.controller.S3Controller;
import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.CommunicationRepositroy;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import Backend_TruckSnack.TruckSnack.repository.dto.SellerLoginDTO;
import Backend_TruckSnack.TruckSnack.repository.mapping.ReviewListMapping;
import Backend_TruckSnack.TruckSnack.util.ApiResponse;
import Backend_TruckSnack.TruckSnack.util.SellerUtil;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SellerService {
    @Autowired
    private EntityManager entityManager;
    private final SellerRepository sellerRepository;

    private final CommunicationRepositroy communicationRepositroy;

    private final SellerUtil sellerUtil;

    private final S3Controller s3Controller;

    private final S3Service s3Service;

    private static double grade_sect = 0.0;


    public List<ReviewListMapping> reviewList_seller_service(String seller_id){
        List<ReviewListMapping> Review_list;
        Review_list = communicationRepositroy.findBySellerIdAndIsDeleted(seller_id , false);

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

    public SellerLoginDTO login_find_data_service(String seller_id) throws IOException {
        //setter 안쓰고 매핑으로 해도 되긴하는데 확실히 메모리 리소스를 더 많이 쓸거같네

        Seller sellerData = sellerRepository.findById(seller_id);
        //seller setter Start
        Seller responseData = new Seller();
        responseData.setId(sellerData.getId());
        responseData.setBusinessName(sellerData.getBusinessName());
        //seller setter end

        //sellerLoginDTO ->data setting start
        SellerLoginDTO sellerLoginDTO = new SellerLoginDTO();
        sellerLoginDTO.setSeller(responseData);
        //sellerLoginDTO ->data setting end

        //seller get img start
        if(sellerUtil.check_img_url_util(seller_id)){
            log.info("login_find_data_service : True" );
            InputStreamResource inputStreamResource = s3Service.s3_img_seller_main_return_service(seller_id);
            String base64EncodedImage = Base64.getEncoder().encodeToString(inputStreamResource.getInputStream().readAllBytes());
            sellerLoginDTO.setBase64EncodedImage(base64EncodedImage);


        }else {
            log.info("login_find_data_service : false" );
            sellerLoginDTO.setBase64EncodedImage(null);

        }
        //seller get img end

        return sellerLoginDTO;


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

    public String img_upload_seller_service(MultipartFile multipartFile , String sellerId) throws IOException {
        /**
         * 셀러 대표 이미지 업로드 ..
         * sellerId 가 존재하는지 확인
         * 이미지 업로드 -> S3controller로 다시 보내서 확인해서 올리고
         * S3 url 받으면 put해서 셀러에 저장해주기
         * 확인 후 돌려주기
         */
        String return_log = null;
        if(sellerUtil.check_id_util(sellerId)){
            log.info("seller_id : True");
            ResponseEntity<ApiResponse<String>> temp = s3Controller.uploadFile(multipartFile);
            log.info("img_upload_seller_service : {}",temp.getBody().getMessage());
            String s3_url = temp.getBody().getMessage();
            if(merge_seller_s3_url(sellerId , s3_url)){
                log.info("seller_imgUpload success");
                return "seller_imgUpload success : "+s3_url;
            }
            else{
                log.info("seller_imgUpload 실패");
                return "seller_imgUpload 실패";
            }
        }else {
            log.info("seller_id : false");
            return_log ="seller_id가 존재하지 않습니다.";
        }


        return return_log;
    }


    public boolean merge_seller_s3_url(String seller_id , String s3_url){
        Seller seller;
        seller = sellerRepository.findById(seller_id);
        seller.setSellerImgS3Url(s3_url);
        entityManager.merge(seller);
        log.info("seller_ s3 img -> 변경완료...");

        return true;
    }

}
