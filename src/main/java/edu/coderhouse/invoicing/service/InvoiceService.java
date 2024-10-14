package edu.coderhouse.invoicing.service;

import edu.coderhouse.invoicing.entity.Client;
import edu.coderhouse.invoicing.entity.Invoice;
import edu.coderhouse.invoicing.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository repository;

    // Constructor para inyección de dependencias
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.repository = invoiceRepository;
    }

    // Guardar una factura
    public Invoice save(Invoice invoice) {
        return repository.save(invoice);
    }

    // Guardar una lista de facturas
    public List<Invoice> saveAll(List<Invoice> invoices) {
        return repository.saveAll(invoices);
    }

    // Obtener todas las facturas
    public List<Invoice> getInvoices() {
        return repository.findAll();
    }

    // Obtener una factura por su ID
    public Optional<Invoice> getById(long id) {
        return repository.findById(id);
    }

    // Actualizar una factura
//    public Invoice update(Invoice invoice) {
//        return repository.save(invoice);
//    }

    public Optional<Invoice> update(long id, Invoice newInvoiceData) {
        return repository.findById(id).map(invoice -> {
            invoice.setClient(newInvoiceData.getClient());
            invoice.setCreatedAt(newInvoiceData.getCreatedAt());
            invoice.setTotal(newInvoiceData.getTotal());
            return repository.save(invoice);
        });
    }

    // Eliminar una factura
    public boolean delete(long id) {
        Optional<Invoice> invoice = repository.findById(id);
        if (invoice.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}