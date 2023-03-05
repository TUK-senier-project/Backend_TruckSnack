package Backend_TruckSnack.TruckSnack.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/test-api" , method = RequestMethod.GET)
    public String testAPI(){
        log.info("연결 요청");
        return "연결 가능합니다";
    }
    //@GetMapping -> 이렇게 써야지
    //@PathVariable 여러개도 가능하다
    @GetMapping("mapping/{id}")
    public String testPathAPI(@PathVariable("id") String data){
        log.info("id data={}",data);
        return "test succese";
    }
}
