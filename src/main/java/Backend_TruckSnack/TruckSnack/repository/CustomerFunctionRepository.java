package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerFunctionRepository extends JpaRepository<Customer, Long> {
    static Customer findById(String name, String phone_number) {
        return null;
    }
}
