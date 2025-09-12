package com.daffidev.soapi.Controller;

import com.daffidev.soapi.Model.RutasPaquetes;
import com.daffidev.soapi.Service.RutasPaquetesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/amachon/rutas-paquetes")
public class RutasPaquetesController {

    private final RutasPaquetesService rutasPaquetesService;

    public RutasPaquetesController(RutasPaquetesService rutasPaquetesService) {
        this.rutasPaquetesService = rutasPaquetesService;
    }

    @GetMapping
    public List<RutasPaquetes> obtenerTodos() {
        return rutasPaquetesService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutasPaquetes> obtenerPorId(@PathVariable Integer id) {
        Optional<RutasPaquetes> rutaPaquete = rutasPaquetesService.obtenerPorId(id);
        return rutaPaquete.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ruta/{rutaId}")
    public List<RutasPaquetes> obtenerPorRuta(@PathVariable Integer rutaId) {
        return rutasPaquetesService.obtenerPorRuta(rutaId);
    }

    @GetMapping("/paquete/{paqueteId}")
    public List<RutasPaquetes> obtenerPorPaquete(@PathVariable Integer paqueteId) {
        return rutasPaquetesService.obtenerPorPaquete(paqueteId);
    }

    @GetMapping("/ruta/{rutaId}/entregados")
    public List<RutasPaquetes> obtenerEntregados(@PathVariable Integer rutaId) {
        return rutasPaquetesService.obtenerEntregados(rutaId);
    }

    @GetMapping("/ruta/{rutaId}/pendientes")
    public List<RutasPaquetes> obtenerPendientes(@PathVariable Integer rutaId) {
        return rutasPaquetesService.obtenerPendientes(rutaId);
    }

    @PostMapping("/asignar")
    public ResponseEntity<RutasPaquetes> asignarPaqueteARuta(
            @RequestParam Integer rutaId,
            @RequestParam Integer paqueteId,
            @RequestParam Integer ordenEntrega) {
        try {
            RutasPaquetes rutaPaquete = rutasPaquetesService.asignarPaqueteARuta(rutaId, paqueteId, ordenEntrega);
            return ResponseEntity.ok(rutaPaquete);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutasPaquetes> actualizar(@PathVariable Integer id, @RequestBody RutasPaquetes rutaPaquete) {
        try {
            RutasPaquetes rutaPaqueteActualizado = rutasPaquetesService.actualizar(id, rutaPaquete);
            return ResponseEntity.ok(rutaPaqueteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/entregar")
    public ResponseEntity<RutasPaquetes> marcarComoEntregado(@PathVariable Integer id) {
        try {
            RutasPaquetes rutaPaqueteEntregado = rutasPaquetesService.marcarComoEntregado(id);
            return ResponseEntity.ok(rutaPaqueteEntregado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            rutasPaquetesService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/ruta/{rutaId}/paquete/{paqueteId}")
    public ResponseEntity<Void> eliminarPaqueteDeRuta(@PathVariable Integer rutaId, @PathVariable Integer paqueteId) {
        try {
            rutasPaquetesService.eliminarPaqueteDeRuta(rutaId, paqueteId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}