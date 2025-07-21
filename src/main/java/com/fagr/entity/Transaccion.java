package com.fagr.entity;

public class Transaccion {

    public enum Tipo {
        DEPOSITO, RETIRO, TRANSFERENCIA
    }

    private String id;
    private Tipo tipo;
    private double monto;
    private String fecha;

    public Transaccion(String id, Tipo tipo, double monto, String fecha) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }
    public Tipo getTipo() {
        return tipo;
    }
    public double getMonto() {
        return monto;
    }
    public String getFecha() {
        return fecha;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }   

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public String toString() {
        return "Transaccion{" +
                "id='" + id + '\'' +
                ", tipo=" + tipo +
                ", monto=" + monto +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
