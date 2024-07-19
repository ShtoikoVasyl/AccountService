package edu.shtoiko.accountservice.repository;

import edu.shtoiko.accountservice.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    public Optional<Currency> findCurrencyByCode(String code);
}
