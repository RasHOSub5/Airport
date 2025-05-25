package Core.Controllers;

import Models.Location;
import Repository.LocationRepository;
import Utils.Response;
import Utils.ResponseCode;
import java.util.List;

public class LocationController {

    private final LocationRepository repository;

    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    // Crear ubicación
    public Response createLocation(String airportId, String airportName,
            String airportCity, String airportCountry,
            double airportLatitude, double airportLongitude) {
        try {
            // Validar existencia previa
            if (repository.exists(airportId)) {
                return new Response(ResponseCode.ERROR, "El ID del aeropuerto ya existe");
            }

            // Crear ubicación (las validaciones se lanzan desde el constructor)
            Location location = new Location(
                    airportId,
                    airportName.trim(),
                    airportCity.trim(),
                    airportCountry.trim(),
                    airportLatitude,
                    airportLongitude
            );

            repository.addLocation(location);
            return new Response(ResponseCode.SUCCESS, "Aeropuerto registrado", location.clone());

        } catch (IllegalArgumentException e) {
            return new Response(ResponseCode.ERROR, e.getMessage());
        }
    }

    // Actualizar ubicación
    public Response updateLocation(String airportId, String newName,
            String newCity, String newCountry) {
        Location location = repository.findById(airportId);
        if (location == null) {
            return new Response(ResponseCode.ERROR, "Aeropuerto no encontrado");
        }

        // Actualizar campos modificables
        location.setAirportName(newName.trim());
        location.setAirportCity(newCity.trim());
        location.setAirportCountry(newCountry.trim());

        repository.updateLocation(location);
        return new Response(ResponseCode.SUCCESS, "Aeropuerto actualizado", location.clone());
    }

    // Obtener todas las ubicaciones
    // LocationController.java
    public Response<List<Location>> getAllLocations() {
        List<Location> locations = repository.getAllLocationsSortedById();
        return new Response<>(ResponseCode.SUCCESS, "Ubicaciones obtenidas", locations);
    }
}
