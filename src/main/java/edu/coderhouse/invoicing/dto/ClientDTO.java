package edu.coderhouse.invoicing.dto;

import edu.coderhouse.invoicing.entity.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientDTO {
    private Long id;
    private String name;
    private String lastName;
    private String docNumber;

    // Constructor
    public ClientDTO(ClientEntity client) {
        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.docNumber = client.getDocNumber();
    }

}
