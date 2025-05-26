package Repository;

import Models.Flight;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonFlightRepository implements FlightRepository {
    private List<Flight> flights = new ArrayList<>();
    private static final String FILE_PATH = "data/flights.json";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public JsonFlightRepository() {
        loadFromJson();
    }

    @Override
    public void addFlight(Flight flight) {
        if (!exists(flight.getId())) {
            flights.add(flight);
            saveToJson();
        }
    }

    @Override
    public void updateFlight(Flight flight) {
        Flight existing = findById(flight.getId());
        if (existing != null) {
            existing.setDepartureDate(flight.getDepartureDate());
            // Actualizar otros campos si es necesario...
            saveToJson();
        }
    }

    @Override
    public Flight findById(String id) {
        return flights.stream()
            .filter(f -> f.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean exists(String id) {
        return flights.stream().anyMatch(f -> f.getId().equals(id));
    }

    @Override
    public List<Flight> getAllFlightsSortedByDate() {
        return flights.stream()
            .sorted((f1, f2) -> f1.getDepartureDate().compareTo(f2.getDepartureDate()))
            .toList();
    }

    private void loadFromJson() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                // Implementar lógica para reconstruir Flight desde JSON...
            }
        } catch (IOException e) {
            System.err.println("Error cargando vuelos: " + e.getMessage());
        }
    }

    private void saveToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Flight flight : flights) {
            JSONObject obj = new JSONObject();
            obj.put("id", flight.getId());
            obj.put("departureDate", flight.getDepartureDate().format(DATE_FORMATTER));
            // Agregar más campos...
            jsonArray.put(obj);
        }

        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonArray.toString(2));
        } catch (IOException e) {
            System.err.println("Error guardando vuelos: " + e.getMessage());
        }
    }
}