package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.S3Img;
import Backend_TruckSnack.TruckSnack.service.S3_img_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/upload", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class S3_img_Controller {
    private final S3_img_Service s3ImgServices;

    @PostMapping
    public ResponseEntity<S3Img> post(
            @RequestPart(value ="file", required=false) MultipartFile s3Img) {
        return ResponseEntity.ok(s3ImgServices.save(s3Img));
    }
}
