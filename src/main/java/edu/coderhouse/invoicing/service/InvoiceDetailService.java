package edu.coderhouse.invoicing.service;

import edu.coderhouse.invoicing.entity.Invoice;
import edu.coderhouse.invoicing.entity.InvoiceDetail;
import edu.coderhouse.invoicing.repository.InvoiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailService {

    @Autowired
    private InvoiceDetailRepository repository;

    // Constructor para inyección de dependencias
    public InvoiceDetailService(InvoiceDetailRepository invoiceDetailRepository) {
        this.repository = invoiceDetailRepository;
    }

    // Guardar un detalle de factura
    public InvoiceDetail save(InvoiceDetail invoiceDetail) {
        return repository.save(invoiceDetail);
    }

    // Guardar una lista de detalles de factura
    public List<InvoiceDetail> saveAll(List<InvoiceDetail> invoiceDetails) {
        return repository.saveAll(invoiceDetails);
    }

    // Obtener todos los detalles de facturas
    public List<InvoiceDetail> getInvoiceDetails() {
        return repository.findAll();
    }

    // Obtener un detalle de factura por su ID
    public InvoiceDetail getById(long id) {
        return repository.findById(id).orElse(null);
    }

    // Actualizar un detalle
    public Optional<InvoiceDetail> update(long id, InvoiceDetail newInvoiceDetailData) {
        return repository.findById(id).map(invoiceDetail -> {
            invoiceDetail.setInvoiceDetailId(newInvoiceDetailData.getInvoiceDetailId());
            invoiceDetail.setAmount(newInvoiceDetailData.getAmount());
            invoiceDetail.setProduct(newInvoiceDetailData.getProduct());
            invoiceDetail.setPrice(newInvoiceDetailData.getPrice());
            return repository.save(invoiceDetail);
        });
    }

    // Eliminar un detalle de factura
    public boolean delete(long id) {
        Optional<InvoiceDetail> invoiceDetail = repository.findById(id);
        if (invoiceDetail.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
