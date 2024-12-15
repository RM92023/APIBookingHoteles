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
            System.out.println("4. Realizar reserva");
            System.out.println("5. Gestionar reserva");
            System.out.println("6. Salir");
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
                    realizarReserva(hotelService, reservaService, scanner);
                    break;
                case 5:
                    gestionarReserva(hotelService, reservaService, scanner);
                    break;
                case 6:
                    System.out.println("¡Gracias por usar Booking Hoteles!");
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        } while (opcion != 6);
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
            for (Hotel hotel : hotelService.buscarHoteles("", true, "Hotel", 1, 0, 1)) {
                System.out.println("- " + hotel.getNombre());
            }

            System.out.print("Ingrese el nombre del hotel: ");
            scanner.nextLine(); // Limpiar el buffer
            String hotelNombre = scanner.nextLine();

            Hotel hotel = hotelService.buscarHotelPorNombre(hotelNombre);
            if (hotel == null) {
                System.out.println("El hotel ingresado no existe.");
                return;
            }

            System.out.print("Ingrese su nombre: ");
            String nombreCliente = scanner.nextLine();

            System.out.print("Ingrese su apellido: ");
            String apellidoCliente = scanner.nextLine();

            System.out.print("Ingrese su fecha de nacimiento (YYYY-MM-DD): ");
            LocalDate fechaNacimientoCliente = LocalDate.parse(scanner.nextLine());

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
            scanner.nextLine(); // Limpiar el buffer
            String tipoHabitacion = scanner.nextLine();

            double precioBase = hotel.getPrecioPorNoche() * cantidad;
            double precioFinal = hotelService.calcularPrecioConDescuento(inicio, fin, hotel, cantidad);
            System.out.printf("Precio final con ajustes: %.2f%n", precioFinal);

            Reserva reserva = new Reserva(
                    nombreCliente,
                    apellidoCliente,
                    emailCliente,
                    fechaNacimientoCliente,
                    hotelNombre,
                    inicio,
                    fin,
                    cantidad,
                    tipoHabitacion
            );

            reservaService.registrarReserva(reserva, hotel); // Usamos la reserva directamente
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

    private static void confirmarHabitaciones(HotelService hotelService, Scanner scanner) {
        System.out.print("Ingrese el nombre del hotel: ");
        scanner.nextLine(); // Limpiar el buffer
        String hotelNombre = scanner.nextLine();

        System.out.print("Fecha de inicio (YYYY-MM-DD): ");
        LocalDate inicio = LocalDate.parse(scanner.nextLine());

        System.out.print("Fecha de fin (YYYY-MM-DD): ");
        LocalDate fin = LocalDate.parse(scanner.nextLine());

        System.out.print("Cantidad de adultos: ");
        int adultos = scanner.nextInt();

        System.out.print("Cantidad de niños: ");
        int niños = scanner.nextInt();

        System.out.print("Cantidad de habitaciones: ");
        int cantidadHabitaciones = scanner.nextInt();

        List<Habitacion> habitaciones = hotelService.confirmarHabitaciones(hotelNombre, inicio, fin, adultos, niños, cantidadHabitaciones);

        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones disponibles para los criterios especificados.");
        } else {
            System.out.println("Habitaciones disponibles:");
            for (Habitacion habitacion : habitaciones) {
                System.out.println(habitacion);
            }
        }
    }

    private static void realizarReserva(HotelService hotelService, ReservaService reservaService, Scanner scanner) {
        System.out.print("Ingrese el nombre del hotel: ");
        scanner.nextLine(); // Limpiar el buffer
        String hotelNombre = scanner.nextLine();

        Hotel hotel = hotelService.buscarHotelPorNombre(hotelNombre);
        if (hotel == null) {
            System.out.println("El hotel ingresado no existe.");
            return;
        }

        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese su apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();

        System.out.print("Ingrese su nacionalidad: ");
        String nacionalidad = scanner.nextLine();

        System.out.print("Ingrese su número de teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Ingrese la hora aproximada de llegada (HH:mm): ");
        String horaLlegada = scanner.nextLine();

        System.out.print("Ingrese el tipo de habitación: ");
        String tipoHabitacion = scanner.nextLine();

        System.out.print("Ingrese la cantidad de habitaciones: ");
        int cantidadHabitaciones = scanner.nextInt();

        String resultado = reservaService.realizarReserva(nombre, apellido, email, nacionalidad, telefono, horaLlegada, hotel, tipoHabitacion, cantidadHabitaciones);
        System.out.println(resultado);
    }

    private static void gestionarReserva(HotelService hotelService, ReservaService reservaService, Scanner scanner) {
        System.out.print("Ingrese su email: ");
        scanner.nextLine(); // Limpiar buffer
        String email = scanner.nextLine();

        System.out.print("Ingrese su fecha de nacimiento (YYYY-MM-DD): ");
        LocalDate fechaNacimiento = LocalDate.parse(scanner.nextLine());

        Reserva reserva = reservaService.autenticarReserva(email, fechaNacimiento);
        if (reserva == null) {
            System.out.println("No se encontró ninguna reserva con los datos proporcionados.");
            return;
        }

        System.out.println("Reserva encontrada:");
        System.out.println(reserva);

        System.out.println("¿Qué desea actualizar?");
        System.out.println("1. Cambiar habitación");
        System.out.println("2. Cambiar alojamiento");
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                System.out.println("Habitaciones disponibles:");
                Hotel hotel = hotelService.buscarHotelPorNombre(reserva.getHotelNombre());
                for (Habitacion habitacion : hotel.getHabitaciones()) {
                    System.out.println(habitacion);
                }

                System.out.print("Seleccione el nuevo tipo de habitación: ");
                scanner.nextLine(); // Limpiar buffer
                String nuevoTipoHabitacion = scanner.nextLine();

                reservaService.actualizarReserva(reserva, hotel, nuevoTipoHabitacion);
                break;

            case 2:
                System.out.println("La reserva será cancelada. Proceda a crear una nueva reserva.");
                reservaService.obtenerReservas().remove(reserva);
                crearReserva(reservaService, hotelService, scanner);
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }



}
