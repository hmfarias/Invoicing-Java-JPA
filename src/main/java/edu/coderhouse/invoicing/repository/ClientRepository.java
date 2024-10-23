package edu.coderhouse.invoicing.repository;

import edu.coderhouse.invoicing.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
