package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.dto.ErrorResponseDto;
import edu.coderhouse.invoicing.entity.Invoice;
import edu.coderhouse.invoicing.service.InvoiceService;
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
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    //Constructor
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    //PARA TRAER TODAS LAS FACTURAS
    @Operation(summary = "Gets all invoices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfull operation"),
            @ApiResponse(responseCode = "400", description = "Invalid Request"),
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Invoice>> getAll(){
        Iterable<Invoice> invoices = invoiceService.getInvoices();
        return ResponseEntity.ok(invoices);
    }

    //PARA TRAER UNA FACTURA POR ID
    @Operation(summary = "Gets an invoice by ID")
    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<Invoice>> getById(@PathVariable long id){
        Optional<Invoice> invoice = invoiceService.getById(id);

        //Verifico si la factura con ese id existe
        if (invoice.isPresent()){
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA AGREGAR UNA NUEVA FACTURA
    @Operation(summary = "Add a new invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfull operation"),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        try {
            Invoice newInvoice = invoiceService.save(invoice);
            return ResponseEntity.ok(newInvoice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //PARA ACTUALIZAR UNA FACTURA
    @Operation(summary = "Update invoice's data")
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Invoice> update(@PathVariable long id, @RequestBody Invoice invoice) {
        Optional<Invoice> updatedInvoice = invoiceService.update(id, invoice);

        // Verifico si la factura fue encontrada y actualizada
        if (updatedInvoice.isPresent()) {
            return ResponseEntity.ok(updatedInvoice.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA ELIMINAR UNA FACTURA
    @Operation(summary = "Remove an invoice")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = invoiceService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    //PARA ASIGNAR EL CLIENTE A UNA FACTURA
    @Operation(summary = "Assign a customer to an invoice")
    @PostMapping("/{clientId}/asign/{invoiceId}")
    public ResponseEntity<Invoice> asignClient(@PathVariable Long clientId, @PathVariable Long invoiceId) throws Exception {
        Invoice invoice = invoiceService.asignClient(clientId,invoiceId);

        return ResponseEntity.ok(invoice);
    }

}
