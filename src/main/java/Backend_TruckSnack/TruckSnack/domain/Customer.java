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
        name = "customer" ,
        schema = "SnackTest",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id_unique",
                        columnNames = "id"
                )
        }
)
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "customer_generator"
//    )
//    @SequenceGenerator(
//            name = "customer_generator",
//            sequenceName = "customer_sequence_name",
//            allocationSize = 1
//    )
    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "ID",nullable = false ,length = 35,unique = true)
    private String id;

    @Column(name = "NAME",nullable = false ,length = 10)
    private String name;
    @Column(name = "PASSWORD",nullable = false ,length = 20)
    private String password;
    @Column(name = "CUSTOMER_IMG_S3_URL",nullable = true ,length = 100)
    private String customerImgS3Url;
    @Column(name = "PHONE_NUMBER",nullable = true ,length = 11)
    private String phoneNumber;
    @Column(name = "LOCATION",nullable = true ,length = 50)
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
