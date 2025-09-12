package com.daffidev.soapi.Controller;

import com.daffidev.soapi.Model.Clientes;
import com.daffidev.soapi.Service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/amachon/cliente")
public class ClientesController {

    private final ClienteService clienteService;

    public ClientesController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Clientes> obtenerTodos() {
        return clienteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clientes> obtenerPorId(@PathVariable Integer id) {
        Optional<Clientes> cliente = clienteService.obtenerPorId(id);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public List<Clientes> buscarPorNombre(@PathVariable char nombre) {
        return clienteService.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar/email/{email}")
    public List<Clientes> buscarPorEmail(@PathVariable char email) {
        return clienteService.buscarPorEmail(email);
    }

    @PostMapping
    public ResponseEntity<Clientes> crear(@RequestBody Clientes cliente) {
        try {
            Clientes nuevoCliente = clienteService.crear(cliente);
            return ResponseEntity.ok(nuevoCliente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clientes> actualizar(@PathVariable Integer id, @RequestBody Clientes cliente) {
        try {
            Clientes clienteActualizado = clienteService.actualizar(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            clienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}