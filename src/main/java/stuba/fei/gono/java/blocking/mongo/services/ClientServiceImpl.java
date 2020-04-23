package stuba.fei.gono.java.blocking.mongo.services;

import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.ClientRepository;
import stuba.fei.gono.java.blocking.services.ClientService;
import stuba.fei.gono.java.pojo.Client;
import java.util.Optional;

/***
 * Implementation of ClientService for use with MongoDB.
 * @see ClientService
 */
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Client> getClientById(String id) {
        return clientRepository.findById(id);
    }
}
