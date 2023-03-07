package Backend_TruckSnack.TruckSnack.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "seller" ,
        schema = "SnackTest",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "business_name_unique",
                        columnNames = "business_name"
                )
        }
)
@Data
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;
    @Column(name = "ID",nullable = false ,length = 35)
    private String id;
    @Column(name = "PASSWORD",nullable = false ,length = 20)
    private String password;
    @Column(name = "BUSINESS_NAME",nullable = false,length = 15)
    private String business_name;
    @Column(name = "CONTENT",nullable = false,length = 50)
    private String content;
    @Column(name = "CATEGORY",nullable = false)
    private  int category;
    @Column(name = "DEADLINE",nullable = false,length = 50)
    private int deadline;
    @Column(name = "PHONE_NUMBER",nullable = true,length = 11)
    private String phone_number;
    @Column(name = "SELLER_IMG_S3_URL",nullable = true,length = 100)
    private String seller_img_s3_url;
    @Column(name = "LOCATION",nullable = true,length = 50)
    private String location;
    @CreationTimestamp
    @Column(name = "IS_CREATED",nullable = false)
    private LocalDateTime is_created;
    @UpdateTimestamp
    @Column(name = "IS_UPDATED",nullable = false)
    private LocalDateTime is_updated;
    @Column(name = "IS_DELETED",nullable = false)
    private boolean is_deleted;
}
