package Backend_TruckSnack.TruckSnack.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerClassController {
    @GetMapping("/customer/findUsers/{id}")
    public String findUser(@PathVariable String id){
        return "user id =" + id;
    }
    @PostMapping("/customer/register")
    public String register(){
        return "success";
    }

}
