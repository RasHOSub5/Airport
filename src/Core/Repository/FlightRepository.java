package Core.Repository;

<<<<<<< HEAD
import Core.Models.Flight;
=======
import Models.Flight;
>>>>>>> 618bf3ce7120e29c31f0bdc433589ac4c49292ed
import java.util.List;

public interface FlightRepository {
    void addFlight(Flight flight);
    void updateFlight(Flight flight);
    Flight findById(String id);
    boolean exists(String id);
    List<Flight> getAllFlightsSortedByDate();
}