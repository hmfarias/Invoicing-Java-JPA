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
public class InvoiceEntity {
    /*Constructors  ----------------------------------------------------*/
    public InvoiceEntity(ClientEntity client, LocalDateTime createdAt, double total) {
        this.client = client;
        this.createdAt = createdAt;
        this.total = total;
    }

//    public InvoiceEntity(ClientEntity client, LocalDateTime createdAt, double total, List<InvoiceDetailEntity> invoiceDetails) {
//        this.client = client;
//        this.createdAt = createdAt;
//        this.total = total;
//        this.invoiceDetails = invoiceDetails;
//    }
    /*END Constructors  ------------------------------------------------*/

    /*Fields -----------------------------------------------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "IDENTITY self-generated", requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private int id;

    @Column(name = "created_at")
    @Schema(description = "Invoice creation date", requiredMode = Schema.RequiredMode.AUTO, example = "01/04/2024")
    private LocalDateTime createdAt;

    @Column(name = "total")
    @Schema(description = "Total invoice value", requiredMode = Schema.RequiredMode.AUTO, example = "7.450,00")
    private double total;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private ClientEntity client;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InvoiceDetailEntity> invoiceDetails;
    /*END Fields -----------------------------------------------------------*/
}
