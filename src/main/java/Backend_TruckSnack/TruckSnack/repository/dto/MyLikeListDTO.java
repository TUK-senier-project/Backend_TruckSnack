package Backend_TruckSnack.TruckSnack.repository.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MyLikeListDTO {
    private List<String> myLike_seller_id_list;
    private List<String> myLike_businessName_list;
}
