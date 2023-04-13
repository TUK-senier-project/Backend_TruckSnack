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
        name = "food" ,
        schema = "truck_snack_db"
)
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;
    @JoinColumn(name="SELLER_SEQ")
    private Long sellerSeq;
    @Column(name = "FOOD_NAME",nullable = false,length = 20)
    private String foodName;
    @Column(name = "PRICE",nullable = false)
    private int price;
    @Column(name = "FOOD_IMG_S3_URL",nullable = true,length = 200)
    private String foodImgS3URL;
    @Column(name = "IS_CREATED",nullable = false)
    @CreationTimestamp
    private LocalDateTime isCreated;
    @Column(name = "IS_UPDATED",nullable = false)
    @UpdateTimestamp
    private LocalDateTime isUpdated;
    @Column(name = "IS_DELETED",nullable = false)
    private boolean isDeleted;
}
