package Backend_TruckSnack.TruckSnack.util;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SellerUtil {
    @Autowired
    private final SellerRepository sellerRepository;

    public SellerUtil(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public boolean check_id_util(String seller_id){
        log.info("check_id_util : seller_id : {}" , seller_id);
        if(sellerRepository.existsById(seller_id)){
            log.info("check_id_util : true");
            return true;
        }else{
            log.info("check_id_util : false");
            return false;
        }
    }

    public boolean check_img_url_util(String seller_id){
        log.info("check_img_url_util : seller_id : {}" , seller_id);
        String seller_img_url = sellerRepository.findById(seller_id).getSellerImgS3Url();
        if(seller_img_url !=null){
            log.info("check_img_url_util : true");
            return true;
        }else{
            log.info("check_img_url_util : false");
            return false;
        }
    }


}
