package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.dto.RecommendOrderDataDTO;
import Backend_TruckSnack.TruckSnack.service.S3Service;
import Backend_TruckSnack.TruckSnack.service.S3Upload;
import Backend_TruckSnack.TruckSnack.util.ApiResponse;
import Backend_TruckSnack.TruckSnack.util.SellerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
@RestController
public class S3Controller {
    private final S3Upload s3Upload;
    private final SellerUtil sellerUtil;
    private final S3Service s3Service;
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @PostMapping("/upload-Test")
    public ResponseEntity<ApiResponse<String>> uploadFileTest(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        try {
            String s3Url = s3Upload.upload(multipartFile);
            ApiResponse<String> response = ApiResponse.success(HttpStatus.CREATED, s3Url);

            log.info("uploadFile : url 확인 : {}",response.getMessage());

            return ResponseEntity.status(response.getStatus()).body(response);

        } catch (FileSizeLimitExceededException ex) {
            ApiResponse<String> response = ApiResponse.error(HttpStatus.BAD_REQUEST, "파일 크기가 제한을 초과하였습니다.");
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception ex) {
            log.error("파일 업로드 중 오류 발생", ex);
            ApiResponse<String> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생하였습니다.");
            return ResponseEntity.status(response.getStatus()).body(response);
        }
    }
    @PostMapping("/getImg-Test")
    public ResponseEntity getImgTest(@RequestBody Seller sellerData) throws IOException {
        String  seller_id = sellerData.getId();
        String logs =null;
        if(sellerUtil.check_id_util(seller_id)){
            log.info("아이디 체크 성공");
            if(sellerUtil.check_img_url_util(seller_id)){
                log.info("이미지 체크 존재");
                InputStreamResource resource = s3Service.s3_img_seller_main_return_service(seller_id);
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else{
                log.info("이미지 NULL");
                logs = "IMG Null";
            }
        }
        else {
            log.info("아이디 체크 실패");
            logs = "해당아이디는 없는 아이디 입니다.";
        }
        String json = objectMapper.writeValueAsString(logs);
        return ResponseEntity.ok(json);
    }




    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        try {
            String s3Url = s3Upload.upload(multipartFile);
            ApiResponse<String> response = ApiResponse.success(HttpStatus.CREATED, s3Url);

            log.info("uploadFile : url 확인 : {}",response.getMessage());

            return ResponseEntity.status(response.getStatus()).body(response);

        } catch (FileSizeLimitExceededException ex) {
            ApiResponse<String> response = ApiResponse.error(HttpStatus.BAD_REQUEST, "파일 크기가 제한을 초과하였습니다.");
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (Exception ex) {
            log.error("파일 업로드 중 오류 발생", ex);
            ApiResponse<String> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생하였습니다.");
            return ResponseEntity.status(response.getStatus()).body(response);
        }
    }

}
