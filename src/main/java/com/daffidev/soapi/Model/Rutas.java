package com.daffidev.soapi.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity @Table(name = "rutas")
public class Rutas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int repartidor_id;
    private Date fecha;
    private String estado;
    private double kmEstimados;
    private Date created_app;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRepartidor_id() {
        return repartidor_id;
    }

    public void setRepartidor_id(int repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getKmEstimados() {
        return kmEstimados;
    }

    public void setKmEstimados(double kmEstimados) {
        this.kmEstimados = kmEstimados;
    }

    public Date getCreated_app() {
        return created_app;
    }

    public void setCreated_app(Date created_app) {
        this.created_app = created_app;
    }
}
