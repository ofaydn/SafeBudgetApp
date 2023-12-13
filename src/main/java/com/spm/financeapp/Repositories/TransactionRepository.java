package com.spm.financeapp.Repositories;

import com.spm.financeapp.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.user.id =:id")
    List<Transaction> findAllByUserId(Integer id);

    @Query("SELECT SUM(t.price) FROM Transaction t WHERE t.user.id =:id AND t.type = 'EXPENSE'")
    Double allExpenseByUserId(Integer id);

    @Query("SELECT SUM(t.price) FROM Transaction t WHERE t.user.id =:id AND t.type = 'INCOME'")
    Double allIncomeByUserId(Integer id);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.user.id =:id")
    int numberOfTransactionsByUserId(Integer id);

    @Query("SELECT SUM(t.price) FROM Transaction t WHERE t.user.id =:id AND MONTH(t.date) =:month AND YEAR(t.date) =:year AND t.type = 'EXPENSE'")
    Double totalExpenseByUserIdAndMonthAndYear(Integer id, Integer month, Integer year);

    @Query("SELECT SUM(t.price) FROM Transaction t WHERE t.user.id =:id AND MONTH(t.date) =:month AND YEAR(t.date) =:year AND t.type = 'INCOME'")
    Double totalIncomeByUserIdAndMonthAndYear(Integer id, Integer month, Integer year);




}
