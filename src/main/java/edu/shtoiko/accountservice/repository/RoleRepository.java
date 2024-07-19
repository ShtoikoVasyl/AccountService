package edu.shtoiko.accountservice.repository;

import edu.shtoiko.accountservice.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
