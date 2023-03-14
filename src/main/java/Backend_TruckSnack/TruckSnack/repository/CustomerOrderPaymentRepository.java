package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderPaymentRepository  extends JpaRepository<CustomerOrderPayment, Long> {
}
