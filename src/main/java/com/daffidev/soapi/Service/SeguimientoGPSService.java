package com.daffidev.soapi.Service;

import com.daffidev.soapi.Model.SeguimientoGPS;
import com.daffidev.soapi.Repository.ISeguimientoGPSRepository;
import com.daffidev.soapi.Repository.IRepartidoresRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SeguimientoGPSService {

    private final ISeguimientoGPSRepository seguimientoGPSRepository;
    private final IRepartidoresRepository repartidoresRepository;

    public SeguimientoGPSService(ISeguimientoGPSRepository seguimientoGPSRepository, IRepartidoresRepository repartidoresRepository) {
        this.seguimientoGPSRepository = seguimientoGPSRepository;
        this.repartidoresRepository = repartidoresRepository;
    }

    public List<SeguimientoGPS> obtenerTodos() {
        return seguimientoGPSRepository.findAll();
    }

    public Optional<SeguimientoGPS> obtenerPorId(Integer id) {
        return seguimientoGPSRepository.findById(id);
    }

    public List<SeguimientoGPS> obtenerPorRepartidor(Integer repartidorId) {
        return seguimientoGPSRepository.findAll().stream()
                .filter(s -> s.getRepartidor_id() == repartidorId)
                .toList();
    }

    public Optional<SeguimientoGPS> obtenerUltimaPosicion(Integer repartidorId) {
        return seguimientoGPSRepository.findAll().stream()
                .filter(s -> s.getRepartidor_id() == repartidorId)
                .max((s1, s2) -> s1.getTimestamp().compareTo(s2.getTimestamp()));
    }

    public SeguimientoGPS registrarPosicion(SeguimientoGPS seguimiento) {
        if (!repartidoresRepository.existsById(seguimiento.getRepartidor_id())) {
            throw new RuntimeException("Repartidor no existe");
        }
        seguimiento.setTimestamp(new Date());
        return seguimientoGPSRepository.save(seguimiento);
    }

    public SeguimientoGPS registrarPosicion(Integer repartidorId, double latitud, double longitud) {
        if (!repartidoresRepository.existsById(repartidorId)) {
            throw new RuntimeException("Repartidor no existe");
        }
        SeguimientoGPS seguimiento = new SeguimientoGPS();
        seguimiento.setRepartidor_id(repartidorId);
        seguimiento.setLatitud(latitud);
        seguimiento.setLongitud(longitud);
        seguimiento.setTimestamp(new Date());
        return seguimientoGPSRepository.save(seguimiento);
    }

    public void eliminar(Integer id) {
        if (!seguimientoGPSRepository.existsById(id)) {
            throw new RuntimeException("Registro de seguimiento no encontrado");
        }
        seguimientoGPSRepository.deleteById(id);
    }

    public List<SeguimientoGPS> obtenerHistorialRepartidor(Integer repartidorId, Date fechaInicio, Date fechaFin) {
        return seguimientoGPSRepository.findAll().stream()
                .filter(s -> s.getRepartidor_id() == repartidorId)
                .filter(s -> s.getTimestamp().after(fechaInicio) && s.getTimestamp().before(fechaFin))
                .sorted((s1, s2) -> s1.getTimestamp().compareTo(s2.getTimestamp()))
                .toList();
    }
}