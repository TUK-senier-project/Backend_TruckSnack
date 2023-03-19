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
        name = "CustomerOrderPayment" ,
        schema = "SnackTest"
)
@Data
public class CustomerOrderPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @JoinColumn(name="Customer_ID")
    private String customerId;
    @JoinColumn(name = "Seller_ID")
    private String sellerId;
    @Column(name = "ORDER_TOTAL_PRICE",nullable = true)
    private int orderTotalPrice;
    @Column(name = "ORDER_STATE",nullable = false)
    private int orderState;
    @Column(name = "IS_CREATED",nullable = false)
    @CreationTimestamp
    private LocalDateTime isCreated;
    @Column(name = "IS_UPDATED",nullable = false)
    @UpdateTimestamp
    private LocalDateTime isUpdated;
    @Column(name = "IS_DELETED",nullable = false)
    private boolean isDeleted;
}
