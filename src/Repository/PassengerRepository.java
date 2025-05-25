package Repository;

import Models.Passenger;
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