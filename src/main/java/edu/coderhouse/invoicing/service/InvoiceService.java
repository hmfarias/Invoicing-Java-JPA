package edu.coderhouse.invoicing.service;

import edu.coderhouse.invoicing.entity.ClientEntity;
import edu.coderhouse.invoicing.entity.InvoiceEntity;
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
    public InvoiceEntity save(InvoiceEntity invoice) {
        return invoiceRepository.save(invoice);
    }

    // Guardar una lista de facturas
    public List<InvoiceEntity> saveAll(List<InvoiceEntity> invoices) {
        return invoiceRepository.saveAll(invoices);
    }

    // Obtener todas las facturas
    public List<InvoiceEntity> getInvoices() {
        return invoiceRepository.findAll();
    }

    // Obtener una factura por su ID
    public Optional<InvoiceEntity> getById(long id) {
        return invoiceRepository.findById(id);
    }

    // Actualizar una factura
//    public Invoice update(Invoice invoice) {
//        return repository.save(invoice);
//    }

    public Optional<InvoiceEntity> update(long id, InvoiceEntity newInvoiceData) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoice.setClient(newInvoiceData.getClient());
            invoice.setCreatedAt(newInvoiceData.getCreatedAt());
            invoice.setTotal(newInvoiceData.getTotal());
            return invoiceRepository.save(invoice);
        });
    }

    // Eliminar una factura
    public boolean delete(long id) {
        Optional<InvoiceEntity> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            invoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Asignar factura a usuario
    public InvoiceEntity asignClient(Long clientId, Long invoiceId) throws Exception{
        Optional<ClientEntity> clientOpt = clientRepository.findById(clientId);
        Optional<InvoiceEntity> invoiceOpt = invoiceRepository.findById(invoiceId);

        if(clientOpt.isPresent() && invoiceOpt.isPresent()){
            InvoiceEntity invoice = invoiceOpt.get();
            ClientEntity client = clientOpt.get();

            invoice.setClient(client);

            return invoiceRepository.save(invoice);
        }else {
            throw new BadRequestException("Invoice or Client not found");
        }

    }

}