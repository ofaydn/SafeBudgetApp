package com.spm.financeapp.Controllers;

import com.spm.financeapp.DTOs.LoginRequest;
import com.spm.financeapp.DTOs.MessageResponse;
import com.spm.financeapp.Enums.EnumRole;
import com.spm.financeapp.Models.Role;
import com.spm.financeapp.Models.User;
import com.spm.financeapp.Repositories.RoleRepository;
import com.spm.financeapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.spm.financeapp.DTOs.SignupRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.spm.financeapp.Enums.EnumRole.ADMIN;

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

//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
//        return ResponseEntity.ok(userDetails.getUsername());
//    }

    @GetMapping("/signup")
    public String registerUser(){
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(User newuser) {

        User user = new User(newuser.getUsername(),
                newuser.getEmail(),
                passwordEncoder.encode(newuser.getPassword()));

        Set<Role> roles = new HashSet<>();


        Role userRole = roleRepository.findByName(EnumRole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);


        user.setRole(roles);
        userRepository.save(user);

        return "redirect:/signin" ;
    }

}
