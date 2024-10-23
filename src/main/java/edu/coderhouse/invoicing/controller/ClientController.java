package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.dto.ErrorResponseDto;
import edu.coderhouse.invoicing.entity.ClientEntity;
import edu.coderhouse.invoicing.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController //Transforma la clase en un controlador de Spring
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    //Constructor
    public ClientController(ClientService service) {
        this.clientService = service;
    }

    //PARA TRAER TODOS LOS CLIENTES
    @Operation(summary = "Gets all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfull operation"),
            @ApiResponse(responseCode = "400", description = "Invalid Request"),
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ClientEntity>> getAll(){
        List<ClientEntity> clients = clientService.getClients();
        return ResponseEntity.ok(clients);
    }

    //PARA TRAER UN CLIENTE POR ID
    @Operation(summary = "Gets a client by ID")
    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<ClientEntity>> getById(@PathVariable long id){
        Optional<ClientEntity> client = clientService.getById(id);

        //Verifico si el cliente con ese id existe
        if (client.isPresent()){
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA AGREGAR UN NUEVO CLIENTE
    @Operation(summary = "Add a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfull operation"),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClientEntity> create(@RequestBody ClientEntity client) {
        try {
            ClientEntity newClient = clientService.save(client);
            return ResponseEntity.ok(newClient);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //PARA ACTUALIZAR UN CLIENTE
    @Operation(summary = "Update a customer's data")
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClientEntity> update(@PathVariable long id, @RequestBody ClientEntity client) {
        Optional<ClientEntity> updatedClient = clientService.update(id, client);

        // Verifico si el cliente fue encontrado y actualizado
        return updatedClient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //PARA ELIMINAR UN CLIENTE
    @Operation(summary = "Remove a client")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = clientService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
