package Controllers;

import Controllers.Utils.Response;
import Models.*;
import java.time.LocalDateTime;
import java.util.*;

public class FlightController {

    private final HashMap<String, Flight> flights;
    private final PlaneController planeController;
    private final LocationController locationController;

    public FlightController(PlaneController planeController, LocationController locationController) {
        this.flights = new HashMap<>();
        this.planeController = planeController;
        this.locationController = locationController;
    }

    public Response createFlight(String id, String planeId, String departureId, String arrivalId,
                                 LocalDateTime departureDate, int hArrival, int mArrival) {
        if (flights.containsKey(id))
            return new Response(400, "ID de vuelo ya existe");
        if (!id.matches("[A-Z]{3}[0-9]{3}"))
            return new Response(400, "Formato de ID inválido (esperado XXXYYY)");

        Response planeResp = planeController.getPlane(planeId);
        if (planeResp.getCode() != 200)
            return new Response(404, "Avión no encontrado");

        Response depResp = locationController.getLocation(departureId);
        Response arrResp = locationController.getLocation(arrivalId);
        if (depResp.getCode() != 200 || arrResp.getCode() != 200)
            return new Response(404, "Localización no encontrada");

        if (hArrival == 0 && mArrival == 0)
            return new Response(400, "Duración del vuelo inválida");

        Flight flight = new Flight(id,
                (Plane) planeResp.getData(),
                (Location) depResp.getData(),
                (Location) arrResp.getData(),
                departureDate, hArrival, mArrival);

        flights.put(id, flight);
        return new Response(201, "Vuelo creado", cloneFlight(flight));
    }

    public Response addPassengerToFlight(String flightId, Passenger passenger) {
        Flight flight = flights.get(flightId);
        if (flight == null)
            return new Response(404, "Vuelo no encontrado");

        flight.addPassenger(passenger);
        return new Response(200, "Pasajero añadido al vuelo");
    }

    public Response delayFlight(String flightId, int hours, int minutes) {
        if (hours == 0 && minutes == 0)
            return new Response(400, "Tiempo de retraso inválido");

        Flight flight = flights.get(flightId);
        if (flight == null)
            return new Response(404, "Vuelo no encontrado");

        flight.delay(hours, minutes);
        return new Response(200, "Vuelo retrasado correctamente");
    }

    public Response getAllFlights() {
        List<Flight> result = new ArrayList<>(flights.values());
        result.sort(Comparator.comparing(Flight::getDepartureDate));

        List<Flight> clones = new ArrayList<>();
        for (Flight f : result)
            clones.add(cloneFlight(f));

        return new Response(200, "Lista de vuelos obtenida", clones);
    }

    public Response getFlight(String id) {
        Flight flight = flights.get(id);
        if (flight == null)
            return new Response(404, "Vuelo no encontrado");
        return new Response(200, "Vuelo encontrado", cloneFlight(flight));
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
                    f.getMinutesDurationArrival());
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
                    f.getMinutesDurationScale());   
        }
    }
}
