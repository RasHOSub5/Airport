package Core.Repository;

import Models.Flight;
import java.util.List;

public interface FlightRepository {
    void addFlight(Flight flight);
    void updateFlight(Flight flight);
    Flight findById(String id);
    boolean exists(String id);
    List<Flight> getAllFlightsSortedByDate();
}