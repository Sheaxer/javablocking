package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import stuba.fei.gono.java.pojo.Account;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account,String> {
    Optional<Account> getAccountByIban(String iban);
    Optional<Account> getAccountByLocalAccountNumber(String localAccountNumber);
}
