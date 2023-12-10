package com.spm.financeapp.Controllers;

import com.spm.financeapp.Models.Transaction;
import com.spm.financeapp.Repositories.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class TransactionController {
    private final TransactionRepository transactionRepository;

    public TransactionController(com.spm.financeapp.Repositories.TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/transaction")
    public String getTransaction(){
        return "transaction/index";
    }
    @PostMapping("/transaction/addtransaction")
    public String postTransaction(Transaction transaction){
        Transaction newtransaction = new Transaction();
        newtransaction.setCategory(transaction.getCategory());
        if(transaction.getDate() != null) {
            newtransaction.setDate(transaction.getDate());
        }
        newtransaction.setIsPeriodic(transaction.getIsPeriodic());
        newtransaction.setPeriod(transaction.getPeriod());
        newtransaction.setPrice(transaction.getPrice());
        newtransaction.setType(transaction.getType());
        newtransaction.setUser(transaction.getUser());


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
