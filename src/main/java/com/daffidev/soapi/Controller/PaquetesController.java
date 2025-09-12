package com.daffidev.soapi.Controller;

import com.daffidev.soapi.Model.Paquetes;
import com.daffidev.soapi.Service.PaquetesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/amachon/paquete")
public class PaquetesController {

    private final PaquetesService paquetesService;

    public PaquetesController(PaquetesService paquetesService) {
        this.paquetesService = paquetesService;
    }

    @GetMapping
    public List<Paquetes> obtenerTodos() {
        return paquetesService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paquetes> obtenerPorId(@PathVariable Integer id) {
        Optional<Paquetes> paquete = paquetesService.obtenerPorId(id);
        return paquete.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Paquetes> obtenerPorCliente(@PathVariable Integer clienteId) {
        return paquetesService.obtenerPorCliente(clienteId);
    }

    @PostMapping
    public ResponseEntity<Paquetes> crear(@RequestBody Paquetes paquete) {
        try {
            Paquetes nuevoPaquete = paquetesService.crear(paquete);
            return ResponseEntity.ok(nuevoPaquete);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paquetes> actualizar(@PathVariable Integer id, @RequestBody Paquetes paquete) {
        try {
            Paquetes paqueteActualizado = paquetesService.actualizar(id, paquete);
            return ResponseEntity.ok(paqueteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Paquetes> actualizarEstado(@PathVariable Integer id, @RequestBody String estado) {
        try {
            Paquetes paqueteActualizado = paquetesService.actualizarEstado(id, estado);
            return ResponseEntity.ok(paqueteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            paquetesService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}