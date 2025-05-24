/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.Utils.Response;
import Models.Location;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class LocationController {

    private final HashMap<String, Location> locations;

    public LocationController() {
        this.locations = new HashMap<>();
    }

    // Registrar una nueva localización (aeropuerto)
    public Response createLocation(String airportId, String airportName, String airportCity, String airportCountry, double airportLatitude, double airportLongitude) {
        // Validaciones básicas de campos no vacíos
        if (airportId == null || airportId.isEmpty() ||
            airportName == null || airportName.isEmpty() ||
            airportCity == null || airportCity.isEmpty() ||
            airportCountry == null || airportCountry.isEmpty()) {
            return new Response(400, "Ningún campo debe estar vacío");
        }
        // Validar formato del ID: 3 letras mayúsculas
        if (!airportId.matches("[A-Z]{3}")) {
            return new Response(400, "El ID debe tener 3 letras mayúsculas");
        }
        // Validar latitud [-90, 90]
        if (airportLatitude < -90 || airportLatitude > 90) {
            return new Response(400, "Latitud fuera del rango válido [-90, 90]");
        }
        // Validar longitud [-180, 180]
        if (airportLongitude < -180 || airportLongitude > 180) {
            return new Response(400, "Longitud fuera del rango válido [-180, 180]");
        }
        // Redondear latitud y longitud a 4 decimales
        airportLatitude = Math.round(airportLatitude * 10000.0) / 10000.0;
        airportLongitude = Math.round(airportLongitude * 10000.0) / 10000.0;

        // Verificar unicidad del ID
        if (locations.containsKey(airportId)) {
            return new Response(400, "ID de localización ya existe");
        }

        Location location = new Location(airportId, airportName, airportCity, airportCountry, airportLatitude, airportLongitude);
        locations.put(airportId, location);

        return new Response(200, "Localización creada", cloneLocation(location));
    }

    // Obtener lista ordenada de localizaciones por airportId
    public Response getLocation(String departureId) {
        ArrayList<Location> locList = new ArrayList<>(locations.values());
        Collections.sort(locList, Comparator.comparing(Location::getAirportId));
        ArrayList<Location> copies = new ArrayList<>();
        for (Location loc : locList) {
            copies.add(cloneLocation(loc));
        }
        return new Response(200, "Lista de localizaciones obtenida", copies);
    }

    // Buscar localización por ID (devuelve copia)
    public Response findLocation(String airportId) {
        Location loc = locations.get(airportId);
        if (loc == null) {
            return new Response(404, "Localización no encontrada");
        }
        return new Response(200, "Localización encontrada", cloneLocation(loc));
    }

    // Método para clonar Location (patrón Prototype)
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
