package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.CustomerOrderPayment;
import Backend_TruckSnack.TruckSnack.repository.mapping.RankCategoryReorderMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RankRepository extends JpaRepository<CustomerOrderPayment, Long> {
    List<RankCategoryReorderMapping> findBySellerIdAndOrderState(String sellerId , int orderState);
    @Query("SELECT e.customerId, COUNT(e) FROM CustomerOrderPayment e WHERE e.sellerId = :sellerId GROUP BY e.customerId HAVING COUNT(e) > 1")
    List<Object[]> findDuplicateValues(@Param("sellerId") String sellerId);


}
