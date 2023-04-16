package Backend_TruckSnack.TruckSnack.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodDetailDTO {
    private Long foodSeq;
    private String foodName;
    private int price;
    private String base64Img;
}
