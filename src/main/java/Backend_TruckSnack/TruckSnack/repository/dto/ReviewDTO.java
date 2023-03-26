package Backend_TruckSnack.TruckSnack.repository.dto;

import Backend_TruckSnack.TruckSnack.domain.Communication;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDTO {
    private Communication communication;
    private String customer_id;
}
