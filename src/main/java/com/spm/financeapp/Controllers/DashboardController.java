package com.spm.financeapp.Controllers;

import com.spm.financeapp.Models.Transaction;
import com.spm.financeapp.Models.User;
import com.spm.financeapp.Repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {
    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName);
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "dashboard/index";
    }
}
