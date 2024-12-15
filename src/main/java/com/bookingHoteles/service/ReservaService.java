package com.bookingHoteles.service;

import com.bookingHoteles.model.Hotel;
import com.bookingHoteles.model.Reserva;
import com.bookingHoteles.model.Habitacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaService {
    private List<Reserva> reservas;

    public ReservaService() {
        reservas = new ArrayList<>();
    }

    public void registrarReserva(Reserva reserva, Hotel hotel) {
        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getTipo().equalsIgnoreCase(reserva.getTipoHabitacion())) {
                habitacion.disminuirDisponibilidad(reserva.getCantidadHabitaciones());
                break;
            }
        }
        reservas.add(reserva);
    }

    public String realizarReserva(String nombre, String apellido, String email, String nacionalidad, String telefono, String horaLlegada, Hotel hotel, String tipoHabitacion, int cantidadHabitaciones) {
        // Validar que el hotel existe y el tipo de habitación está disponible
        Habitacion habitacionSeleccionada = null;
        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getTipo().equalsIgnoreCase(tipoHabitacion) && habitacion.getCantidadDisponible() >= cantidadHabitaciones) {
                habitacionSeleccionada = habitacion;
                break;
            }
        }

        if (habitacionSeleccionada == null) {
            return "No hay disponibilidad para el tipo de habitación seleccionada.";
        }

        // Reducir disponibilidad de la habitación
        habitacionSeleccionada.disminuirDisponibilidad(cantidadHabitaciones);

        // Crear registro de la reserva
        String registro = String.format(
                "Reserva realizada por: %s %s\nEmail: %s\nNacionalidad: %s\nTeléfono: %s\nHora de llegada: %s\nHotel: %s\nTipo de habitación: %s\nCantidad de habitaciones: %d\n",
                nombre, apellido, email, nacionalidad, telefono, horaLlegada, hotel.getNombre(), tipoHabitacion, cantidadHabitaciones
        );
        System.out.println(registro); // Puedes almacenarlo en un log o una lista de reservas

        return "Se ha realizado la reserva con éxito.";
    }

    public Reserva autenticarReserva(String email, LocalDate fechaNacimiento) {
        for (Reserva reserva : reservas) {
            if (reserva.getClienteEmail().equalsIgnoreCase(email) &&
                    reserva.getClienteFechaNacimiento().equals(fechaNacimiento)) {
                return reserva; // Retorna la reserva si la autenticación es exitosa
            }
        }
        return null; // Retorna null si no se encuentra
    }

    public void actualizarReserva(Reserva reserva, Hotel hotel, String nuevoTipoHabitacion) {
        // Restaurar la disponibilidad de la habitación actual
        for (Habitacion habActual : hotel.getHabitaciones()) {
            if (habActual.getTipo().equalsIgnoreCase(reserva.getTipoHabitacion())) {
                habActual.disminuirDisponibilidad(-reserva.getCantidadHabitaciones());
                break;
            }
        }

        // Validar si la nueva habitación está disponible
        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getTipo().equalsIgnoreCase(nuevoTipoHabitacion) &&
                    habitacion.getCantidadDisponible() >= reserva.getCantidadHabitaciones()) {
                habitacion.disminuirDisponibilidad(reserva.getCantidadHabitaciones());
                reserva.setTipoHabitacion(nuevoTipoHabitacion);
                System.out.println("Reserva actualizada con éxito.");
                return;
            }
        }
        System.out.println("No hay disponibilidad para el tipo de habitación solicitado.");
    }



    public List<Reserva> obtenerReservas() {
        return reservas;
    }
}
