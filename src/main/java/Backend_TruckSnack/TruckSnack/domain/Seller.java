package Backend_TruckSnack.TruckSnack.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "seller" ,
        schema = "truck_snack_db",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "business_name_unique",
                        columnNames = "business_name"
                )
        }
)
@Data
public class Seller {
    public int setSeq;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;
    @Column(name = "ID",nullable = false ,length = 35,unique = true)
    private String id;
    @Column(name = "PASSWORD",nullable = false ,length = 20)
    private String password;
    @Column(name = "BUSINESS_NAME",nullable = false,length = 15)
    private String businessName;
    @Column(name = "CONTENT",nullable = false,length = 50)
    private String content;
    @Column(name = "CATEGORY",nullable = false)
    private  int category;
    @Column(name = "DEADLINE",nullable = false,length = 50)
    private int deadline;
    @Column(name = "PHONE_NUMBER",nullable = true ,length = 11)
    private String phoneNumber;
    @Column(name = "Grade",nullable = true)
    private double grade;
    @Column(name = "SELLER_IMG_S3_URL",nullable = true,length = 200)
    private String sellerImgS3Url;
    @Column(name = "LOCATION",nullable = true,length = 50)
    private String location;
    @Column(name = "IS_CREATED",nullable = false)
    @CreationTimestamp
    private LocalDateTime isCreated;
    @Column(name = "IS_UPDATED",nullable = false)
    @UpdateTimestamp
    private LocalDateTime isUpdated;
    @Column(name = "IS_DELETED",nullable = false)
    private boolean isDeleted;
}
