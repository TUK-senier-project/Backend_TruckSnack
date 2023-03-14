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
        name = "OrderPayment" ,
        schema = "SnackTest"
)
@Data
public class OrderPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;
    @JoinColumn(name="FOOD_SEQ")
    private Long foodSeq;
//    @JoinColumn(name="CUSTOMER_ORDER_PAYMENT")
//    private Long customerOrderPayment;
    @Column(name = "Quantity",nullable = false)
    int quantity;
    @Column(name = "TOTAL_PRICE",nullable = false)
    int totalPrice;
    @Column(name = "IS_CREATED",nullable = false)
    @CreationTimestamp
    private LocalDateTime isCreated;
    @Column(name = "IS_UPDATED",nullable = false)
    @UpdateTimestamp
    private LocalDateTime isUpdated;
    @Column(name = "IS_DELETED",nullable = false)
    private boolean isDeleted;


}
