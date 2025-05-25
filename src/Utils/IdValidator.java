package Utils;

public class IdValidator {

    // Valida ID de pasajero (1-15 dígitos)
    public static boolean isPassengerIdValid(long id) {
        return id >= 0 && String.valueOf(id).length() <= 15;
    }

    // Valida ID de avión (XXYYYYY)
    public static boolean isPlaneIdValid(String id) {
        return id.matches("[A-Z]{2}\\d{5}");
    }

    // Valida ID de aeropuerto (3 letras mayúsculas)
    public static boolean isAirportIdValid(String id) {
        return id.matches("[A-Z]{3}");
    }

    // Valida ID de vuelo (XXXYYY)
    public static boolean isFlightIdValid(String id) {
        return id.matches("[A-Z]{3}\\d{3}");
    }
}