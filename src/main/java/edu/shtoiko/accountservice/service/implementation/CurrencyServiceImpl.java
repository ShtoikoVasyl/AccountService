package edu.shtoiko.accountservice.service.implementation;

import edu.shtoiko.accountservice.model.entity.Currency;
import edu.shtoiko.accountservice.repository.CurrencyRepository;
import edu.shtoiko.accountservice.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    public Currency getCurrencyByCode(String code){
        return currencyRepository.findCurrencyByCode(code);
    }
}
