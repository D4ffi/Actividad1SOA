package com.daffidev.soapi.Controller;

import com.daffidev.soapi.Model.SeguimientoGPS;
import com.daffidev.soapi.Service.SeguimientoGPSService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/amachon/seguimiento")
public class SeguimientoGPSController {

    private final SeguimientoGPSService seguimientoGPSService;

    public SeguimientoGPSController(SeguimientoGPSService seguimientoGPSService) {
        this.seguimientoGPSService = seguimientoGPSService;
    }

    @GetMapping
    public List<SeguimientoGPS> obtenerTodos() {
        return seguimientoGPSService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoGPS> obtenerPorId(@PathVariable Integer id) {
        Optional<SeguimientoGPS> seguimiento = seguimientoGPSService.obtenerPorId(id);
        return seguimiento.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/repartidor/{repartidorId}")
    public List<SeguimientoGPS> obtenerPorRepartidor(@PathVariable Integer repartidorId) {
        return seguimientoGPSService.obtenerPorRepartidor(repartidorId);
    }

    @GetMapping("/repartidor/{repartidorId}/ultimo")
    public ResponseEntity<SeguimientoGPS> obtenerUltimaPosicion(@PathVariable Integer repartidorId) {
        Optional<SeguimientoGPS> ultimaPosicion = seguimientoGPSService.obtenerUltimaPosicion(repartidorId);
        return ultimaPosicion.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/repartidor/{repartidorId}/historial")
    public List<SeguimientoGPS> obtenerHistorial(
            @PathVariable Integer repartidorId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        return seguimientoGPSService.obtenerHistorialRepartidor(repartidorId, fechaInicio, fechaFin);
    }

    @PostMapping
    public ResponseEntity<SeguimientoGPS> registrarPosicion(@RequestBody SeguimientoGPS seguimiento) {
        try {
            SeguimientoGPS nuevoSeguimiento = seguimientoGPSService.registrarPosicion(seguimiento);
            return ResponseEntity.ok(nuevoSeguimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/repartidor/{repartidorId}")
    public ResponseEntity<SeguimientoGPS> registrarPosicionRapida(
            @PathVariable Integer repartidorId,
            @RequestParam double latitud,
            @RequestParam double longitud) {
        try {
            SeguimientoGPS nuevoSeguimiento = seguimientoGPSService.registrarPosicion(repartidorId, latitud, longitud);
            return ResponseEntity.ok(nuevoSeguimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            seguimientoGPSService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}