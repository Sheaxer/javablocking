package stuba.fei.gono.java.blocking.services;

import org.springframework.stereotype.Service;
import stuba.fei.gono.java.pojo.Client;

import java.util.Optional;

public interface ClientService {

    Optional<Client> getClientById(String id);

}
