package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> getAccountByIban(String iban);
    Optional<Account> getAccountByLocalNumber(String localNumber);
}
