package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class S3Service {
    private final SellerRepository sellerRepository;
    private final AmazonS3 amazonS3;
    public InputStreamResource s3_img_seller_main_return_service(String seller_id) throws MalformedURLException {
        /**
         * 셀러아이디로 이미지 url받기
         */
        String s3_url = sellerRepository.findById(seller_id).getSellerImgS3Url();
        //url 파싱
        Pair<String, String> bucketAndKey = url_patten_parsing(s3_url);
        String bucketName = bucketAndKey.getFirst();
        String objectKey = bucketAndKey.getSecond();
        log.info("s3_img_seller_main_return_service : bucketName : {}  , objectKey : {}" , bucketName , objectKey);
        // S3 object 불러오기
        S3Object s3Object = amazonS3.getObject(bucketName,objectKey);
        InputStream inputStream = s3Object.getObjectContent();
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        return inputStreamResource;

    }

    public Pair<String, String> url_patten_parsing(String s3_url) throws MalformedURLException {
        URL url = new URL(s3_url);
        String bucketName = url.getHost().split("\\.")[0];
        String objectKey = url.getPath().substring(1); // 첫번째 '/'를 제거
        return Pair.of(bucketName, objectKey);
    }

}