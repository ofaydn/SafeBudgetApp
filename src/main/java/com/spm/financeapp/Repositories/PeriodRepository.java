package com.spm.financeapp.Repositories;

import com.spm.financeapp.Models.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period, Integer> {
}
