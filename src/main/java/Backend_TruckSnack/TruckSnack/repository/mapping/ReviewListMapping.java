package Backend_TruckSnack.TruckSnack.repository.mapping;

import java.time.LocalDateTime;

public interface ReviewListMapping {
    String getCustomerNickName();
    double getGrade();
    String getReview();
    LocalDateTime getIsCreated();

}
