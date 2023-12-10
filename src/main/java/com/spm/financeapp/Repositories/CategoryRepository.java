package com.spm.financeapp.Repositories;

import com.spm.financeapp.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
