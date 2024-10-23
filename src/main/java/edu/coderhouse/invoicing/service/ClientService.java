package edu.coderhouse.invoicing.service;

import edu.coderhouse.invoicing.entity.ClientEntity;
import edu.coderhouse.invoicing.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired //Indico que la propiedad será inyectada
    private ClientRepository repository;

    //Creo el constructor
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    //OBTENER TODOS LOS CLIENTES
    public List<ClientEntity> getClients(){
        return repository.findAll();
    }

    //OBTENER UN CLIENTE POR ID
    //uso Optional porque puede que el producto que busque esté o no esté.
    //si no está, Optional resuelve
    public Optional<ClientEntity> getById(long id){
        return repository.findById(id);
    }

    //INSERTAR UN CLIENTE
    public ClientEntity save(ClientEntity client){
        return repository.save(client);
    }

    //ACTUALIZAR UN CLIENTE
    public Optional<ClientEntity> update(long id, ClientEntity newClientData) {
        return repository.findById(id).map(client -> {
            client.setName(newClientData.getName());
            client.setLastName(newClientData.getLastName());
            client.setDocNumber(newClientData.getDocNumber());
            return repository.save(client);
        });
    }

    //ELIMINAR UN CLIENTE
    public boolean delete(long id) {
        Optional<ClientEntity> client = repository.findById(id);
        if (client.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
