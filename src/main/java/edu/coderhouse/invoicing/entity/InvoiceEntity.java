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

//    @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "client_id")
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private ClientEntity client;

//    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<InvoiceDetailEntity> invoiceDetails;

//
//    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<InvoiceDetailEntity> invoiceDetails;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "client_id", nullable = false)
//    private ClientEntity client;

    /*END Fields -----------------------------------------------------------*/
}
