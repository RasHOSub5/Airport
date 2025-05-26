package Core.Repository;

<<<<<<< HEAD
import Core.Models.Flight;
import Core.Models.Plane;
=======
import Models.Flight;
import Models.Plane;
>>>>>>> 618bf3ce7120e29c31f0bdc433589ac4c49292ed
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JsonPlaneRepository implements PlaneRepository {

    private List<Plane> planes = new ArrayList<>();
    private static final String FILE_PATH = "data/planes.json";

    public JsonPlaneRepository() {
        loadFromJson();
    }

    @Override
    public void addPlane(Plane plane) {
        if (!exists(plane.getId())) {
            planes.add(plane);
            saveToJson();
        }
    }

    @Override
    public void updatePlane(Plane plane) {
        Plane existing = findById(plane.getId());
        if (existing != null) {
            existing.setBrand(plane.getBrand());
            existing.setModel(plane.getModel());
            existing.setAirline(plane.getAirline());
            saveToJson();
        }
    }

    @Override
    public Plane findById(String id) {
        return planes.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean exists(String id) {
        return planes.stream().anyMatch(p -> p.getId().equals(id));
    }

    @Override
    public List<Plane> getAllPlanes() {
        return planes.stream()
                .sorted((p1, p2) -> p1.getId().compareTo(p2.getId()))
                .toList();
    }

    // --- Métodos auxiliares para JSON ---
    private void loadFromJson() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Plane plane = new Plane.Builder(obj.getString("id"))
                        .setBrand(obj.getString("brand"))
                        .setModel(obj.getString("model"))
                        .setMaxCapacity(obj.getInt("maxCapacity"))
                        .setAirline(obj.getString("airline"))
                        .build();
                planes.add(plane);
            }
        } catch (IOException e) {
            System.err.println("Error cargando aviones desde JSON: " + e.getMessage());
        }
    }

    private void saveToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Plane plane : planes) {
            JSONObject obj = new JSONObject();
            obj.put("id", plane.getId());
            obj.put("brand", plane.getBrand());
            obj.put("model", plane.getModel());
            obj.put("maxCapacity", plane.getMaxCapacity());
            obj.put("airline", plane.getAirline());
            jsonArray.put(obj);
        }

        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonArray.toString(2)); // Formateado bonito
        } catch (IOException e) {
            System.err.println("Error guardando aviones en JSON: " + e.getMessage());
        }
    }

    // --- Métodos adicionales para gestión de vuelos ---
    public void addFlightToPlane(String planeId, Flight flight) {
        Plane plane = findById(planeId);
        if (plane != null) {
            plane.addFlight(flight);
            saveToJson();
        }
    }

    public List<Flight> getFlightsByPlane(String planeId) {
        Plane plane = findById(planeId);
        return plane != null ? plane.getFlights() : Collections.emptyList();
    }
}
