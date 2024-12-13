package com.bookingHoteles.model;

import java.time.LocalDate;

public class Reserva {
    private String clienteNombre;
    private String clienteEmail;
    private String hotelNombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cantidadHabitaciones;

    public Reserva(String clienteNombre, String clienteEmail, String hotelNombre, LocalDate fechaInicio, LocalDate fechaFin, int cantidadHabitaciones) {
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.hotelNombre = hotelNombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadHabitaciones = cantidadHabitaciones;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "clienteNombre='" + clienteNombre + '\'' +
                ", clienteEmail='" + clienteEmail + '\'' +
                ", hotelNombre='" + hotelNombre + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", cantidadHabitaciones=" + cantidadHabitaciones +
                '}';
    }
}
