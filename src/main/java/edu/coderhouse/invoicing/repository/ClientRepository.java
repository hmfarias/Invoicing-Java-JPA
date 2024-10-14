package edu.coderhouse.invoicing.repository;

import edu.coderhouse.invoicing.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
