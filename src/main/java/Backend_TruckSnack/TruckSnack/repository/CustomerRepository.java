package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findById(String id);

    @Query("SELECT c from  Customer c where c.name = ?1 AND c.phoneNumber =?2")
    Customer findByNameAndPhoneNumber(String name,String phoneNumber);


}
