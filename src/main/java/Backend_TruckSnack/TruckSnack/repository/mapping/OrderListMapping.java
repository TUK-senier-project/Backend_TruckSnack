package Backend_TruckSnack.TruckSnack.repository.mapping;

import java.time.LocalDateTime;

public interface OrderListMapping {
    Long getSeq();
    String getCustomerId();
    String getSellerId();
    int getOrderTotalPrice();
    LocalDateTime getIsCreated();
}
