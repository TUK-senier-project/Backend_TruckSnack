package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.Admin;
import Backend_TruckSnack.TruckSnack.domain.Customer;
import Backend_TruckSnack.TruckSnack.repository.dto.AdminLoginDTO;
import Backend_TruckSnack.TruckSnack.repository.mapping.ReviewListMapping;
import Backend_TruckSnack.TruckSnack.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/adminReview")
    public String adminReview(){
        return "adminReviewFinder";
    }

    @GetMapping("/adminReview/result")
    public String adminReviewResult(){
        return "adminReview";
    }
    @GetMapping("/adminAccess")
    public String adminAccess(){
        return "adminAccess";
    }
    @GetMapping("/userBlackListSub")
    public String userBlackListSub(){
        return "userBlackListSub";
    }

    @GetMapping("/userBlackList")
    public String userBlackList(){
        return "userBlackList";
    }

    @PostMapping("/userBlackListSub")
    public String userBlackListSub(String userId, Model model) {
        if(adminService.userBlackListSub(userId)){
            return "redirect:/admin/userBlackList";
        }else{
            return "redirect:/admin/userBlackListSub";
        }
    }

    @PostMapping("/userBlackList")
    public ModelAndView userBlackList(Model model) {
        List<Customer> list = adminService.userBlackList();
        if(list != null && !list.isEmpty()){
            ModelAndView mv = new ModelAndView("/userBlackList");
            mv.addObject("userBlackList",list);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("/admin");
            return mv;

        }
    }


    @PostMapping("/adminAccess")
    public ModelAndView processAdminFinder(Model model) {
        List<Admin> list  = adminService.adminList();
        for (Admin admin : list) {
            System.out.println("ID: " + admin.getId());
            System.out.println("Password: " + admin.getPassword());
            System.out.println("NickName: " + admin.getNickName());
            System.out.println("CheckSome: " + admin.isCheckSome());
            System.out.println("--------------------------------");
        }
        if (list != null && !list.isEmpty()) {
            ModelAndView mv = new ModelAndView("adminAccess");
            mv.addObject("adminList",list);
            return mv;
            //model.addAttribute("reviewList", list);
            //return "redirect:/admin/adminReview/result";
        } else {
            ModelAndView mv = new ModelAndView("/admin");
            return mv;
            //return "redirect:/admin/adminHome";
        }
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

    @PostMapping("/login")
    public String processLogin(AdminLoginDTO adminLoginDTO, Model model) {
        log.info("adminLogin DTO id , pass {} {}",adminLoginDTO.getId() , adminLoginDTO.getPassword());
        String result = adminService.adminLogin(adminLoginDTO.getId(), adminLoginDTO.getPassword());

        if ("success".equals(result)) {
            log.info("Success login");
            return "redirect:/admin/adminHome";
        } else {
            model.addAttribute("error", result);
            return "redirect:/admin/login";
        }
    }

    @PostMapping("/adminReview")
    public ModelAndView processReviewFinder(String id, Model model) {
        log.info("processReivew");
        List<ReviewListMapping> list = adminService.adminReviewCheck(id);
//        for (ReviewListMapping review : list) {
//            System.out.println("Customer Nickname: " + review.getCustomerNickName());
//            System.out.println("Grade: " + review.getGrade());
//            System.out.println("Review: " + review.getReview());
//            System.out.println("Created Date: " + review.getIsCreated());
//            System.out.println("-----------------------------------");
//        }
        if (list != null && !list.isEmpty()) {
            ModelAndView mv = new ModelAndView("/adminReview");
            mv.addObject("reviewList",list);
            return mv;
            //model.addAttribute("reviewList", list);
            //return "redirect:/admin/adminReview/result";
        } else {
            ModelAndView mv = new ModelAndView("/admin");
            return mv;
            //return "redirect:/admin/adminHome";
        }

    }
    @PostMapping("/deleteReview")
    public String deleteReview(@RequestParam("seq") Long seq) {
        if(adminService.adminDeletedReview(seq)){
            return "redirect:/admin/adminReview/result";
        }else{
            return "redirect:/admin/adminReview/result";
        }
    }

    @PostMapping("/approvalAdmin")
    public String approvalAdmin(@RequestParam("id") String id) {
        log.info("id :: {}" , id );
        if(adminService.approvalAdmin(id)){
            return "redirect:/admin/adminAccess";
        }else{
            return "redirect:/admin/adminHome";
        }
    }


}
