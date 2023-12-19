package com.spm.financeapp.Controllers;

import com.spm.financeapp.DTOs.TransactionDTO;
import com.spm.financeapp.Enums.TransactionType;
import com.spm.financeapp.Models.Category;
import com.spm.financeapp.Models.Period;
import com.spm.financeapp.Models.Transaction;
import com.spm.financeapp.Models.User;
import com.spm.financeapp.Repositories.CategoryRepository;
import com.spm.financeapp.Repositories.PeriodRepository;
import com.spm.financeapp.Repositories.TransactionRepository;
import com.spm.financeapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
        model.addAttribute("periodList", periodList);
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
        newtransaction.setIsPeriodic(Objects.equals(transaction.getIsPeriodic(), "on"));
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

        return "redirect:/dashboard";
    }
    @GetMapping("/transaction/list")
    public String getTransactionList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        String nameSurname = userRepository.findByUsername(currentPrincipalName).get().getFirstname() + " " + userRepository.findByUsername(currentPrincipalName).get().getLastname();

        model.addAttribute("namesurname", nameSurname);

        List<Transaction> transactionList = transactionRepository.findAllByUserId(userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null);

        model.addAttribute("transactionList", transactionList);
        return "transaction/list";
    }
    @RequestMapping(value = "/transaction/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteTransaction(@PathVariable String id){
        //check whether id is number or not
        if (id.matches("[0-9]+")){
            transactionRepository.deleteById(Integer.parseInt(id));
        }
        return "redirect:/transaction/list";
    }
}
