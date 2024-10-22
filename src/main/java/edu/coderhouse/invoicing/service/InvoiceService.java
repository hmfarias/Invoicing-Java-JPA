package edu.coderhouse.invoicing.service;

import edu.coderhouse.invoicing.entity.Client;
import edu.coderhouse.invoicing.entity.Invoice;
import edu.coderhouse.invoicing.repository.ClientRepository;
import edu.coderhouse.invoicing.repository.InvoiceRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    // Constructor para inyección de dependencias
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // Guardar una factura
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    // Guardar una lista de facturas
    public List<Invoice> saveAll(List<Invoice> invoices) {
        return invoiceRepository.saveAll(invoices);
    }

    // Obtener todas las facturas
    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    // Obtener una factura por su ID
    public Optional<Invoice> getById(long id) {
        return invoiceRepository.findById(id);
    }

    // Actualizar una factura
//    public Invoice update(Invoice invoice) {
//        return repository.save(invoice);
//    }

    public Optional<Invoice> update(long id, Invoice newInvoiceData) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoice.setClient(newInvoiceData.getClient());
            invoice.setCreatedAt(newInvoiceData.getCreatedAt());
            invoice.setTotal(newInvoiceData.getTotal());
            return invoiceRepository.save(invoice);
        });
    }

    // Eliminar una factura
    public boolean delete(long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            invoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Asignar factura a usuario
    public Invoice asignClient(Long clientId, Long invoiceId) throws Exception{
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);

        if(clientOpt.isPresent() && invoiceOpt.isPresent()){
            Invoice invoice = invoiceOpt.get();
            Client client = clientOpt.get();

            invoice.setClient(client);

            return invoiceRepository.save(invoice);
        }else {
            throw new BadRequestException("Invoice or Client not found");
        }

    }

}