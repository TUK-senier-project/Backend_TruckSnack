package Backend_TruckSnack.TruckSnack.service;

import Backend_TruckSnack.TruckSnack.repository.FoodListRepository;
import Backend_TruckSnack.TruckSnack.repository.mapping.FoodListMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RankService {
    private final FoodListRepository foodListRepository;
    public void category_rank(){
        List<FoodListMapping> seller_list;
        seller_list= foodListRepository.findByCategory(1);

        log.info(seller_list.toString());
    }
}
