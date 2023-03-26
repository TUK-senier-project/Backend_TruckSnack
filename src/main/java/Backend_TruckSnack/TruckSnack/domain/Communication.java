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
        name = "comumunication" ,
        schema = "truck_snack_db"
)
@Data
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;
    @JoinColumn(name="CUSTOMER_NICK_NAME")
    private String customerNickName;
    @JoinColumn(name="seller_id")
    private String sellerId;
    @JoinColumn(name="CUSTOMER_ORDERE_PAYMENT_SEQ")
    private Long customerOrderPaymentSeq;
    @Column(name = "REVIEW",nullable = false , length = 200)
    private String review;
    @Column(name = "GRADE",nullable = false)
    private double grade;
    @Column(name = "IS_CREATED",nullable = false)
    @CreationTimestamp
    private LocalDateTime isCreated;
    @Column(name = "IS_UPDATED",nullable = false)
    @UpdateTimestamp
    private LocalDateTime isUpdated;
    @Column(name = "IS_DELETED",nullable = false)
    private boolean isDeleted;
}
