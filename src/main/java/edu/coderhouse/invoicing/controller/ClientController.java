package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.dto.ErrorResponseDto;
import edu.coderhouse.invoicing.entity.ClientEntity;
import edu.coderhouse.invoicing.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController //Transforma la clase en un controlador de Spring
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    //Constructor
    public ClientController(ClientService service) {
        this.clientService = service;
    }

    // PARA TRAER TODOS LOS CLIENTES
    @Operation(summary = "Gets all clients", description = "Retrieves a list of all clients from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientEntity[].class))),
            @ApiResponse(responseCode = "204", description = "No clients found"),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ClientEntity>> getAll() {
        List<ClientEntity> clients = clientService.getClients();

        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay clientes
        }

        return ResponseEntity.ok(clients); // 200 OK si se encuentran clientes
    }

    // PARA TRAER UN CLIENTE POR ID
    @Operation(summary = "Gets a client by ID", description = "Retrieves a specific client by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientEntity.class))),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClientEntity> getById(
            @Parameter(description = "Unique ID of the client to be retrieved", required = true)
            @PathVariable long id) {

        // Busco el cliente por ID
        return clientService.getById(id)
                .map(ResponseEntity::ok) // Si el cliente se encuentra, retornar con 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no existe, retornar 404
    }

    // PARA AGREGAR UN NUEVO CLIENTE
    @Operation(summary = "Add a new client", description = "Creates a new client and returns the created entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(
            @Parameter(description = "Client entity to be created", required = true)
            @Valid @RequestBody ClientEntity client, BindingResult result) {

        // Verificar si hay errores de validación
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Field " + err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors); // 400 Bad Request
        }

        try {
            // Guardar el cliente
            ClientEntity newClient = clientService.save(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient); // 201 Created
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    // PARA ACTUALIZAR UN CLIENTE
    @Operation(summary = "Update a customer's data", description = "Updates a client's information based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientEntity.class))),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClientEntity> update(
            @Parameter(description = "ID of the client to update", example = "1", required = true)
            @PathVariable long id,

            @Parameter(description = "Updated client entity details", required = true)
            @Valid @RequestBody ClientEntity client) {
        try {
            // Intento actualizar el cliente
            Optional<ClientEntity> updatedClient = clientService.update(id, client);

            // Verifico si el cliente fue encontrado y actualizado
            return updatedClient.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build()); // 404 Not Found si no existe
        } catch (Exception e) {
            e.printStackTrace(); // Log para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

// PARA ELIMINAR UN CLIENTE
    @Operation(summary = "Remove a client", description = "Deletes a client by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the client to delete", example = "1", required = true)
            @PathVariable long id) {
        try {
            boolean deleted = clientService.delete(id);

            if (deleted) {
                return ResponseEntity.noContent().build(); // 204 No Content
            } else {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

}
