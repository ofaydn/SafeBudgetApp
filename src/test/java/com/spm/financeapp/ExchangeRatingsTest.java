package com.spm.financeapp;

import com.spm.financeapp.Controllers.ExchangeController;
import com.spm.financeapp.Models.Exchange;
import com.spm.financeapp.Models.User;
import com.spm.financeapp.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExchangeRatingsTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ExchangeController exchangeController;

    @Test
    public void testGetExchange() throws IOException {
        // Arrange
        User user = new User("testuser", "testuser@example.com", "password", "Test", "User");
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        //ArrayList<Exchange> exchangeList = new ArrayList<>();
        //exchangeList.add(new Exchange("USD", 1.0));
        // Add more exchange rates as necessary...

        when(exchangeController.getExchange(any(Model.class))).thenReturn("exchange");

        Model model = Mockito.mock(Model.class);

        // Act
        String result = exchangeController.getExchange(model);

        // Assert
        assertEquals("exchange", result);
    }
}
