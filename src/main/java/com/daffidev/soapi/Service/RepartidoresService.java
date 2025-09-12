package com.daffidev.soapi.Service;

import com.daffidev.soapi.Model.Repartidores;
import com.daffidev.soapi.Repository.IRepartidoresRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RepartidoresService {

    private final IRepartidoresRepository repartidoresRepository;

    public RepartidoresService(IRepartidoresRepository repartidoresRepository) {
        this.repartidoresRepository = repartidoresRepository;
    }

    public List<Repartidores> obtenerTodos() {
        return repartidoresRepository.findAll();
    }

    public Optional<Repartidores> obtenerPorId(Integer id) {
        return repartidoresRepository.findById(id);
    }

    public List<Repartidores> obtenerActivos() {
        return repartidoresRepository.findAll().stream()
                .filter(Repartidores::isActive)
                .toList();
    }

    public Repartidores crear(Repartidores repartidor) {
        repartidor.setCreatedAt(new Date());
        repartidor.setActive(true);
        return repartidoresRepository.save(repartidor);
    }

    public Repartidores actualizar(Integer id, Repartidores repartidor) {
        if (!repartidoresRepository.existsById(id)) {
            throw new RuntimeException("Repartidor no encontrado");
        }
        repartidor.setId(id);
        return repartidoresRepository.save(repartidor);
    }

    public void eliminar(Integer id) {
        if (!repartidoresRepository.existsById(id)) {
            throw new RuntimeException("Repartidor no encontrado");
        }
        repartidoresRepository.deleteById(id);
    }

    public Repartidores activarDesactivar(Integer id, boolean activo) {
        Optional<Repartidores> repartidorOpt = repartidoresRepository.findById(id);
        if (repartidorOpt.isEmpty()) {
            throw new RuntimeException("Repartidor no encontrado");
        }
        Repartidores repartidor = repartidorOpt.get();
        repartidor.setActive(activo);
        return repartidoresRepository.save(repartidor);
    }
}