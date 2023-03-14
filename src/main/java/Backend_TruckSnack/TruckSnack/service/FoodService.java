package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.domain.Food;
import Backend_TruckSnack.TruckSnack.domain.Seller;
import Backend_TruckSnack.TruckSnack.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodService {
    private final FoodRepository foodRepository;
    public String upload_food_service(@RequestBody Food foodData , int sellerSeq)throws IOException {
        foodRepository.save(
                Food.builder()
                        .foodName(foodData.getFoodName())
                        .price(foodData.getPrice())
                        .sellerSeq((long) sellerSeq)
                        .build()
        );
        return "Success";
    }

}
