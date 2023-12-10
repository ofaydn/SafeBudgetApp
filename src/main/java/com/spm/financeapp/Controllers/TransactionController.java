package com.spm.financeapp.Controllers;

import com.spm.financeapp.DTOs.TransactionDTO;
import com.spm.financeapp.Enums.TransactionType;
import com.spm.financeapp.Models.Category;
import com.spm.financeapp.Models.Period;
import com.spm.financeapp.Models.Transaction;
import com.spm.financeapp.Repositories.CategoryRepository;
import com.spm.financeapp.Repositories.PeriodRepository;
import com.spm.financeapp.Repositories.TransactionRepository;
import com.spm.financeapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class TransactionController {
    @Autowired
    private final TransactionRepository transactionRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final PeriodRepository periodRepository;

    public TransactionController(TransactionRepository transactionRepository, UserRepository userRepository, CategoryRepository categoryRepository, PeriodRepository periodRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.periodRepository = periodRepository;
    }

    @GetMapping("/transaction")
    public String getTransaction(Model model){
        List<Category> categoryList = categoryRepository.findAll();
        List<Period> periodList = periodRepository.findAll();

        model.addAttribute("categoryList", categoryList);
        return "transaction/index";
    }
    @PostMapping("/transaction/addtransaction")
    public String postTransaction(TransactionDTO transaction){
        Transaction newtransaction = new Transaction();
        if (transaction.getCategoryId() != null) {
            newtransaction.setCategory(categoryRepository.findById(transaction.getCategoryId()).isPresent() ? categoryRepository.findById(transaction.getCategoryId()).get() : null);
        }
        Date date = new Date();
        newtransaction.setDate(date);
        newtransaction.setIsPeriodic(transaction.getIsPeriodic() == 1);
        if(transaction.getPeriodId() != null) {
            newtransaction.setPeriod(periodRepository.findById(transaction.getPeriodId()).isPresent() ? periodRepository.findById(transaction.getPeriodId()).get() : null);
        }
        newtransaction.setPrice(transaction.getPrice());
        if (Objects.equals(transaction.getType(), "income")){
            newtransaction.setType(TransactionType.INCOME);
        }
        if (Objects.equals(transaction.getType(), "expense")){
            newtransaction.setType(TransactionType.EXPENSE);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        newtransaction.setUser(userRepository.findByUsername(authentication.getName()).isPresent() ? userRepository.findByUsername(authentication.getName()).get() : null);

        transactionRepository.save(newtransaction);

        return "redirect:/transaction";
    }
    @GetMapping("/transaction/list")
    public String getTransactionList(Model model){
        List<Transaction> transactionList = transactionRepository.findAll();
        model.addAttribute("transactionList", transactionList);
        return "transaction/list";
    }
}
