package com.daffidev.soapi.Service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ClienteService {
    private final Map<Long, String> clientes = new HashMap<>();
    private long idCounter = 1;

    public Collection<String> listarClientes() {
        return clientes.values();
    }

    public String obtenerCliente(Long id) {
        return clientes.getOrDefault(id, null);
    }

    public Long crearCliente(String nombre) {
        long id = idCounter++;
        clientes.put(id, nombre);
        return id;
    }

    public String actualizarCliente(Long id, String nombre) {
        clientes.put(id, nombre);
        return nombre;
    }

    public void eliminarCliente(Long id) {
        clientes.remove(id);
    }
}