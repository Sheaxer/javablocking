package stuba.fei.gono.java.blocking.mongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.ClientRepository;
import stuba.fei.gono.java.blocking.services.ClientService;
import stuba.fei.gono.java.pojo.Client;
import java.util.Optional;

/***
 * <div class="en">Implementation of ClientService using CRUD operations with auto generated instance of
 * ClientRepository.</div>
 * <div class="sk">Implementácia ClientService rozhrania pomocou CRUD operácií a automaticky generovanej inštancie
 * rozhrania ClientRepository.</div>
 * @see ClientRepository
 */
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Client> getClientById(String id) {
        return clientRepository.findById(id);
    }
}
