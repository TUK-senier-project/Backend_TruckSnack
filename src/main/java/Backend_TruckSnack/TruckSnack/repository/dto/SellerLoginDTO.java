package Backend_TruckSnack.TruckSnack.repository.dto;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;
@Setter
@Getter
public class SellerLoginDTO {

    private Seller seller;

    private String base64EncodedImage;
}
