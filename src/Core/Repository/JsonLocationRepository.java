package Core.Repository;

import Core.Models.Location;
import Utils.Response;
import Utils.ResponseCode;
import Utils.JsonDataLoader;
import Utils.CloneUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JsonLocationRepository implements LocationRepository {
    private List<Location> locations = new ArrayList<>();
    private static final String FILE_PATH = "data/locations.json";

    public JsonLocationRepository() {
        loadFromJson();
    }

    @Override
    public void addLocation(Location location) {
        if (!exists(location.getAirportId())) {
            locations.add(location);
            saveToJson();
        }
    }

    @Override
    public void updateLocation(Location location) {
        Location existing = findById(location.getAirportId());
        if (existing != null) {
            existing.setAirportName(location.getAirportName());
            existing.setAirportCity(location.getAirportCity());
            existing.setAirportCountry(location.getAirportCountry());
            saveToJson();
        }
    }

    @Override
    public Location findById(String airportId) {
        return locations.stream()
            .filter(l -> l.getAirportId().equals(airportId))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean exists(String airportId) {
        return locations.stream().anyMatch(l -> l.getAirportId().equals(airportId));
    }

    
    public List<Location> getAllLocationsSortedById() {
        return locations.stream()
            .sorted((l1, l2) -> l1.getAirportId().compareTo(l2.getAirportId()))
            .collect(Collectors.toList());
    }

    // Cargar desde JSON
    private void loadFromJson() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                try {
                    Location location = new Location(
                        obj.getString("airportId"),
                        obj.getString("airportName"),
                        obj.getString("airportCity"),
                        obj.getString("airportCountry"),
                        obj.getDouble("airportLatitude"),
                        obj.getDouble("airportLongitude")
                    );
                    locations.add(location);
                } catch (IllegalArgumentException e) {
                    System.err.println("Datos inválidos en JSON: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando ubicaciones: " + e.getMessage());
        }
    }

    // Guardar en JSON
    private void saveToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Location loc : locations) {
            JSONObject obj = new JSONObject();
            obj.put("airportId", loc.getAirportId());
            obj.put("airportName", loc.getAirportName());
            obj.put("airportCity", loc.getAirportCity());
            obj.put("airportCountry", loc.getAirportCountry());
            obj.put("airportLatitude", loc.getAirportLatitude());
            obj.put("airportLongitude", loc.getAirportLongitude());
            jsonArray.put(obj);
        }

        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonArray.toString(2)); // Formato legible
        } catch (IOException e) {
            System.err.println("Error guardando ubicaciones: " + e.getMessage());
        }
    }
}