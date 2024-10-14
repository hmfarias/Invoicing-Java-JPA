package edu.coderhouse.invoicing.controller;

import edu.coderhouse.invoicing.entity.InvoiceDetail;
import edu.coderhouse.invoicing.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController //Transforma la clase en un controlador de Spring
@RequestMapping("/api/detalle-facturas")
public class InvoiceDetailController {
    @Autowired
    private InvoiceDetailService service;

    //Constructor
    public InvoiceDetailController(InvoiceDetailService service) {
        this.service = service;
    }

    //PARA TRAER TODOS LOS DETALLES
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<InvoiceDetail>> getAll(){
        Iterable<InvoiceDetail> invoiceDetails = service.getInvoiceDetails();
        return ResponseEntity.ok(invoiceDetails);
    }

    //PARA TRAER UN DETALLE POR ID
    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<InvoiceDetail>> getById(@PathVariable long id){
        Optional<InvoiceDetail> invoiceDetail = Optional.ofNullable(service.getById(id));

        //Verifico si el detalle con ese id existe
        if (invoiceDetail.isPresent()){
            return ResponseEntity.ok(invoiceDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA AGREGAR UN NUEVO DETALLE
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InvoiceDetail> create(@RequestBody InvoiceDetail invoiceDetail) {
        try {
            InvoiceDetail newInvoiceDetail = service.save(invoiceDetail);
            return ResponseEntity.ok(newInvoiceDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //PARA ACTUALIZAR UN DETALLE
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InvoiceDetail> update(@PathVariable long id, @RequestBody InvoiceDetail invoiceDetail) {
        Optional<InvoiceDetail> updatedInvoiceDetail = service.update(id, invoiceDetail);

        // Verifico si el detalle fue encontrada y actualizada
        if (updatedInvoiceDetail.isPresent()) {
            return ResponseEntity.ok(updatedInvoiceDetail.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PARA ELIMINAR UN DETALLE
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
