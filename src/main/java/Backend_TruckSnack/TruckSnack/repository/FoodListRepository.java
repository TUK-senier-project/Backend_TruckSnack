package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import Backend_TruckSnack.TruckSnack.repository.mapping.RankCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodListRepository extends JpaRepository<Seller, Long> {

    //@Query("SELECT s from  Seller s where s.category = ?1")
    List<FoodListMapping> findByCategory(int category);
    List<FoodListMapping> findByDeadlineGreaterThan(int time);

}
