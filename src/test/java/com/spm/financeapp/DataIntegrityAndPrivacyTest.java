package com.spm.financeapp;

import com.spm.financeapp.Models.User;
import com.spm.financeapp.Repositories.UserRepository;
import com.spm.financeapp.Security.Services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DataIntegrityAndPrivacyTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUnauthorizedAccess() throws Exception {
        // Create a mock user without the necessary permissions
        User mockUser = new User("mockUser", "mockEmail", "mockPassword", "Mock", "User");

        // Save the mock user in the UserRepository
        userRepository.save(mockUser);

        // Load the mock user using UserDetailsServiceImpl
        UserDetails userDetails = userDetailsService.loadUserByUsername("mockUser");

        // Attempt to authenticate with the mock user's credentials
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Attempt to access financial data
        MvcResult result = mockMvc.perform(get("/transaction/*"))
                .andExpect(status().isForbidden())
                .andReturn();

        // Check the response
        assertEquals(403, result.getResponse().getStatus());
    }
}
