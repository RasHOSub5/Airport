package Core.Controllers;

import Core.Models.Plane;
import Core.Repository.PlaneRepository;
import Utils.Response;
import Utils.ResponseCode;
import Utils.IdValidator;
import java.util.List;

public class PlaneController {

    private final PlaneRepository repository;

    public PlaneController(PlaneRepository repository) {
        this.repository = repository;
    }

    public Response createPlane(String id, String brand, String model,
            int maxCapacity, String airline) {
        // Validaciones
        if (!IdValidator.isPlaneIdValid(id)) {
            return new Response(ResponseCode.ERROR, "ID inválido. Formato: XXYYYYY");
        }
        if (repository.exists(id)) {
            return new Response(ResponseCode.ERROR, "El avión ya existe");
        }

        // Creación con Builder
        Plane plane = new Plane.Builder(id)
                .setBrand(brand.trim())
                .setModel(model.trim())
                .setMaxCapacity(maxCapacity)
                .setAirline(airline.trim())
                .build();

        repository.addPlane(plane);
        return new Response(ResponseCode.SUCCESS, "Avión creado", plane.clone());
    }

    public Response updatePlane(String id, String newBrand, String newModel, String newAirline) {
        Plane plane = repository.findById(id);
        if (plane == null) {
            return new Response(ResponseCode.ERROR, "Avión no encontrado");
        }

        plane.setBrand(newBrand.trim());
        plane.setModel(newModel.trim());
        plane.setAirline(newAirline.trim());

        repository.updatePlane(plane);
        return new Response(ResponseCode.SUCCESS, "Avión actualizado", plane.clone());
    }

    public Response<List<Plane>> getAllPlanes() {
        List<Plane> planes = repository.getAllPlanes();
        return new Response<>(
                ResponseCode.SUCCESS,
                "Lista de aviones obtenida",
                planes.stream()
                        .map(Plane::clone)
                        .toList()
        );
    }
}
