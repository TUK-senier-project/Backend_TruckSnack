package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.service.S3Upload;
import Backend_TruckSnack.TruckSnack.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
@RestController
public class S3Controller {
    private final S3Upload s3Upload;

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
