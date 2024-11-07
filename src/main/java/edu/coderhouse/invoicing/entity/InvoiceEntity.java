package edu.coderhouse.invoicing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/*Constructor, Geters y Seters ---------------------------------------------------*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*END Geters y Seters -----------------------------------------------*/

@Entity
@Table(name = "invoice")
@Schema(description = "Represents an invoice in the system, including the client, creation date, total amount, and associated invoice details.")
public class InvoiceEntity {
    /*Constructors  ----------------------------------------------------*/
    public InvoiceEntity(ClientEntity client, LocalDateTime createdAt, double total) {
        this.client = client;
        this.createdAt = createdAt;
        this.total = total;
    }

    /*Fields -----------------------------------------------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "IDENTITY self-generated", requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private Long id;

    @Column(name = "created_at")
    @Schema(description = "Invoice creation date", requiredMode = Schema.RequiredMode.AUTO, example = "01/04/2024")
    private LocalDateTime createdAt;

    @Column(name = "total")
    @Schema(description = "Total invoice value", requiredMode = Schema.RequiredMode.AUTO, example = "7.450,00")
    private double total;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @JsonBackReference
    @Schema(description = "The client to whom the invoice is associated", example = "ClientEntity{id=1, name=\"Marcelo\", lastName=\"Farias\"}")
    private ClientEntity client;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Schema(description = "List of invoice details associated with this invoice", example = "[InvoiceDetailEntity{invoiceDetailId=1, amount=5, price=100.0}, ...]")
    private List<InvoiceDetailEntity> invoiceDetails;
    /*END Fields -----------------------------------------------------------*/
}
