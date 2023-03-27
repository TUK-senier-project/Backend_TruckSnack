package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.service.RankService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RankController {

    private final RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping("/rank")
    public ResponseEntity category_rank() throws JsonProcessingException {
        rankService.category_rank();
        return null;
    }
}
