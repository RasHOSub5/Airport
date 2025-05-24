/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.Utils.Response;
import Models.Plane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class PlaneController {

    private final HashMap<String, Plane> planes;

    public PlaneController() {
        planes = new HashMap<>();
    }

    public Response registerPlane(String id, String brand, String model, int maxCapacity, String airline) {
        // Validar campos no vacíos
        if (id == null || brand == null || model == null || airline == null ||
            id.isBlank() || brand.isBlank() || model.isBlank() || airline.isBlank()) {
            return new Response(400, "Ningún campo debe estar vacío");
        }

        // Validar formato del ID (XXYYYYY)
        if (!id.matches("[A-Z]{2}\\d{5}")) {
            return new Response(400, "El ID del avión debe seguir el formato XXYYYYY");
        }

        // Validar unicidad
        if (planes.containsKey(id)) {
            return new Response(409, "El ID del avión ya existe");
        }

        Plane plane = new Plane(id, brand, model, maxCapacity, airline);
        planes.put(id, plane);

        return new Response(201, "Avión registrado correctamente");
    }

    public Response getPlane(String id) {
        Plane plane = planes.get(id);
        if (plane == null) {
            return new Response(404, "Avión no encontrado");
        }

        return new Response(200, "Avión encontrado", clonePlane(plane));
    }

    public Response getAllPlanes() {
        ArrayList<Plane> all = new ArrayList<>(planes.values());
        all.sort(Comparator.comparing(Plane::getId));

        // Copias (Prototype)
        ArrayList<Plane> copies = new ArrayList<>();
        for (Plane p : all) {
            copies.add(clonePlane(p));
        }

        return new Response(200, "Lista de aviones obtenida", copies);
    }

    private Plane clonePlane(Plane plane) {
        return new Plane(
            plane.getId(),
            plane.getBrand(),
            plane.getModel(),
            plane.getMaxCapacity(),
            plane.getAirline()
        );
    }
}

