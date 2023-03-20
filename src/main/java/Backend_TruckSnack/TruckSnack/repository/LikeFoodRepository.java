package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.LikeFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeFoodRepository extends JpaRepository<LikeFood, Long> {
    Optional<LikeFood> findBySellerIdAndCustomerId(String sellerId , String customerId);
}
