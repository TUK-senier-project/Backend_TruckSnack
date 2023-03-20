package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.LikeFood;
import Backend_TruckSnack.TruckSnack.repository.mapping.MyLikeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeFoodRepository extends JpaRepository<LikeFood, Long> {
    Optional<LikeFood> findBySellerIdAndCustomerId(String sellerId , String customerId);

    List<MyLikeMapping> findByCustomerId(String customerId);

}
