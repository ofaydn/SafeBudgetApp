package com.spm.financeapp.Controllers;

import com.spm.financeapp.Models.User;
import com.spm.financeapp.Repositories.TransactionRepository;
import com.spm.financeapp.Repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class DashboardController {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public DashboardController(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        String nameSurname = userRepository.findByUsername(currentPrincipalName).get().getFirstName() + " " + userRepository.findByUsername(currentPrincipalName).get().getLastName();

        model.addAttribute("namesurname", nameSurname);

        double totalIncome;
        double totalExpense;

        totalIncome = transactionRepository.allIncomeByUserId(userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null) == null ? 0 : transactionRepository.allIncomeByUserId(userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null);
        totalExpense = transactionRepository.allExpenseByUserId(userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null) == null ? 0 : transactionRepository.allExpenseByUserId(userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null);

        double AllIncomes = Math.round(totalIncome/(totalIncome+totalExpense)*100);
        double AllExpenses = Math.round(totalExpense/(totalIncome+totalExpense)*100);

        int numberOfTransactions = transactionRepository.numberOfTransactionsByUserId(userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null);

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        double[] profitByMonth = new double[12];
        for (int i = 0; i < 12; i++) {
            int id = userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null;

            profitByMonth[i] = (transactionRepository.totalIncomeByUserIdAndMonthAndYear(id, i+1, year) == null ? 0 : transactionRepository.totalIncomeByUserIdAndMonthAndYear(id, i+1, year)) - (transactionRepository.totalExpenseByUserIdAndMonthAndYear(id, i+1, year) == null ? 0 : transactionRepository.totalExpenseByUserIdAndMonthAndYear(id, i+1, year));
        }

        double totalProfit =0;
        for (int i = 0; i < 12; i++) {
            totalProfit += profitByMonth[i];
        }

        double thisMonthProfit = profitByMonth[month-1];

        double thisMonthExpense = transactionRepository.totalExpenseByUserIdAndMonthAndYear(userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null, month, year) == null ? 0 : transactionRepository.totalExpenseByUserIdAndMonthAndYear(userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null, month, year);

        double thisMonthIncome = transactionRepository.totalIncomeByUserIdAndMonthAndYear(userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null, month, year) == null ? 0 : transactionRepository.totalIncomeByUserIdAndMonthAndYear(userRepository.findByUsername(currentPrincipalName).isPresent() ? userRepository.findByUsername(currentPrincipalName).get().getId() : null, month, year);

        double thisMonthExpenseRate = Math.round(thisMonthExpense/(thisMonthExpense+thisMonthIncome)*100);

        double thisMonthIncomeRate = Math.round(thisMonthIncome/(thisMonthExpense+thisMonthIncome)*100);

        double overall = totalIncome - totalExpense;


        model.addAttribute("income", thisMonthIncomeRate);
        model.addAttribute("expense", thisMonthExpenseRate);
        model.addAttribute("numberOfTransactions", numberOfTransactions);
        model.addAttribute("profitByMonth", profitByMonth);
        model.addAttribute("totalProfit", totalProfit);
        model.addAttribute("thisMonthProfit", thisMonthProfit);
        model.addAttribute("overall", overall);

        return "dashboard/index";
    }
}
