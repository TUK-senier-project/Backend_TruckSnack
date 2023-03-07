package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.S3Img;
import Backend_TruckSnack.TruckSnack.stroragy.AmazonS3ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3_img_Service {
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    public S3Img save(MultipartFile s3Img) {
        S3Img s3ImgDetail = S3Img.multipartOf(s3Img);
        amazonS3ResourceStorage.store(s3ImgDetail.getPath(),s3Img);
        return s3ImgDetail;
    }
}
