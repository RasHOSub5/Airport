/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Storage;

/**
 *
 * @author Rashid
 */
import Models.Plane;
import Controllers.Utils.Response;
import Controllers.Utils.StatusCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PlaneStorage {
    private final HashMap<String, Plane> planes;

    public PlaneStorage() {
        this.planes = new HashMap<>();
    }

    public Response registerPlane(String id, String brand, String model, int maxCapacity, String airline) {
        if (id == null || !id.matches("[A-Z]{2}\\d{5}")) {
            return new Response(StatusCode.BAD_REQUEST, "El ID del avión debe seguir el formato XXYYYYY.");
        }
        if (planes.containsKey(id)) {
            return new Response(StatusCode.BAD_REQUEST, "Ya existe un avión con ese ID.");
        }
        if (brand == null || brand.isBlank() || model == null || model.isBlank() || airline == null || airline.isBlank()) {
            return new Response(StatusCode.BAD_REQUEST, "Todos los campos del avión deben ser no vacíos.");
        }
        if (maxCapacity <= 0) {
            return new Response(StatusCode.BAD_REQUEST, "La capacidad máxima debe ser mayor a 0.");
        }

        Plane newPlane = new Plane(id, brand, model, maxCapacity, airline);
        planes.put(id, newPlane);
        return new Response(StatusCode.CREATED, "Avión registrado exitosamente.");
    }

    public Response getPlane(String id) {
        Plane plane = planes.get(id);
        if (plane == null) {
            return new Response(StatusCode.NOT_FOUND, "Avión no encontrado.");
        }
        return new Response(StatusCode.OK, copyPlane(plane));
    }

    public Response getAllPlanes() {
        List<Plane> all = new ArrayList<>();
        for (Plane p : planes.values()) {
            all.add(copyPlane(p));
        }
        all.sort((a, b) -> a.getId().compareTo(b.getId())   );
        return new Response(StatusCode.OK, all);
    }

    private Plane copyPlane(Plane plane) {
        Plane copy = new Plane(
            plane.getId(),
            plane.getBrand(),
            plane.getModel(),
            plane.getMaxCapacity(),
            plane.getAirline()
        );
        // No se copian los vuelos porque no se requiere esa profundidad en el patrón Prototype aquí
        return copy;
    }
}
