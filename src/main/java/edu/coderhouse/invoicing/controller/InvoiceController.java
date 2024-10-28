package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.dto.ErrorResponseDto;
import edu.coderhouse.invoicing.dto.InvoiceDTO;
import edu.coderhouse.invoicing.entity.InvoiceEntity;
import edu.coderhouse.invoicing.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController //Transforma la clase en un controlador de Spring
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    //Constructor
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    //PARA TRAER TODAS LAS FACTURAS
    @Operation(summary = "Gets all invoices", description = "Fetches all the invoices from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<InvoiceEntity>> getAll() {
        Iterable<InvoiceEntity> invoices = invoiceService.getInvoices();
        return ResponseEntity.ok(invoices);
    }

    //PARA TRAER UNA FACTURA POR ID
    @Operation(summary = "Gets an invoice by ID", description = "Fetches an invoice by its unique ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceEntity.class))),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<InvoiceEntity>> getById(
            @Parameter(description = "ID of the invoice to retrieve", required = true)
            @PathVariable long id) {
        Optional<InvoiceEntity> invoice = invoiceService.getById(id);

        if (invoice.isPresent()) {
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PARA TRAER UNA FACTURA CON EL CLIENTE RELACIONADO Y LOS DETALLES CORRESPONDIENTES
    @GetMapping(value = "/{id}/with-client", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Retrieve an invoice with client details", description = "Fetches an invoice by its ID and includes associated client and invoice details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content)
    })
    public ResponseEntity<InvoiceDTO> getInvoiceWithClient(
            @Parameter(description = "ID of the invoice to be retrieved", required = true)
            @PathVariable long id) {
        Optional<InvoiceEntity> invoice = invoiceService.getById(id);
        return invoice.map(invoiceEntity -> ResponseEntity.ok(new InvoiceDTO(invoiceEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //PARA AGREGAR UNA NUEVA FACTURA COMPLETA CON CLIENTE E INVOICE DETAILS EN EL REQUEST
    @Operation(
            summary = "Add a new invoice",
            description = "Creates a new invoice and returns the created invoice entity."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InvoiceEntity> create(
            @Parameter(description = "Invoice entity to be created", required = true)
            @RequestBody InvoiceEntity invoice) {
        try {
            InvoiceEntity newInvoice = invoiceService.save(invoice);
            return ResponseEntity.ok(newInvoice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //PARA ACTUALIZAR UNA FACTURA
    @Operation(
            summary = "Update invoice's data",
            description = "Updates an existing invoice based on the provided ID and returns the updated invoice entity."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceEntity.class))),
            @ApiResponse(responseCode = "404", description = "Invoice not found"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InvoiceEntity> update(
            @Parameter(description = "ID of the invoice to update", required = true)
            @PathVariable long id,
            @Parameter(description = "Updated invoice data", required = true)
            @RequestBody InvoiceEntity invoice) {

        Optional<InvoiceEntity> updatedInvoice = invoiceService.update(id, invoice);
        return updatedInvoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //PARA ELIMINAR UNA FACTURA
    @Operation(summary = "Remove an invoice", description = "Deletes an invoice by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Invoice not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the invoice to delete", required = true)
            @PathVariable long id) {

        boolean deleted = invoiceService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    //PARA ASIGNAR LA FACTURA A UN CLIENTE
    @Operation(summary = "Assign a customer to an invoice", description = "Assigns a specified client to a specified invoice based on their IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client assigned to invoice successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvoiceEntity.class))),
            @ApiResponse(responseCode = "404", description = "Client or Invoice not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/{clientId}/asign/{invoiceId}")
    public ResponseEntity<InvoiceEntity> asignClient(
            @Parameter(description = "ID of the client to be assigned", required = true)
            @PathVariable Long clientId,
            @Parameter(description = "ID of the invoice to assign the client to", required = true)
            @PathVariable Long invoiceId) throws Exception {

        InvoiceEntity invoice = invoiceService.asignClient(clientId, invoiceId);
        return ResponseEntity.ok(invoice);
    }

}
