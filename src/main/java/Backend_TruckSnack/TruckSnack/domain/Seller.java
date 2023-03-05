package Backend_TruckSnack.TruckSnack.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(
        name = "seller" ,
        schema = "SnackTest",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "bussiness_name_unique",
                        columnNames = "bussiness_name"
                )
        }
)
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ",nullable = false)
    private String seq;
    @Column(name = "BUSSINESS_NAME",nullable = false)
    private String bussiness_name;
    @Column(name = "CONTENT",nullable = false)
    private String content;
    @Column(name = "CATEGORY",nullable = false)
    private  int category;
    @Column(name = "DEADLINE",nullable = false)
    private int deadline;
    @Column(name = "PHONE_NUMBER",nullable = true)
    private String phone_number;
    @Column(name = "SELLER_IMG_S3_URL",nullable = true)
    private String seller_img_s3_url;
    @Column(name = "LOCATION",nullable = true)
    private String location;
    @Column(name = "IS_CREATED",nullable = false)
    private LocalDateTime is_created;
    @Column(name = "IS_UPDATED",nullable = false)
    private LocalDateTime is_updated;
    @Column(name = "IS_DELETED",nullable = false)
    private boolean is_deleted;
}
