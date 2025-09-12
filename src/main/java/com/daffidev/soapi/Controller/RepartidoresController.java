package com.daffidev.soapi.Controller;

import com.daffidev.soapi.Model.Repartidores;
import com.daffidev.soapi.Service.RepartidoresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/amachon/repartidores")
public class RepartidoresController {

    private final RepartidoresService repartidoresService;

    public RepartidoresController(RepartidoresService repartidoresService) {
        this.repartidoresService = repartidoresService;
    }

    @GetMapping
    public List<Repartidores> obtenerTodos() {
        return repartidoresService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repartidores> obtenerPorId(@PathVariable Integer id) {
        Optional<Repartidores> repartidor = repartidoresService.obtenerPorId(id);
        return repartidor.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activos")
    public List<Repartidores> obtenerActivos() {
        return repartidoresService.obtenerActivos();
    }

    @PostMapping
    public ResponseEntity<Repartidores> crear(@RequestBody Repartidores repartidor) {
        try {
            Repartidores nuevoRepartidor = repartidoresService.crear(repartidor);
            return ResponseEntity.ok(nuevoRepartidor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repartidores> actualizar(@PathVariable Integer id, @RequestBody Repartidores repartidor) {
        try {
            Repartidores repartidorActualizado = repartidoresService.actualizar(id, repartidor);
            return ResponseEntity.ok(repartidorActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Repartidores> cambiarEstado(@PathVariable Integer id, @RequestBody Boolean activo) {
        try {
            Repartidores repartidorActualizado = repartidoresService.activarDesactivar(id, activo);
            return ResponseEntity.ok(repartidorActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            repartidoresService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}