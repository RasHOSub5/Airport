package Controllers;  // Asegúrate de que esté en el paquete 'controllers'

import Models.Passenger;
import Repository.PassengerRepository;
import Utils.Response;
import Utils.ResponseCode;
import Utils.IdValidator;
import Utils.DateValidator;
import Utils.PhoneValidator;
import java.time.LocalDate;
import java.util.List;

public class PassengerController {
    private final PassengerRepository repository;

    public PassengerController(PassengerRepository repository) {
        this.repository = repository;
    }

    public Response createPassenger(
        long id, 
        String firstname, 
        String lastname, 
        LocalDate birthDate,
        int countryPhoneCode, 
        long phone, 
        String country
    ) {
        // Validación ID (único, positivo, máximo 15 dígitos)
        if (!IdValidator.isPassengerIdValid(id)) {
            return new Response(ResponseCode.ERROR, "ID inválido: debe ser positivo y tener máximo 15 dígitos");
        }
        if (repository.exists(id)) {
            return new Response(ResponseCode.ERROR, "ID ya registrado");
        }

        // Validación nombres y apellidos (no vacíos)
        if (firstname == null || firstname.trim().isEmpty()) {
            return new Response(ResponseCode.ERROR, "El nombre no puede estar vacío");
        }
        if (lastname == null || lastname.trim().isEmpty()) {
            return new Response(ResponseCode.ERROR, "El apellido no puede estar vacío");
        }

        // Validación fecha de nacimiento (válida y no futura)
        if (!DateValidator.isBirthDateValid(birthDate)) {
            return new Response(ResponseCode.ERROR, "Fecha de nacimiento inválida");
        }

        // Validación código telefónico (1-3 dígitos)
        if (!PhoneValidator.isCountryCodeValid(countryPhoneCode)) {
            return new Response(ResponseCode.ERROR, "Código telefónico inválido (debe ser entre 1 y 3 dígitos)");
        }

        // Validación teléfono (1-11 dígitos)
        if (!PhoneValidator.isPhoneNumberValid(phone)) {
            return new Response(ResponseCode.ERROR, "Teléfono inválido (máximo 11 dígitos)");
        }

        // Validación país (no vacío)
        if (country == null || country.trim().isEmpty()) {
            return new Response(ResponseCode.ERROR, "El país no puede estar vacío");
        }

        
        
        // Crear y guardar el pasajero
        Passenger passenger = new Passenger.Builder(id)
            .setFirstname(firstname.trim())
            .setLastname(lastname.trim())
            .setBirthDate(birthDate)
            .setCountryPhoneCode(countryPhoneCode)
            .setPhone(phone)
            .setCountry(country.trim())
            .build();

        repository.addPassenger(passenger);

        return new Response(ResponseCode.SUCCESS, "Pasajero registrado exitosamente", passenger.clone());
    }
    
    public Response<List<Passenger>> getAllPassengers() {
    List<Passenger> passengers = repository.getAllPassengersSortedById();
    return new Response<>(
        ResponseCode.SUCCESS, 
        "Lista de pasajeros obtenida", 
        passengers.stream()
            .map(Passenger::clone)
            .toList()
    );
}
}