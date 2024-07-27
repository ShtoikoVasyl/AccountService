package edu.shtoiko.accountservice.model.mapping;

import edu.shtoiko.accountservice.model.Dto.AccountResponse;
import edu.shtoiko.accountservice.model.account.CurrentAccount;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class AccountToResponseConverter implements Converter<CurrentAccount, AccountResponse> {
    @Override
    public AccountResponse convert(MappingContext<CurrentAccount, AccountResponse> context) {
        CurrentAccount source = context.getSource();
        return AccountResponse.builder()
            .accountId(source.getId())
            .accountName(source.getAccountName())
            .accountStatus(source.getAccountStatus())
            .accountNumber(source.getAccountNumber())
            .currencyCode(source.getCurrency().getCode())
            .ownerId(source.getOwnerId())
            .amount(source.getAmount())
            .build();
    }
}
