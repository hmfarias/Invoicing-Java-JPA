package edu.coderhouse.invoicing.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clients")
public class Client {

    /*Constructors  ----------------------------------------------------*/

    public Client(){}

    public Client(String name, String lastName, String docNumber) {
        this.name = name;
        this.lastName = lastName;
        this.docNumber = docNumber;
    }

    // Constructor completo (cuando quieres inicializar para pruebas)
    public Client(String name, String lastName, String docNumber, List<Invoice> invoices) {
        this.name = name;
        this.lastName = lastName;
        this.docNumber = docNumber;
        this.invoices = invoices;
    }

    /*END Constructors  ------------------------------------------------*/


    /*Fields -----------------------------------------------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 75, nullable = false)
    private String name;

    @Column(name = "lastname", length = 75, nullable = false)
    private String lastName;

    @Column(name = "docnumber", length = 11, nullable = false)
    private String docNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    /*END Fields -----------------------------------------------------------*/

    /*Geters y Seters ---------------------------------------------------*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    /*END Geters y Seters ---------------------------------------------------*/
}
