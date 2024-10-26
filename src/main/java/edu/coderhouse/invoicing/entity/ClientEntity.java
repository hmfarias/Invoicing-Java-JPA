package edu.coderhouse.invoicing.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*Geters y Seters ---------------------------------------------------*/
@Setter
@Getter
/*END Geters y Seters -----------------------------------------------*/

@Entity
@Table(name = "clients")
public class ClientEntity {

    /*Constructors  ----------------------------------------------------*/

    public ClientEntity(){}

    public ClientEntity(String name, String lastName, String docNumber) {
        this.name = name;
        this.lastName = lastName;
        this.docNumber = docNumber;
    }

    // Constructor completo (cuando quieres inicializar para pruebas)
    public ClientEntity(String name, String lastName, String docNumber, List<InvoiceEntity> invoices) {
        this.name = name;
        this.lastName = lastName;
        this.docNumber = docNumber;
        this.invoices = invoices;
    }

    /*END Constructors  ------------------------------------------------*/


    /*Fields -----------------------------------------------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "IDENTITY self-generated", requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private Long id;

    @Column(name = "name", length = 75, nullable = false)
    @Schema(description = "Client's name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Marcelo")
    private String name;

    @Column(name = "lastname", length = 75, nullable = false)
    @Schema(description = "Client's lastname", requiredMode = Schema.RequiredMode.REQUIRED, example = "Farias")
    private String lastName;

    @Column(name = "docnumber", length = 11, nullable = false)
    @Schema(description = "Client's document number", requiredMode = Schema.RequiredMode.REQUIRED, example = "22135791")
    private String docNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    //@Schema(description = "List of client's invoices", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonManagedReference
    private List<InvoiceEntity> invoices;

    /*END Fields -----------------------------------------------------------*/


}
