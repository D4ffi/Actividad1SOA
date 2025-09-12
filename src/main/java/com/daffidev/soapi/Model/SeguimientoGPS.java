package com.daffidev.soapi.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity @Table(name = "seguimiento")
public class SeguimientoGPS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int repartidor_id;
    private double latitud;
    private double longitud;
    private Date timestamp;

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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
