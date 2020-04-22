package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.Account;

public interface AccountService {
    Account getAccountByIban(String iban);
    Account getAccountByLocalNumber(String localNumber);
}
