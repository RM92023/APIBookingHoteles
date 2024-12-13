package com.bookingHoteles.model;

public class Habitacion {
    private String tipo;
    private String descripcion;
    private double precioPorNoche;
    private int cantidadDisponible;

    public Habitacion(String tipo, String descripcion, double precioPorNoche, int cantidadDisponible) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precioPorNoche = precioPorNoche;
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public double getPrecioPorNoche() { return precioPorNoche; }
    public int getCantidadDisponible() { return cantidadDisponible; }

    public void disminuirDisponibilidad(int cantidad) {
        if (cantidadDisponible >= cantidad) {
            cantidadDisponible -= cantidad;
        }
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioPorNoche=" + precioPorNoche +
                ", cantidadDisponible=" + cantidadDisponible +
                '}';
    }
}
