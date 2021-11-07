package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}

