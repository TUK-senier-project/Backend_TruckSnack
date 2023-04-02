package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import Backend_TruckSnack.TruckSnack.repository.mapping.OrderListMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderPaymentRepository  extends JpaRepository<CustomerOrderPayment, Long> {
    List<OrderListMapping> findBySellerId(String sellerId);
    CustomerOrderPayment findBySeq(Long seq);

    int countBySellerId(String sellerId);

    CustomerOrderPayment findTopByCustomerIdOrderByIsCreatedDesc(String customer_id);
}
