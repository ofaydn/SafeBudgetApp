package com.spm.financeapp;

import com.spm.financeapp.Enums.TransactionType;
import com.spm.financeapp.Models.Category;
import com.spm.financeapp.Models.Period;
import com.spm.financeapp.Models.Transaction;
import com.spm.financeapp.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.spm.financeapp.Repositories.TransactionRepository;
import com.spm.financeapp.Controllers.TransactionController;
import com.spm.financeapp.DTOs.TransactionDTO;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BudgetManagementTest  {

    @Autowired
    private TransactionController transactionController;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void testAddTransaction() {
        // Arrange
        TransactionDTO newTransactionDTO = new TransactionDTO();
        newTransactionDTO.setPrice(100.0);
        newTransactionDTO.setIsPeriodic("No");
        newTransactionDTO.setType("Expense");
        newTransactionDTO.setCategoryId(1);
        newTransactionDTO.setPeriodId(1);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        // Act
        String result = transactionController.postTransaction(newTransactionDTO);

        // Assert
        assertEquals("redirect:/dashboard", result);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testListTransactions() {
        // Arrange

        Category category1 = new Category();
        category1.setId(1);
        category1.setName("yemekhane");

        Period period1 = new Period();
        period1.setId(1);
        period1.setType("Monthly");

        User user1 = new User();
        user1.setId(1);
        user1.setUsername("eskhas");
        user1.setEmail("eskhas@example.com");

        Transaction transaction1 = new Transaction();
        transaction1.setType(TransactionType.EXPENSE);
        transaction1.setPrice(99.0);
        transaction1.setDate(new Date());
        transaction1.setIsPeriodic(false);
        transaction1.setCategory(category1);
        transaction1.setPeriod(period1);
        transaction1.setUser(user1);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("yemekhane");

        Period period2 = new Period();
        period2.setId(1);
        period2.setType("Monthly");

        User user2 = new User();
        user2.setId(1);
        user2.setUsername("eskhas");
        user2.setEmail("eskhas@example.com");

        Transaction transaction2 = new Transaction();

        transaction2.setType(TransactionType.EXPENSE);
        transaction2.setPrice(99.0);
        transaction2.setDate(new Date());
        transaction2.setIsPeriodic(false);
        transaction2.setCategory(category1);
        transaction2.setPeriod(period1);
        transaction2.setUser(user1);
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findAllByUserId(any(Integer.class))).thenReturn(transactions);

        // Act
        // Call the method in TransactionController that lists transactions and capture the result

        // Assert
        // Check that the result matches the expected result
    }

    @Test
    public void testDeleteTransaction() {
        // Arrange
        String id = "1";

        // Act
        String result = transactionController.deleteTransaction(id);

        // Assert
        assertEquals("redirect:/transaction/list", result);
        verify(transactionRepository, times(1)).deleteById(Integer.parseInt(id));
    }
}
