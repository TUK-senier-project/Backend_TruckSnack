package Backend_TruckSnack.TruckSnack.repository.dto;

import Backend_TruckSnack.TruckSnack.domain.Customer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerLoginDTO {
    private Customer customer;
    private String base64EncodedImage;
}
