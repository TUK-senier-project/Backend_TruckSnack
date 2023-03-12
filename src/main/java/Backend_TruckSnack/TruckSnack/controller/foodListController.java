package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.service.FoodListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class foodListController {
    private final FoodListService foodListService;
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());;
    public foodListController(FoodListService foodListService) {
        this.foodListService = foodListService;
    }

    @GetMapping("/food-list/{categoryNumber}")
    public ResponseEntity foodList_category(@PathVariable int categoryNumber) throws JsonProcessingException {
        log.info("food-list >> category Number: {}",categoryNumber);
        String json = objectMapper.writeValueAsString(foodListService.get_foodList_service(categoryNumber));
        log.info(json);
        return ResponseEntity.ok(json);
    }

}
