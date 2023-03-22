package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.Communication;
import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationRepositroy extends JpaRepository<Communication, Long> {

}
