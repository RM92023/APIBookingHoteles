package com.bookingHoteles.service;

import com.bookingHoteles.model.Hotel;
import com.bookingHoteles.model.Reserva;
import com.bookingHoteles.model.Habitacion;

import java.util.ArrayList;
import java.util.List;

public class ReservaService {
    private List<Reserva> reservas;

    public ReservaService() {
        reservas = new ArrayList<>();
    }

    public void registrarReserva(Reserva reserva, Hotel hotel, String tipoHabitacion, int cantidad) {
        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getTipo().equalsIgnoreCase(tipoHabitacion)) {
                habitacion.disminuirDisponibilidad(cantidad);
                break;
            }
        }
        reservas.add(reserva);
    }

    public List<Reserva> obtenerReservas() {
        return reservas;
    }
}
