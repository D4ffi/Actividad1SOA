package com.daffidev.soapi.Service;

import com.daffidev.soapi.Model.Clientes;
import com.daffidev.soapi.Repository.IClientesRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final IClientesRepository clientesRepository;

    public ClienteService(IClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    public List<Clientes> obtenerTodos() {
        return clientesRepository.findAll();
    }

    public Optional<Clientes> obtenerPorId(Integer id) {
        return clientesRepository.findById(id);
    }

    public Clientes crear(Clientes cliente) {
        cliente.setCreatedAt(new Date());
        return clientesRepository.save(cliente);
    }

    public Clientes actualizar(Integer id, Clientes cliente) {
        if (!clientesRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        cliente.setId(id);
        return clientesRepository.save(cliente);
    }

    public void eliminar(Integer id) {
        if (!clientesRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clientesRepository.deleteById(id);
    }

    public List<Clientes> buscarPorNombre(char nombre) {
        return clientesRepository.findAll().stream()
                .filter(c -> c.getNombre() == nombre)
                .toList();
    }

    public List<Clientes> buscarPorEmail(char email) {
        return clientesRepository.findAll().stream()
                .filter(c -> c.getEmail() == email)
                .toList();
    }
}