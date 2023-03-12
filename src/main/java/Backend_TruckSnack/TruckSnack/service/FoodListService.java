package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.repository.FoodListRepository;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodListService {
    private final FoodListRepository foodListRepository;

    public List<FoodListMapping> get_foodList_service(int category){
            List<FoodListMapping> seller_list = null;
            String checkCategory = String.valueOf(category);
            if(checkCategory != null){
                seller_list = foodListRepository.findByCategory(category);
            }

            return seller_list;
    }

}
