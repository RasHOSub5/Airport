package Core.Controllers;

<<<<<<< HEAD
import Core.Models.Flight;
import Core.Models.Location;
import Core.Models.Passenger;
import Core.Models.Plane;
import Core.Repository.FlightRepository;
import Core.Repository.LocationRepository;
import Core.Repository.PlaneRepository;
=======
import Models.Flight;
import Models.Location;
import Models.Passenger;
import Models.Plane;
import Repository.FlightRepository;
import Repository.LocationRepository;
import Repository.PlaneRepository;
>>>>>>> 618bf3ce7120e29c31f0bdc433589ac4c49292ed
import Utils.Response;
import Utils.ResponseCode;
import Utils.IdValidator;
import java.time.LocalDateTime;

public class FlightController {

    private final FlightRepository repository;
    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;
    private final LocationRepository locationRepository;

    public FlightController(
            FlightRepository flightRepository,
            PlaneRepository planeRepository,
            LocationRepository locationRepository
    ) {
        this.flightRepository = flightRepository;
        this.planeRepository = planeRepository;
        this.locationRepository = locationRepository;
        this.repository = null;
    }

    public FlightController(FlightRepository repository) {
        this.repository = repository;
        this.flightRepository = null;
        this.planeRepository = null;
        this.locationRepository = null;
    }

    public Response createFlight(String id, Plane plane, Location departure,
            Location arrival, LocalDateTime departureDate,
            int hoursArrival, int minutesArrival) {
        try {
            // Validaciones
            if (!IdValidator.isFlightIdValid(id)) {
                return new Response(ResponseCode.ERROR, "ID inválido. Formato: XXXYYY");
            }
            if (repository.exists(id)) {
                return new Response(ResponseCode.ERROR, "El vuelo ya existe");
            }
            if (plane == null || departure == null || arrival == null) {
                return new Response(ResponseCode.ERROR, "Datos incompletos");
            }

            Flight flight = new Flight.Builder(id, plane, departure, arrival,
                    departureDate, hoursArrival, minutesArrival)
                    .build();

            repository.addFlight(flight);
            return new Response(ResponseCode.SUCCESS, "Vuelo creado", flight.clone());

        } catch (IllegalArgumentException e) {
            return new Response(ResponseCode.ERROR, e.getMessage());
        }
    }

    public Response delayFlight(String flightId, int hours, int minutes) {
        Flight flight = repository.findById(flightId);
        if (flight == null) {
            return new Response(ResponseCode.ERROR, "Vuelo no encontrado");
        }
        if (hours < 0 || minutes < 0) {
            return new Response(ResponseCode.ERROR, "El retraso debe ser positivo");
        }

        flight.setDepartureDate(flight.getDepartureDate().plusHours(hours).plusMinutes(minutes));
        repository.updateFlight(flight);
        return new Response(ResponseCode.SUCCESS, "Vuelo retrasado", flight.clone());
    }

    public Response addPassengerToFlight(String flightId, Passenger passenger) {
        Flight flight = repository.findById(flightId);
        if (flight == null) {
            return new Response(ResponseCode.ERROR, "Vuelo no encontrado");
        }
        if (passenger == null) {
            return new Response(ResponseCode.ERROR, "Pasajero inválido");
        }

        flight.addPassenger(passenger);
        repository.updateFlight(flight);
        return new Response(ResponseCode.SUCCESS, "Pasajero añadido", flight.clone());
    }
}
