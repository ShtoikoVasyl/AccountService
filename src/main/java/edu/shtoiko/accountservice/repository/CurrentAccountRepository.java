package edu.shtoiko.accountservice.repository;

import edu.shtoiko.accountservice.model.account.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {
    List<CurrentAccount> findAllByOwnerId(long id);

    boolean existsByAccountNumber(long accountNumber);

    CurrentAccount findByAccountNumber(Long accountNumber);
}
