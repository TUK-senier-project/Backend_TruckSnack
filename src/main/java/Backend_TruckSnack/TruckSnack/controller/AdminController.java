package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Admin;
import Backend_TruckSnack.TruckSnack.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }
    @GetMapping("/adminHome")
    public String adminHome() {
        return "admin";
    }
    @PostMapping("/signup")
    public String processSignup(Admin admin, Model model) {
        String result = adminService.adminSignUp(admin);
        if ("success".equals(result)) {
            return "redirect:/admin/login";
        } else {
            model.addAttribute("error", result);
            return "signup";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(String id , String password, Model model) {
        String result = adminService.adminLogin(id, password);

        if ("success".equals(result)) {
            return "redirect:/admin/adminHome";
        } else {
            model.addAttribute("error", result);
            return "login";
        }
    }
}
