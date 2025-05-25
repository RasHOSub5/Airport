package Repository;

import Models.Plane;
import java.util.List;

public interface PlaneRepository {
    void addPlane(Plane plane);
    void updatePlane(Plane plane);
    Plane findById(String id);
    boolean exists(String id);
    List<Plane> getAllPlanes();
}