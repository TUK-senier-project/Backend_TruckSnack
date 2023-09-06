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
        return "signup"; // signup.html 템플릿을 보여줍니다.
    }

    @PostMapping("/signup")
    public String processSignup(Admin admin, Model model) {
        String result = adminService.adminSignUp(admin);
        if ("success".equals(result)) {
            return "redirect:/admin/login"; // 회원가입 성공 시 로그인 페이지로 리다이렉트합니다.
        } else {
            model.addAttribute("error", result);
            return "signup"; // 회원가입 실패 시 다시 회원가입 폼을 보여줍니다.
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
