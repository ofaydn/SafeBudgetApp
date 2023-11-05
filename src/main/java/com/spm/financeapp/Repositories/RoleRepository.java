package com.spm.financeapp.Repositories;

import com.spm.financeapp.Enums.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spm.financeapp.Models.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(EnumRole name);
}
