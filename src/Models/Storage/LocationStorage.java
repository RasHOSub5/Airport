/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Storage;

import Models.Location;
import Controllers.Utils.Response;
import java.util.*;

/**
 *
 * @author Rashid
 */
public class LocationStorage {

    private final Map<String, Location> locations;

    public LocationStorage() {
        this.locations = new TreeMap<>();
    }

    public Response register(Location location) {
        if (location == null) {
            return new Response(400, "Location cannot be null");
        }

        String id = location.getAirportId();
        if (!id.matches("[A-Z]{3}")) {
            return new Response(400, "Invalid airport ID format (must be 3 uppercase letters)");
        }

        if (locations.containsKey(id)) {
            return new Response(409, "Location ID already exists");
        }

        if (location.getAirportName().isEmpty() || location.getAirportCity().isEmpty() || location.getAirportCountry().isEmpty()) {
            return new Response(400, "Fields cannot be empty");
        }

        double lat = location.getAirportLatitude();
        double lon = location.getAirportLongitude();

        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            return new Response(400, "Latitude or longitude out of range");
        }

        if (!hasAtMost4Decimals(lat) || !hasAtMost4Decimals(lon)) {
            return new Response(400, "Latitude or longitude must have at most 4 decimal digits");
        }

        locations.put(id, location);
        return new Response(201, "Location registered successfully");
    }

    public Response get(String id) {
        Location location = locations.get(id);
        if (location == null) {
            return new Response(404, "Location not found");
        }

        return new Response(200, "Location found", cloneLocation(location));
    }

    public Response getAll() {
        List<Location> result = new ArrayList<>();
        for (Location l : locations.values()) {
            result.add(cloneLocation(l));
        }
        return new Response(200, "All locations retrieved", result);
    }

    private boolean hasAtMost4Decimals(double value) {
        String text = String.valueOf(value);
        int index = text.indexOf(".");
        return index == -1 || text.length() - index - 1 <= 4;
    }

    private Location cloneLocation(Location loc) {
        return new Location(
                loc.getAirportId(),
                loc.getAirportName(),
                loc.getAirportCity(),
                loc.getAirportCountry(),
                loc.getAirportLatitude(),
                loc.getAirportLongitude()
        );
    }
}
