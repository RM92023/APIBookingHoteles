package com.bookingHoteles.model;

import java.time.LocalDate;

public class Reserva {
    private String clienteNombre;
    private String clienteApellido;
    private String clienteEmail;
    private LocalDate clienteFechaNacimiento; // Nuevo campo para autenticación
    private String hotelNombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cantidadHabitaciones;
    private String tipoHabitacion; // Tipo de habitación reservada

    public Reserva(String clienteNombre, String clienteApellido, String clienteEmail, LocalDate clienteFechaNacimiento,
                   String hotelNombre, LocalDate fechaInicio, LocalDate fechaFin, int cantidadHabitaciones, String tipoHabitacion) {
        this.clienteNombre = clienteNombre;
        this.clienteApellido = clienteApellido;
        this.clienteEmail = clienteEmail;
        this.clienteFechaNacimiento = clienteFechaNacimiento;
        this.hotelNombre = hotelNombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.tipoHabitacion = tipoHabitacion;
    }

    // Getters
    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public LocalDate getClienteFechaNacimiento() {
        return clienteFechaNacimiento;
    }

    public String getHotelNombre() {
        return hotelNombre;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "clienteNombre='" + clienteNombre + '\'' +
                ", clienteApellido='" + clienteApellido + '\'' +
                ", clienteEmail='" + clienteEmail + '\'' +
                ", clienteFechaNacimiento=" + clienteFechaNacimiento +
                ", hotelNombre='" + hotelNombre + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", cantidadHabitaciones=" + cantidadHabitaciones +
                ", tipoHabitacion='" + tipoHabitacion + '\'' +
                '}';
    }
}
