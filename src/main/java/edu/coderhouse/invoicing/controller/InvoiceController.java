package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.entity.Invoice;
import edu.coderhouse.invoicing.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController //Transforma la clase en un controlador de Spring
@RequestMapping("/api/facturas")
public class InvoiceController {
    @Autowired
    private InvoiceService service;

    //Constructor
    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    //PARA TRAER TODAS LAS FACTURAS
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Invoice>> getAll(){
        Iterable<Invoice> invoices = service.getInvoices();
        return ResponseEntity.ok(invoices);
    }

    //PARA TRAER UNA FACTURA POR ID
    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<Invoice>> getById(@PathVariable long id){
        Optional<Invoice> invoice = service.getById(id);

        //Verifico si la factura con ese id existe
        if (invoice.isPresent()){
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA AGREGAR UNA NUEVA FACTURA
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        try {
            Invoice newInvoice = service.save(invoice);
            return ResponseEntity.ok(newInvoice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //PARA ACTUALIZAR UNA FACTURA
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Invoice> update(@PathVariable long id, @RequestBody Invoice invoice) {
        Optional<Invoice> updatedInvoice = service.update(id, invoice);

        // Verifico si la factura fue encontrada y actualizada
        if (updatedInvoice.isPresent()) {
            return ResponseEntity.ok(updatedInvoice.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA ELIMINAR UNA FACTURA
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
