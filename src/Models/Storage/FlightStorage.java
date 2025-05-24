/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Storage;

import Models.Flight;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Rashid
 */
public class FlightStorage {

    private final List<Flight> flights;

    public FlightStorage() {
        this.flights = new ArrayList<>();
    }

    public boolean addFlight(Flight flight) {
        if (getFlightById(flight.getId()).isPresent()) {
            return false; // Ya existe
        }
        flights.add(flight);
        sortFlights();
        return true;
    }

    public boolean removeFlight(String id) {
        Optional<Flight> optionalFlight = getFlightById(id);
        if (optionalFlight.isPresent()) {
            flights.remove(optionalFlight.get());
            return true;
        }
        return false;
    }

    public Optional<Flight> getFlightById(String id) {
        return flights.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst();
    }

    public List<Flight> getAllFlights() {
        List<Flight> copy = new ArrayList<>();
        for (Flight f : flights) {
            copy.add(cloneFlight(f));
        }
        return copy;
    }

    private void sortFlights() {
        flights.sort(Comparator.comparing(Flight::getDepartureDate));
    }

    private Flight cloneFlight(Flight f) {
        if (f.getScaleLocation() == null) {
            return new Flight(
                    f.getId(),
                    f.getPlane(),
                    f.getDepartureLocation(),
                    f.getArrivalLocation(),
                    f.getDepartureDate(),
                    f.getHoursDurationArrival(),
                    f.getMinutesDurationArrival()
            );
        } else {
            return new Flight(
                    f.getId(),
                    f.getPlane(),
                    f.getDepartureLocation(),
                    f.getScaleLocation(),
                    f.getArrivalLocation(),
                    f.getDepartureDate(),
                    f.getHoursDurationArrival(),
                    f.getMinutesDurationArrival(),
                    f.getHoursDurationScale(),
                    f.getMinutesDurationScale()
            );
        }
    }
}
