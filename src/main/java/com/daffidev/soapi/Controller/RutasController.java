package com.daffidev.soapi.Controller;

import com.daffidev.soapi.Model.Rutas;
import com.daffidev.soapi.Service.RutasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/amachon/rutas")
public class RutasController {

    private final RutasService rutasService;

    public RutasController(RutasService rutasService) {
        this.rutasService = rutasService;
    }

    @GetMapping
    public List<Rutas> obtenerTodas() {
        return rutasService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rutas> obtenerPorId(@PathVariable Integer id) {
        Optional<Rutas> ruta = rutasService.obtenerPorId(id);
        return ruta.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/repartidor/{repartidorId}")
    public List<Rutas> obtenerPorRepartidor(@PathVariable Integer repartidorId) {
        return rutasService.obtenerPorRepartidor(repartidorId);
    }

    @GetMapping("/estado/{estado}")
    public List<Rutas> obtenerPorEstado(@PathVariable String estado) {
        return rutasService.obtenerPorEstado(estado);
    }

    @PostMapping
    public ResponseEntity<Rutas> crear(@RequestBody Rutas ruta) {
        try {
            Rutas nuevaRuta = rutasService.crear(ruta);
            return ResponseEntity.ok(nuevaRuta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rutas> actualizar(@PathVariable Integer id, @RequestBody Rutas ruta) {
        try {
            Rutas rutaActualizada = rutasService.actualizar(id, ruta);
            return ResponseEntity.ok(rutaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Rutas> actualizarEstado(@PathVariable Integer id, @RequestBody String estado) {
        try {
            Rutas rutaActualizada = rutasService.actualizarEstado(id, estado);
            return ResponseEntity.ok(rutaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<Rutas> iniciarRuta(@PathVariable Integer id) {
        try {
            Rutas rutaIniciada = rutasService.iniciarRuta(id);
            return ResponseEntity.ok(rutaIniciada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Rutas> finalizarRuta(@PathVariable Integer id) {
        try {
            Rutas rutaFinalizada = rutasService.finalizarRuta(id);
            return ResponseEntity.ok(rutaFinalizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            rutasService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}