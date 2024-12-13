package com.bookingHoteles.main;

import com.bookingHoteles.model.Habitacion;
import com.bookingHoteles.model.Hotel;
import com.bookingHoteles.model.Reserva;
import com.bookingHoteles.service.HotelService;
import com.bookingHoteles.service.ReservaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HotelService hotelService = new HotelService();
        ReservaService reservaService = new ReservaService();
        Scanner scanner = new Scanner(System.in);

        hotelService.inicializarHoteles(); // Inicializamos los hoteles y habitaciones

        int opcion;
        do {
            System.out.println("\n¡Bienvenido a Booking Hoteles!");
            System.out.println("1. Buscar hoteles");
            System.out.println("2. Crear reserva");
            System.out.println("3. Ver reservas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    buscarHoteles(hotelService, scanner);
                    break;
                case 2:
                    crearReserva(reservaService, hotelService, scanner);
                    break;
                case 3:
                    verReservas(reservaService);
                    break;
                case 4:
                    System.out.println("¡Gracias por usar Booking Hoteles!");
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        } while (opcion != 4);
    }

    private static void buscarHoteles(HotelService hotelService, Scanner scanner) {
        System.out.print("Ingrese la ciudad: ");
        String ciudad = scanner.next();

        System.out.print("¿Desea día de sol? (true/false): ");
        boolean diaDeSol = scanner.nextBoolean();

        System.out.print("Tipo de alojamiento (Hotel, Apartamento, Finca): ");
        String tipoAlojamiento = scanner.next();

        System.out.print("Cantidad de adultos: ");
        int adultos = scanner.nextInt();

        System.out.print("Cantidad de niños: ");
        int niños = scanner.nextInt();

        System.out.print("Cantidad de habitaciones: ");
        int cantidadHabitaciones = scanner.nextInt();

        List<Hotel> hoteles = hotelService.buscarHoteles(ciudad, diaDeSol, tipoAlojamiento, adultos, niños, cantidadHabitaciones);
        if (hoteles.isEmpty()) {
            System.out.println("No se encontraron alojamientos que cumplan con los criterios.");
        } else {
            System.out.println("Alojamientos encontrados:");
            for (Hotel hotel : hoteles) {
                System.out.println(hotel);
            }
        }
    }


    private static void crearReserva(ReservaService reservaService, HotelService hotelService, Scanner scanner) {
        try {
            System.out.println("Hoteles disponibles:");
            List<Hotel> hoteles = hotelService.buscarHoteles(
                    "",         // Ciudad vacía para buscar todos los hoteles
                    true,       // Día de sol (puedes ajustar según sea necesario)
                    "Hotel",    // Tipo de alojamiento
                    1,          // Cantidad de adultos (por defecto 1)
                    0,          // Cantidad de niños
                    1           // Cantidad de habitaciones
            );
            for (Hotel hotel : hoteles) {
                System.out.println("- " + hotel.getNombre());
            }


            System.out.print("Ingrese el nombre del hotel: ");
            scanner.nextLine(); // Limpia el buffer del Scanner
            String hotelNombre = scanner.nextLine();

            Hotel hotel = hotelService.buscarHotelPorNombre(hotelNombre);
            if (hotel == null) {
                System.out.println("El hotel ingresado no existe. Por favor, revise el nombre e intente nuevamente.");
                return;
            }

            System.out.print("Ingrese su nombre: ");
            String nombreCliente = scanner.nextLine();

            System.out.print("Ingrese su correo electrónico: ");
            String emailCliente = scanner.nextLine();

            System.out.print("Fecha de inicio (YYYY-MM-DD): ");
            LocalDate inicio = LocalDate.parse(scanner.nextLine());

            System.out.print("Fecha de fin (YYYY-MM-DD): ");
            LocalDate fin = LocalDate.parse(scanner.nextLine());

            if (inicio.isAfter(fin)) {
                System.out.println("La fecha de inicio debe ser anterior a la fecha de fin.");
                return;
            }

            System.out.print("Cantidad de habitaciones: ");
            int cantidad = scanner.nextInt();

            List<Habitacion> disponibles = hotelService.confirmarDisponibilidad(hotelNombre, cantidad);
            if (disponibles.isEmpty()) {
                System.out.println("No hay habitaciones disponibles.");
                return;
            }

            System.out.println("Habitaciones disponibles:");
            for (Habitacion habitacion : disponibles) {
                System.out.println(habitacion);
            }

            System.out.print("Seleccione el tipo de habitación: ");
            scanner.nextLine(); // Limpia el buffer del Scanner
            String tipoHabitacion = scanner.nextLine();

            double precioFinal = hotelService.calcularPrecioConDescuento(inicio, fin, hotel, cantidad);
            System.out.printf("Precio final con ajustes: %.2f%n", precioFinal);

            Reserva reserva = new Reserva(nombreCliente, emailCliente, hotelNombre, inicio, fin, cantidad);
            reservaService.registrarReserva(reserva, hotel, tipoHabitacion, cantidad);

            System.out.println("¡Reserva creada con éxito!");
        } catch (Exception e) {
            System.out.println("Ocurrió un error durante la creación de la reserva: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void verReservas(ReservaService reservaService) {
        List<Reserva> reservas = reservaService.obtenerReservas();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas.");
        } else {
            for (Reserva reserva : reservas) {
                System.out.println(reserva);
            }
        }
    }
}
