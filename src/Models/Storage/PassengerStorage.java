/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Storage;

import Models.Passenger;
import Controllers.Utils.Response;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author Rashid
 */
public class PassengerStorage {

    private final Map<Long, Passenger> passengers;

    public PassengerStorage() {
        this.passengers = new TreeMap<>(); // ordenados por ID
    }

    public Response register(Passenger passenger) {
        if (passenger == null) return new Response(400, "Passenger cannot be null");

        long id = passenger.getId();
        if (id < 0 || String.valueOf(id).length() > 15) {
            return new Response(400, "ID must be >= 0 and at most 15 digits");
        }
        if (passengers.containsKey(id)) {
            return new Response(409, "Passenger ID already exists");
        }

        if (passenger.getFirstname().isEmpty() ||
            passenger.getLastname().isEmpty() ||
            passenger.getCountry().isEmpty()) {
            return new Response(400, "Name and country fields must not be empty");
        }

        if (passenger.getBirthDate() == null || passenger.getBirthDate().isAfter(LocalDate.now())) {
            return new Response(400, "Invalid birth date");
        }

        int phoneCode = passenger.getCountryPhoneCode();
        long phone = passenger.getPhone();
        if (phoneCode < 0 || String.valueOf(phoneCode).length() > 3) {
            return new Response(400, "Country code must be >= 0 and at most 3 digits");
        }
        if (phone < 0 || String.valueOf(phone).length() > 11) {
            return new Response(400, "Phone must be >= 0 and at most 11 digits");
        }

        // Asignar lista de vuelos vacía si está null
        if (passenger.getFlights() == null) {
            passenger = clonePassenger(passenger, new ArrayList<>());
        }

        passengers.put(id, passenger);
        return new Response(201, "Passenger registered successfully");
    }

    public Response get(long id) {
        Passenger p = passengers.get(id);
        if (p == null) {
            return new Response(404, "Passenger not found");
        }
        return new Response(200, "Passenger found", clonePassenger(p));
    }

    public Response getAll() {
        List<Passenger> result = new ArrayList<>();
        for (Passenger p : passengers.values()) {
            result.add(clonePassenger(p));
        }
        return new Response(200, "All passengers retrieved", result);
    }

    public Response update(Passenger updated) {
        long id = updated.getId();
        if (!passengers.containsKey(id)) {
            return new Response(404, "Passenger does not exist");
        }
        Response validation = register(updated);
        if (validation.getCode() != 201) return validation;
        
        // preserve flights list from original
        Passenger original = passengers.get(id);
        updated = clonePassenger(updated, original.getFlights());
        passengers.put(id, updated);

        return new Response(200, "Passenger updated successfully");
    }

    private Passenger clonePassenger(Passenger p) {
        return clonePassenger(p, new ArrayList<>(p.getFlights()));
    }

    private Passenger clonePassenger(Passenger p, List<Models.Flight> flights) {
        Passenger copy = new Passenger(
            p.getId(),
            p.getFirstname(),
            p.getLastname(),
            p.getBirthDate(),
            p.getCountryPhoneCode(),
            p.getPhone(),
            p.getCountry()
        );
        for (Models.Flight f : flights) {
            copy.addFlight(f); // referencias válidas, controladas desde FlightStorage
        }
        return copy;
    }
} 
