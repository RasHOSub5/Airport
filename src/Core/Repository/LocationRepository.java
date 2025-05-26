package Core.Repository;

import Core.Models.Location;
import java.util.List;

public interface LocationRepository {
    void addLocation(Location location);
    void updateLocation(Location location);
    Location findById(String airportId);
    boolean exists(String airportId);
    List<Location> getAllLocationsSortedById(); // Ordenados por ID
}