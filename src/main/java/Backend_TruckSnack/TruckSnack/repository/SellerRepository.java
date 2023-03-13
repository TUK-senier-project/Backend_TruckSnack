package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findById(String id);
    @Query("SELECT s from  Seller s where s.businessName = ?1 AND s.phoneNumber =?2")
    Seller findByBusinessNameAndPhoneNumber(String businessName , String phoneNumber);

}
