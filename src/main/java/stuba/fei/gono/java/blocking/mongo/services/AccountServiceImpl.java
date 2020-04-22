package stuba.fei.gono.java.blocking.mongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.AccountRepository;
import stuba.fei.gono.java.blocking.services.AccountService;
import stuba.fei.gono.java.pojo.Account;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccountByIban(String iban) {
        return accountRepository.getAccountByIban(iban).orElse(null);
    }

    @Override
    public Account getAccountByLocalNumber(String localNumber) {
        return accountRepository.getAccountByLocalAccountNumber(localNumber).orElse(null);
    }
}
