package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.Communication;
import Backend_TruckSnack.TruckSnack.repository.mapping.ReviewListMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunicationRepositroy extends JpaRepository<Communication, Long> {
    List<ReviewListMapping> findBySellerIdAndIsDeleted(String sellerId, boolean isDeletedValue);

}
