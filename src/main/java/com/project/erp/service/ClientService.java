package com.project.erp.service;

import com.project.erp.entity.Client;
import com.project.erp.repository.ClientRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client updateClient(Long id, Client client) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        existing.setName(client.getName());
        existing.setEmail(client.getEmail());
        existing.setPhone(client.getPhone());

        return clientRepository.save(existing);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
