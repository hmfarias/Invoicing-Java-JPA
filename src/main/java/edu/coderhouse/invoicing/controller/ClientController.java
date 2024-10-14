package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.entity.Client;
import edu.coderhouse.invoicing.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController //Transforma la clase en un controlador de Spring
@RequestMapping("/api/clientes")
public class ClientController {
    @Autowired
    private ClientService service;

    //Constructor
    public ClientController(ClientService service) {
        this.service = service;
    }

    //PARA TRAER TODOS LOS CLIENTES
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Client>> getAll(){
        Iterable<Client> clients = service.getClients();
        return ResponseEntity.ok(clients);
    }

    //PARA TRAER UN CLIENTE POR ID
    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<Client>> getById(@PathVariable long id){
        Optional<Client> client = service.getById(id);

        //Verifico si el cliente con ese id existe
        if (client.isPresent()){
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA AGREGAR UN NUEVO CLIENTE
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Client> create(@RequestBody Client client) {
        try {
            Client newClient = service.save(client);
            return ResponseEntity.ok(newClient);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //PARA ACTUALIZAR UN CLIENTE
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Client> update(@PathVariable long id, @RequestBody Client client) {
        Optional<Client> updatedClient = service.update(id, client);

        // Verifico si el cliente fue encontrado y actualizado
        if (updatedClient.isPresent()) {
            return ResponseEntity.ok(updatedClient.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA ELIMINAR UN CLIENTE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = service.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
