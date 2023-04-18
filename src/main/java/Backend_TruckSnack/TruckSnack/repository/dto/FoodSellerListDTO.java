package Backend_TruckSnack.TruckSnack.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodSellerListDTO {
    private String sellerId;
    private String businessName;
    private int deadline;
    private String phoneNumber;
    private double grade;
    private String location;

    private String base64Img;
}
