package com.daffidev.soapi.Service;

import com.daffidev.soapi.Model.RutasPaquetes;
import com.daffidev.soapi.Repository.IRutasPaquetesRepository;
import com.daffidev.soapi.Repository.IRutasRepository;
import com.daffidev.soapi.Repository.IPaquetesRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RutasPaquetesService {

    private final IRutasPaquetesRepository rutasPaquetesRepository;
    private final IRutasRepository rutasRepository;
    private final IPaquetesRepository paquetesRepository;

    public RutasPaquetesService(IRutasPaquetesRepository rutasPaquetesRepository, 
                               IRutasRepository rutasRepository, 
                               IPaquetesRepository paquetesRepository) {
        this.rutasPaquetesRepository = rutasPaquetesRepository;
        this.rutasRepository = rutasRepository;
        this.paquetesRepository = paquetesRepository;
    }

    public List<RutasPaquetes> obtenerTodos() {
        return rutasPaquetesRepository.findAll();
    }

    public Optional<RutasPaquetes> obtenerPorId(Integer id) {
        return rutasPaquetesRepository.findById(id);
    }

    public List<RutasPaquetes> obtenerPorRuta(Integer rutaId) {
        return rutasPaquetesRepository.findAll().stream()
                .filter(rp -> rp.getRutaId() == rutaId)
                .sorted((rp1, rp2) -> Integer.compare(rp1.getOrdenEntrega(), rp2.getOrdenEntrega()))
                .toList();
    }

    public List<RutasPaquetes> obtenerPorPaquete(Integer paqueteId) {
        return rutasPaquetesRepository.findAll().stream()
                .filter(rp -> rp.getPaqueteId() == paqueteId)
                .toList();
    }

    public List<RutasPaquetes> obtenerEntregados(Integer rutaId) {
        return obtenerPorRuta(rutaId).stream()
                .filter(RutasPaquetes::isEntregado)
                .toList();
    }

    public List<RutasPaquetes> obtenerPendientes(Integer rutaId) {
        return obtenerPorRuta(rutaId).stream()
                .filter(rp -> !rp.isEntregado())
                .toList();
    }

    public RutasPaquetes asignarPaqueteARuta(Integer rutaId, Integer paqueteId, Integer ordenEntrega) {
        if (!rutasRepository.existsById(rutaId)) {
            throw new RuntimeException("Ruta no existe");
        }
        if (!paquetesRepository.existsById(paqueteId)) {
            throw new RuntimeException("Paquete no existe");
        }
        
        boolean yaAsignado = rutasPaquetesRepository.findAll().stream()
                .anyMatch(rp -> rp.getRutaId() == rutaId && rp.getPaqueteId() == paqueteId);
        
        if (yaAsignado) {
            throw new RuntimeException("Paquete ya está asignado a esta ruta");
        }

        RutasPaquetes rutaPaquete = new RutasPaquetes();
        rutaPaquete.setRutaId(rutaId);
        rutaPaquete.setPaqueteId(paqueteId);
        rutaPaquete.setOrdenEntrega(ordenEntrega);
        rutaPaquete.setEntregado(false);
        
        return rutasPaquetesRepository.save(rutaPaquete);
    }

    public RutasPaquetes marcarComoEntregado(Integer id) {
        Optional<RutasPaquetes> rutaPaqueteOpt = rutasPaquetesRepository.findById(id);
        if (rutaPaqueteOpt.isEmpty()) {
            throw new RuntimeException("Asignación ruta-paquete no encontrada");
        }
        
        RutasPaquetes rutaPaquete = rutaPaqueteOpt.get();
        rutaPaquete.setEntregado(true);
        rutaPaquete.setFechaEntrega(new Date());
        
        return rutasPaquetesRepository.save(rutaPaquete);
    }

    public RutasPaquetes actualizar(Integer id, RutasPaquetes rutaPaquete) {
        if (!rutasPaquetesRepository.existsById(id)) {
            throw new RuntimeException("Asignación ruta-paquete no encontrada");
        }
        if (!rutasRepository.existsById(rutaPaquete.getRutaId())) {
            throw new RuntimeException("Ruta no existe");
        }
        if (!paquetesRepository.existsById(rutaPaquete.getPaqueteId())) {
            throw new RuntimeException("Paquete no existe");
        }
        
        rutaPaquete.setId(id);
        return rutasPaquetesRepository.save(rutaPaquete);
    }

    public void eliminar(Integer id) {
        if (!rutasPaquetesRepository.existsById(id)) {
            throw new RuntimeException("Asignación ruta-paquete no encontrada");
        }
        rutasPaquetesRepository.deleteById(id);
    }

    public void eliminarPaqueteDeRuta(Integer rutaId, Integer paqueteId) {
        Optional<RutasPaquetes> rutaPaquete = rutasPaquetesRepository.findAll().stream()
                .filter(rp -> rp.getRutaId() == rutaId && rp.getPaqueteId() == paqueteId)
                .findFirst();
                
        if (rutaPaquete.isPresent()) {
            rutasPaquetesRepository.deleteById(rutaPaquete.get().getId());
        } else {
            throw new RuntimeException("Paquete no está asignado a esta ruta");
        }
    }
}