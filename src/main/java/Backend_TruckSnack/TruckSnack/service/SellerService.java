package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {
    private final SellerRepository sellerRepository;

    public String register_seller_service(@RequestBody Seller sellerData)throws IOException{
        sellerRepository.save(
                Seller.builder()
                        .id(sellerData.getId())
                        .password(sellerData.getPassword())
                        .businessName(sellerData.getBusinessName())
                        .content(sellerData.getContent())
                        .category(sellerData.getCategory())
                        .deadline(sellerData.getDeadline())
                        .phoneNumber(sellerData.getPhoneNumber())
                        .location(sellerData.getLocation())
                        .build()
        );
        return "Success";
    }
}
