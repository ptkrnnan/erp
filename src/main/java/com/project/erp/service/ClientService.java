package com.project.erp.service;

import org.springframework.stereotype.Service;

import com.project.erp.repository.ClientRepository;
import com.project.erp.entity.Client;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> listClients() {
        return clientRepository.findAll();
    }
}
