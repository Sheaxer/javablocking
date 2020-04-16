package stuba.fei.gono.java.blocking.mongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import stuba.fei.gono.java.blocking.mongo.repositories.ClientRepository;
import stuba.fei.gono.java.blocking.services.ClientService;
import stuba.fei.gono.java.pojo.Client;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getClientById(String id) {
        return clientRepository.findById(id).orElse( null);
    }
}
