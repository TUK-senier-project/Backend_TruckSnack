package Backend_TruckSnack.TruckSnack.domain;

import Backend_TruckSnack.TruckSnack.util.S3_img_util;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data
public class S3Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;
    @Column(name = "ID")
    private String id;
    @Column(name = "Name")
    private String name;
    @Column(name = "FORMAT")
    private String format;
    @Column(name = "PATH")
    private String path;
    @Column(name = "BYTES")
    private long bytes;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public static S3Img multipartOf(MultipartFile s3Img) {
        final String fileId = S3_img_util.createFileId();
        final String format = S3_img_util.getFormat(s3Img.getContentType());
        return S3Img.builder()
                .id(fileId)
                .name(s3Img.getOriginalFilename())
                .format(format)
                .path(S3_img_util.createPath(fileId, format))
                .bytes(s3Img.getSize())
                .build();

    }

}