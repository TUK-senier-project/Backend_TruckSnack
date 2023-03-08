package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.service.SellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class SellerController {
    private final SellerService sellerService;
    private ObjectMapper objectMapper = new ObjectMapper();
    public SellerController(SellerService sellerService){
        this.sellerService= sellerService;
    }

    @ResponseBody
    @PostMapping("/seller/register")
    public ResponseEntity register_seller(@RequestBody Seller sellerData)throws IndexOutOfBoundsException{


        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
