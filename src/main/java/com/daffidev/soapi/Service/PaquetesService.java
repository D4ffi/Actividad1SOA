package com.daffidev.soapi.Service;

import com.daffidev.soapi.Model.Paquetes;
import com.daffidev.soapi.Model.Clientes;
import com.daffidev.soapi.Repository.IPaquetesRepository;
import com.daffidev.soapi.Repository.IClientesRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaquetesService {

    private final IPaquetesRepository paquetesRepository;
    private final IClientesRepository clientesRepository;

    public PaquetesService(IPaquetesRepository paquetesRepository, IClientesRepository clientesRepository) {
        this.paquetesRepository = paquetesRepository;
        this.clientesRepository = clientesRepository;
    }

    public List<Paquetes> obtenerTodos() {
        return paquetesRepository.findAll();
    }

    public Optional<Paquetes> obtenerPorId(Integer id) {
        return paquetesRepository.findById(id);
    }

    public List<Paquetes> obtenerPorCliente(Integer clienteId) {
        return paquetesRepository.findAll().stream()
                .filter(p -> p.getClienteId() == clienteId)
                .toList();
    }

    public Paquetes crear(Paquetes paquete) {
        if (!clientesRepository.existsById(paquete.getClienteId())) {
            throw new RuntimeException("Cliente no existe");
        }
        paquete.setCreatedAt(new Date());
        paquete.setEstado("PENDIENTE");
        return paquetesRepository.save(paquete);
    }

    public Paquetes actualizar(Integer id, Paquetes paquete) {
        if (!paquetesRepository.existsById(id)) {
            throw new RuntimeException("Paquete no encontrado");
        }
        if (!clientesRepository.existsById(paquete.getClienteId())) {
            throw new RuntimeException("Cliente no existe");
        }
        paquete.setId(id);
        return paquetesRepository.save(paquete);
    }

    public void eliminar(Integer id) {
        if (!paquetesRepository.existsById(id)) {
            throw new RuntimeException("Paquete no encontrado");
        }
        paquetesRepository.deleteById(id);
    }

    public Paquetes actualizarEstado(Integer id, String estado) {
        Optional<Paquetes> paqueteOpt = paquetesRepository.findById(id);
        if (paqueteOpt.isEmpty()) {
            throw new RuntimeException("Paquete no encontrado");
        }
        Paquetes paquete = paqueteOpt.get();
        paquete.setEstado(estado);
        return paquetesRepository.save(paquete);
    }
}