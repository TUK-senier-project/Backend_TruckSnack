package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodDetailMapping;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

public interface FoodListRepository extends JpaRepository<Seller, Long> {

    //@Query("SELECT s from  Seller s where s.category = ?1")
    List<FoodListMapping> findByCategory(int category);
}
