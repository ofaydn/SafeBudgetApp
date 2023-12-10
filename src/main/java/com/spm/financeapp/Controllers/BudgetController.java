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

