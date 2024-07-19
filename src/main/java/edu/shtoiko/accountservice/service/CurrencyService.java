package edu.shtoiko.accountservice.service;

import edu.shtoiko.accountservice.model.entity.Currency;

import java.util.Optional;

public interface CurrencyService {
    Currency getCurrencyByCode(String code);
}
