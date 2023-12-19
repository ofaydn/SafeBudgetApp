package com.spm.financeapp.Controllers;

import com.spm.financeapp.Enums.EnumRole;
import com.spm.financeapp.Models.Role;
import com.spm.financeapp.Models.User;
import com.spm.financeapp.Repositories.RoleRepository;
import com.spm.financeapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return "admin";
    }

    @GetMapping("/rates")
    public String getRates(){return "exchange";}
    @GetMapping("/login")
    public String login(){
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser"){
            return "redirect:/dashboard";
        }

        return "login";
    }
    @PostMapping("/logout")
    public String logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login";
    }


    @GetMapping("/register")
    public String registerUser(){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User newuser) {

        if (userRepository.existsByUsername(newuser.getUsername())) {
            return "redirect:/register?error";
        }

        if (userRepository.existsByEmail(newuser.getEmail())) {
            return "redirect:/register?error";
        }

        User user = new User(newuser.getUsername(),
                newuser.getEmail(),
                passwordEncoder.encode(newuser.getPassword()),
                newuser.getFirstName(),
                newuser.getLastName());

        Set<Role> roles = new HashSet<>();


        Role userRole = roleRepository.findByName(EnumRole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);


        user.setRole(roles);
        userRepository.save(user);

        return "redirect:/login" ;
    }

    @GetMapping("/profile")
    public String getUser (Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        String firstName = userRepository.findByUsername(currentPrincipalName).get().getFirstName();
        String lastName = userRepository.findByUsername(currentPrincipalName).get().getLastName();
        String email = userRepository.findByUsername(currentPrincipalName).get().getEmail();
        Integer id = userRepository.findByUsername(currentPrincipalName).get().getId();
        model.addAttribute("id",id);
        model.addAttribute("username", currentPrincipalName);
        model.addAttribute("firstname", firstName);
        model.addAttribute("lastname", lastName);
        return "profile";
    }
    @PostMapping("/profile/update")//putmapping
    public String profileUpdate(){
        return "redirect:/profile";
    }


}
