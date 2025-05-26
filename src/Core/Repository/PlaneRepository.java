package Core.Repository;

<<<<<<< HEAD
import Core.Models.Plane;
=======
import Models.Plane;
>>>>>>> 618bf3ce7120e29c31f0bdc433589ac4c49292ed
import java.util.List;

public interface PlaneRepository {
    void addPlane(Plane plane);
    void updatePlane(Plane plane);
    Plane findById(String id);
    boolean exists(String id);
    List<Plane> getAllPlanes();
}