<<<<<<< HEAD
package Core.Repository;

import Core.Models.Location;
=======
package Repository;

import Models.Location;
>>>>>>> 618bf3ce7120e29c31f0bdc433589ac4c49292ed
import java.util.List;

public interface LocationRepository {
    void addLocation(Location location);
    void updateLocation(Location location);
    Location findById(String airportId);
    boolean exists(String airportId);
    List<Location> getAllLocationsSortedById(); // Ordenados por ID
}