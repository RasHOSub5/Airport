package Core.Repository;

<<<<<<< HEAD
import Core.Models.Passenger;
=======
import Models.Passenger;
>>>>>>> 618bf3ce7120e29c31f0bdc433589ac4c49292ed
import Utils.Response;
import Utils.ResponseCode;
import java.util.List;

public interface PassengerRepository {
    Response addPassenger(Passenger passenger);
    Response updatePassenger(long id, Passenger newPassenger);
    Passenger findById(long id);
    boolean exists(long id);
    List<Passenger> getAllPassengersSortedById(); 
    void loadFromJson(String filePath);
    void saveToJson(String filePath);
}