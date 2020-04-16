package stuba.fei.gono.java.blocking.services;

import org.springframework.stereotype.Service;
import stuba.fei.gono.java.pojo.Client;
@Service
public interface ClientService {

    Client getClientById(String id);

}
