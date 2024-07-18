package edu.shtoiko.accountservice.service;

import edu.shtoiko.accountservice.model.entity.Currency;

public interface CurrencyService {
    Currency getCurrencyByCode(String code);
}
