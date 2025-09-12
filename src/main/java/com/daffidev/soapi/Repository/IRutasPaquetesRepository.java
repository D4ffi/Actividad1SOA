package com.daffidev.soapi.Repository;

import com.daffidev.soapi.Model.RutasPaquetes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRutasPaquetesRepository extends JpaRepository<RutasPaquetes, Integer> {
}