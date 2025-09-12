package com.daffidev.soapi.Repository;

import com.daffidev.soapi.Model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientesRepository extends JpaRepository<Clientes, Integer> {
}
