package Core.Repository;

import Models.Passenger;
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

public class JsonPassengerRepository implements PassengerRepository {
    
    private List<Passenger> passengers = new ArrayList<>();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public JsonPassengerRepository() {
        loadFromJson("data/passengers.json"); // Ruta relativa desde resources/
    }

    @Override
    public Response addPassenger(Passenger passenger) {
        if (exists(passenger.getId())) {
            return new Response(ResponseCode.ERROR, "ID duplicado");
        }
        passengers.add(passenger);
        saveToJson("data/passengers.json");
        return new Response(ResponseCode.SUCCESS, "Pasajero añadido", CloneUtils.clone(passenger));
    }

    @Override
    public Response updatePassenger(long id, Passenger newPassenger) {
        Passenger existing = findById(id);
        if (existing == null) {
            return new Response(ResponseCode.ERROR, "Pasajero no encontrado");
        }
        
        passengers.remove(existing);
        passengers.add(newPassenger);
        saveToJson("data/passengers.json");
        return new Response(ResponseCode.SUCCESS, "Pasajero actualizado", CloneUtils.clone(newPassenger));
    }

    @Override
    public Passenger findById(long id) {
        return passengers.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean exists(long id) {
        return passengers.stream().anyMatch(p -> p.getId() == id);
    }

    @Override
    public List<Passenger> getAllPassengersSortedById() {
    return passengers.stream()
        .sorted((p1, p2) -> Long.compare(p1.getId(), p2.getId()))
        .toList();
    }

    @Override
    public void loadFromJson(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);
            
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Passenger passenger = new Passenger.Builder(obj.getLong("id"))
                    .setFirstname(obj.getString("firstname"))
                    .setLastname(obj.getString("lastname"))
                    .setBirthDate(JsonDataLoader.parseDate(obj.getString("birthDate")))
                    .setCountryPhoneCode(obj.getInt("countryPhoneCode"))
                    .setPhone(obj.getLong("phone"))
                    .setCountry(obj.getString("country"))
                    .build();
                passengers.add(passenger);
            }
        } catch (IOException e) {
            System.err.println("Error cargando pasajeros: " + e.getMessage());
        }
    }

    @Override
    public void saveToJson(String filePath) {
        JSONArray jsonArray = new JSONArray();
        for (Passenger p : passengers) {
            JSONObject obj = new JSONObject();
            obj.put("id", p.getId());
            obj.put("firstname", p.getFirstname());
            obj.put("lastname", p.getLastname());
            obj.put("birthDate", p.getBirthDate().format(DATE_FORMATTER));
            obj.put("countryPhoneCode", p.getCountryPhoneCode());
            obj.put("phone", p.getPhone());
            obj.put("country", p.getCountry());
            jsonArray.put(obj);
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toString(2)); // Formato bonito
        } catch (IOException e) {
            System.err.println("Error guardando pasajeros: " + e.getMessage());
        }
    }
}