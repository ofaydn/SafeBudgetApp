package com.spm.financeapp;

import com.spm.financeapp.Controllers.HomeController;
import com.spm.financeapp.Models.User;
import com.spm.financeapp.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@SpringBootTest
public class DataIntegrityAndPrivacyTest{

    @Autowired
    private HomeController homeController;

    @MockBean
    private UserRepository userRepository;

    @MockBean(name = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Test
    public void testLoadUserByUsername() {
        // Arrange
        User user = new User("testuser", "testuser@example.com", "password", "Test", "User");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        // Assert
        assertEquals(user.getUsername(), userDetails.getUsername());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        User newUser = new User("testuser", "testuser@example.com", "password", "Test", "User");
        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(false);

        // Act
        String result = homeController.registerUser(newUser);

        // Assert
        assertEquals("redirect:/login", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegisterUserWithExistingUsername() {
        // Arrange
        User newUser = new User("testuser", "testuser@example.com", "password", "Test", "User");
        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(true);

        // Act
        String result = homeController.registerUser(newUser);

        // Assert
        assertEquals("redirect:/register?error", result);
    }

    @Test
    public void testRegisterUserWithExistingEmail() {
        // Arrange
        User newUser = new User("testuser", "testuser@example.com", "password", "Test", "User");
        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(true);

        // Act
        String result = homeController.registerUser(newUser);

        // Assert
        assertEquals("redirect:/register?error", result);
    }
}
