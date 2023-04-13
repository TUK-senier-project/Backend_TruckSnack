package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findById(String id);

    @Query("SELECT c from  Customer c where c.name = ?1 AND c.phoneNumber =?2")
    Optional<Customer> findByNameAndPhoneNumber(String name, String phoneNumber);

    boolean existsById(String customer_id);

}
