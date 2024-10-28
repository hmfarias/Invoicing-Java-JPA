// Este DTO y los relacionados, se crean Para que no se produzca una relación cíclica y JPA pueda serializar
// correctamente el JSON al consultar una factura pero mostrando el cliente con el que está relacionada
// Ocurre que para que se muestre correctamente un cliente con el detalle de sus facturas fue necesario anotar
// las relaciones uno a muchos (cliente -> facturas)  y muchas a una (facturas -> cliente)
// usando  @JsonManagedReference y @JsonBackReference
// eso hace que al consultar una factura, (que es backreference) no se muestra el cliente asociado
// usando estos DTO se pudo solucionar este problema

package edu.coderhouse.invoicing.dto;

import edu.coderhouse.invoicing.entity.InvoiceEntity;
import edu.coderhouse.invoicing.entity.InvoiceDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private Long id;
    private LocalDateTime createdAt;
    private Double total;
    private ClientDTO client; // DTO anidado para el cliente
    private List<InvoiceDetailDTO> invoiceDetails; // Lista de detalles de la factura

    // Constructor
    public InvoiceDTO(InvoiceEntity invoiceEntity) {
        this.id = invoiceEntity.getId();
        this.createdAt = invoiceEntity.getCreatedAt();
        this.total = invoiceEntity.getTotal();
        this.client = new ClientDTO(invoiceEntity.getClient()); // Solo incluye datos necesarios del cliente

        // Mapea cada detalle de la entidad a un DTO y los agrega a la lista
        this.invoiceDetails = invoiceEntity.getInvoiceDetails().stream()
                .map(InvoiceDetailDTO::new)
                .collect(Collectors.toList());
    }
}
