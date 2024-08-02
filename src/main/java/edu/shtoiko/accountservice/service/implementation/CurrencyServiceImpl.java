package edu.shtoiko.accountservice.service.implementation;

import edu.shtoiko.accountservice.model.entity.Currency;
import edu.shtoiko.accountservice.repository.CurrencyRepository;
import edu.shtoiko.accountservice.service.CurrencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    public Currency getCurrencyByCode(String code) {
        return currencyRepository.findCurrencyByCode(code).orElseThrow(() -> {
            log.error("Account with code={} not found", code);
            return new EntityNotFoundException("Account with id=" + code + " not found");
        });
    }
}
