package com.spm.financeapp;
import com.spm.financeapp.Controllers.HomeController;
import com.spm.financeapp.Enums.EnumRole;
import com.spm.financeapp.Models.Role;
import com.spm.financeapp.Models.User;
import com.spm.financeapp.Repositories.RoleRepository;
import com.spm.financeapp.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@SpringBootTest
public class SecurityTest {

    @Autowired
    private HomeController homeController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        homeController = new HomeController(userRepository, roleRepository, passwordEncoder);
    }
    @Test
    public void testRegisterUser() {
        // Arrange
        Role role = new Role();
        role.setName(EnumRole.USER);
        User newUser = new User("testuser", "testuser@example.com", "password", "Test", "User");
        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(EnumRole.USER)).thenReturn(Optional.of(role));

        // Act
        String result = homeController.registerUser(newUser);

        // Assert
        assertEquals("redirect:/login", result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(newUser.getPassword());
    }
}
