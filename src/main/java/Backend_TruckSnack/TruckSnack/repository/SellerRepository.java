package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.mapping.RankCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findById(String id);
    Seller findBySeq(Long seq);
    @Query("SELECT s from  Seller s where s.businessName = ?1 AND s.phoneNumber =?2")
    Seller findByBusinessNameAndPhoneNumber(String businessName , String phoneNumber);

    List<RankCategoryMapping> findByCategory(int category);
}
