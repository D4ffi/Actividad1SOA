package com.daffidev.soapi.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity @Table(name = "rutas-paquetes")
public class RutasPaquetes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int rutaId;
    private int paqueteId;
    private int ordenEntrega;
    private boolean entregado;
    private Date fechaEntrega;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRutaId() {
        return rutaId;
    }

    public void setRutaId(int rutaId) {
        this.rutaId = rutaId;
    }

    public int getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(int paqueteId) {
        this.paqueteId = paqueteId;
    }

    public int getOrdenEntrega() {
        return ordenEntrega;
    }

    public void setOrdenEntrega(int ordenEntrega) {
        this.ordenEntrega = ordenEntrega;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
