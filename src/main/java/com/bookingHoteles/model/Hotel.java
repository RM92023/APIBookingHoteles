package com.bookingHoteles.model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String nombre;
    private String ciudad;
    private float calificacion; // Calificación como float.
    private double precioPorNoche;
    private boolean diaDeSol; // Indica si ofrece "Día de Sol".
    private List<String> servicios;
    private List<Habitacion> habitaciones;
    private String actividades; // Actividades disponibles en el alojamiento
    private boolean incluyeAlmuerzo; // Indica si incluye almuerzo
    private boolean incluyeRefrigerio; // Indica si incluye refrigerio


    // Constructor actualizado
    public Hotel(String nombre, String ciudad, float calificacion, double precioPorNoche, boolean diaDeSol, String actividades, boolean incluyeAlmuerzo, boolean incluyeRefrigerio) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.calificacion = calificacion;
        this.precioPorNoche = precioPorNoche;
        this.diaDeSol = diaDeSol;
        this.actividades = actividades;
        this.incluyeAlmuerzo = incluyeAlmuerzo;
        this.incluyeRefrigerio = incluyeRefrigerio;
        this.servicios = new ArrayList<>(); // Inicialización
        this.habitaciones = new ArrayList<>(); // Inicialización
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public String getCiudad() { return ciudad; }
    public float getCalificacion() { return calificacion; }
    public double getPrecioPorNoche() { return precioPorNoche; }
    public boolean isDiaDeSol() { return diaDeSol; }
    public List<String> getServicios() { return servicios; }
    public List<Habitacion> getHabitaciones() { return habitaciones; }
    public void agregarHabitacion(Habitacion habitacion) { habitaciones.add(habitacion); }
    public String getActividades() { return actividades; }
    public boolean isIncluyeAlmuerzo() { return incluyeAlmuerzo; }
    public boolean isIncluyeRefrigerio() { return incluyeRefrigerio; }

    public void agregarServicio(String servicio) {
        servicios.add(servicio);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", calificacion=" + calificacion +
                ", precioPorNoche=" + precioPorNoche +
                ", diaDeSol=" + diaDeSol +
                ", actividades='" + actividades + '\'' +
                ", incluyeAlmuerzo=" + incluyeAlmuerzo +
                ", incluyeRefrigerio=" + incluyeRefrigerio +
                '}';
    }
}
