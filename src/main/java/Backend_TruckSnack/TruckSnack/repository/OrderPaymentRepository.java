package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.OrderPayment;
import Backend_TruckSnack.TruckSnack.repository.mapping.OrderListDetailMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPaymentRepository extends JpaRepository<OrderPayment , Long> {
    List<OrderListDetailMapping> findByCustomerOrderPaymentSeq(Long customer_orderPayment_seq);
}
