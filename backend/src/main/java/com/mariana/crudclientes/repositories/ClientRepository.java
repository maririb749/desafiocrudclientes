package com.mariana.crudclientes.repositories;

import com.mariana.crudclientes.entitie.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
