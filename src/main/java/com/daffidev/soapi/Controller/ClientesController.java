package com.daffidev.soapi.Controller;

    import com.daffidev.soapi.Service.ClienteService;
    import org.springframework.web.bind.annotation.*;
    import java.util.*;

    @RestController
    @RequestMapping("/amachon/cliente")
    public class ClientesController {

        private final ClienteService clienteService;

        public ClientesController(ClienteService clienteService) {
            this.clienteService = clienteService;
        }

        @GetMapping
        public Collection<String> listarClientes() {
            return clienteService.listarClientes();
        }

        @GetMapping("/{id}")
        public String obtenerCliente(@PathVariable Long id) {
            return clienteService.obtenerCliente(id);
        }

        @PostMapping
        public Long crearCliente(@RequestBody String nombre) {
            return clienteService.crearCliente(nombre);
        }

        @PutMapping("/{id}")
        public String actualizarCliente(@PathVariable Long id, @RequestBody String nombre) {
            return clienteService.actualizarCliente(id, nombre);
        }

        @DeleteMapping("/{id}")
        public void eliminarCliente(@PathVariable Long id) {
            clienteService.eliminarCliente(id);
        }
    }