package com.daffidev.soapi.Service;

import com.daffidev.soapi.Model.Rutas;
import com.daffidev.soapi.Repository.IRutasRepository;
import com.daffidev.soapi.Repository.IRepartidoresRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RutasService {

    private final IRutasRepository rutasRepository;
    private final IRepartidoresRepository repartidoresRepository;

    public RutasService(IRutasRepository rutasRepository, IRepartidoresRepository repartidoresRepository) {
        this.rutasRepository = rutasRepository;
        this.repartidoresRepository = repartidoresRepository;
    }

    public List<Rutas> obtenerTodas() {
        return rutasRepository.findAll();
    }

    public Optional<Rutas> obtenerPorId(Integer id) {
        return rutasRepository.findById(id);
    }

    public List<Rutas> obtenerPorRepartidor(Integer repartidorId) {
        return rutasRepository.findAll().stream()
                .filter(r -> r.getRepartidor_id() == repartidorId)
                .toList();
    }

    public List<Rutas> obtenerPorEstado(String estado) {
        return rutasRepository.findAll().stream()
                .filter(r -> estado.equals(r.getEstado()))
                .toList();
    }

    public Rutas crear(Rutas ruta) {
        if (!repartidoresRepository.existsById(ruta.getRepartidor_id())) {
            throw new RuntimeException("Repartidor no existe");
        }
        ruta.setCreated_app(new Date());
        ruta.setEstado("PLANIFICADA");
        return rutasRepository.save(ruta);
    }

    public Rutas actualizar(Integer id, Rutas ruta) {
        if (!rutasRepository.existsById(id)) {
            throw new RuntimeException("Ruta no encontrada");
        }
        if (!repartidoresRepository.existsById(ruta.getRepartidor_id())) {
            throw new RuntimeException("Repartidor no existe");
        }
        ruta.setId(id);
        return rutasRepository.save(ruta);
    }

    public void eliminar(Integer id) {
        if (!rutasRepository.existsById(id)) {
            throw new RuntimeException("Ruta no encontrada");
        }
        rutasRepository.deleteById(id);
    }

    public Rutas actualizarEstado(Integer id, String estado) {
        Optional<Rutas> rutaOpt = rutasRepository.findById(id);
        if (rutaOpt.isEmpty()) {
            throw new RuntimeException("Ruta no encontrada");
        }
        Rutas ruta = rutaOpt.get();
        ruta.setEstado(estado);
        return rutasRepository.save(ruta);
    }

    public Rutas iniciarRuta(Integer id) {
        return actualizarEstado(id, "EN_CURSO");
    }

    public Rutas finalizarRuta(Integer id) {
        return actualizarEstado(id, "COMPLETADA");
    }
}