package Backend_TruckSnack.TruckSnack.domain;

import lombok.*;

import javax.persistence.*;

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
    @Column(name = "ORDER_TOTAL_PRICE",nullable = true)
    int orderTotalPrice;
}
