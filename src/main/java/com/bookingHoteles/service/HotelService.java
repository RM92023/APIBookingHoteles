package com.bookingHoteles.service;

import com.bookingHoteles.model.Hotel;
import com.bookingHoteles.model.Habitacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelService {
    private List<Hotel> hoteles;

    public HotelService() {
        hoteles = new ArrayList<>();
    }

    public void inicializarHoteles() {
        Hotel hotel1 = new Hotel(
                "Hotel Sol", "Cartagena", 4.5f, 200000, true,
                "Natación, Paseos en bote", true, false
        );
        hotel1.agregarHabitacion(new Habitacion("Sencilla", "1 cama, vista al mar", 100000, 10));
        hotel1.agregarHabitacion(new Habitacion("Doble", "2 camas, vista al mar", 150000, 5));
        hoteles.add(hotel1);

        Hotel hotel2 = new Hotel(
                "Hotel Luna", "Medellín", 5.0f, 350000, false,
                "Spa, Caminatas", false, true
        );
        hotel2.agregarHabitacion(new Habitacion("Suite", "Cama king, jacuzzi", 500000, 2));
        hoteles.add(hotel2);
    }

    public Hotel buscarHotelPorNombre(String nombre) {
        for (Hotel hotel : hoteles) {
            if (hotel.getNombre().equalsIgnoreCase(nombre)) {
                return hotel;
            }
        }
        return null;
    }

    public List<Hotel> buscarHoteles(String ciudad, boolean diaDeSol, String tipoAlojamiento, int adultos, int niños, int cantidadHabitaciones) {
        List<Hotel> resultado = new ArrayList<>();
        for (Hotel hotel : hoteles) {
            boolean cumpleCiudad = ciudad.isEmpty() || hotel.getCiudad().equalsIgnoreCase(ciudad);
            boolean cumpleDiaDeSol = hotel.isDiaDeSol() == diaDeSol;
            boolean cumpleHabitaciones = hotel.getHabitaciones().stream().anyMatch(h -> h.getCantidadDisponible() >= cantidadHabitaciones);

            // Solo incluir si cumple con los filtros de ciudad, día de sol y habitaciones disponibles
            if (cumpleCiudad && cumpleDiaDeSol && cumpleHabitaciones) {
                resultado.add(hotel);
            }
        }
        return resultado;
    }

    public double calcularPrecioConDescuento(LocalDate inicio, LocalDate fin, Hotel hotel, int cantidadHabitaciones) {
        // Encontrar la habitación más barata
        Habitacion habitacionMasBarata = hotel.getHabitaciones().stream()
                .min((h1, h2) -> Double.compare(h1.getPrecioPorNoche(), h2.getPrecioPorNoche()))
                .orElse(null);

        if (habitacionMasBarata == null) return 0;

        double precioBase = habitacionMasBarata.getPrecioPorNoche() * cantidadHabitaciones * (fin.getDayOfMonth() - inicio.getDayOfMonth() + 1);

        // Aplicar aumentos y descuentos
        int diaInicio = inicio.getDayOfMonth();
        int diaFin = fin.getDayOfMonth();

        if (diaInicio >= 5 && diaFin <= 10) {
            precioBase *= 0.92; // Descuento del 8%
        } else if (diaInicio >= 10 && diaFin <= 15) {
            precioBase *= 1.10; // Aumento del 10%
        } else if (diaInicio >= 26) {
            precioBase *= 1.15; // Aumento del 15%
        }

        return precioBase;
    }

    public List<Habitacion> confirmarDisponibilidad(String hotelNombre, int cantidad) {
        Hotel hotel = buscarHotelPorNombre(hotelNombre);
        if (hotel == null) return null;

        List<Habitacion> disponibles = new ArrayList<>();
        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getCantidadDisponible() >= cantidad) {
                disponibles.add(habitacion);
            }
        }
        return disponibles;
    }
}
