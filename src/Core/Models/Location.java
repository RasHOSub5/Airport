package Core.Models;

public class Location implements Cloneable {
    private final String airportId;    // Formato AAA (3 letras mayúsculas)
    private String airportName;
    private String airportCity;
    private String airportCountry;
    private final double airportLatitude;  // Rango [-90, 90]
    private final double airportLongitude; // Rango [-180, 180]

    // Constructor original (con validaciones)
    public Location(String airportId, String airportName, String airportCity, 
                   String airportCountry, double airportLatitude, double airportLongitude) {
        // Validaciones
        if (!airportId.matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("ID inválido: debe ser 3 letras mayúsculas");
        }
        if (airportLatitude < -90 || airportLatitude > 90) {
            throw new IllegalArgumentException("Latitud inválida [-90, 90]");
        }
        if (airportLongitude < -180 || airportLongitude > 180) {
            throw new IllegalArgumentException("Longitud inválida [-180, 180]");
        }

        this.airportId = airportId;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
        this.airportLatitude = airportLatitude;
        this.airportLongitude = airportLongitude;
    }

    // --- Builder estático (para creación opcional) ---
    public static class Builder {
        private String airportId;
        private String airportName;
        private String airportCity;
        private String airportCountry;
        private double airportLatitude;
        private double airportLongitude;

        public Builder(String airportId) {
            this.airportId = airportId;
        }

        public Builder setAirportName(String airportName) {
            this.airportName = airportName;
            return this;
        }

        public Builder setAirportCity(String airportCity) {
            this.airportCity = airportCity;
            return this;
        }

        public Builder setAirportCountry(String airportCountry) {
            this.airportCountry = airportCountry;
            return this;
        }

        public Builder setAirportLatitude(double airportLatitude) {
            this.airportLatitude = airportLatitude;
            return this;
        }

        public Builder setAirportLongitude(double airportLongitude) {
            this.airportLongitude = airportLongitude;
            return this;
        }

        public Location build() {
            return new Location(
                airportId, 
                airportName, 
                airportCity, 
                airportCountry, 
                airportLatitude, 
                airportLongitude
            );
        }
    }

    public String getId() {
        return airportId;
    }

    public String getName() {
        return airportName;
    }

    public String getCity() {
        return airportCity;
    }

    public String getCountry() {
        return airportCountry;
    }

    public double getLatitude() {
        return airportLatitude;
    }

    public double getLongitude() {
        return airportLongitude;
    }
    
    // --- Getters ---
    public String getAirportId() { return airportId; }
    public String getAirportName() { return airportName; }
    public String getAirportCity() { return airportCity; }
    public String getAirportCountry() { return airportCountry; }
    public double getAirportLatitude() { return airportLatitude; }
    public double getAirportLongitude() { return airportLongitude; }

    // --- Setters (para campos modificables) ---
    public void setAirportName(String airportName) { this.airportName = airportName; }
    public void setAirportCity(String airportCity) { this.airportCity = airportCity; }
    public void setAirportCountry(String airportCountry) { this.airportCountry = airportCountry; }

    @Override
    public Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar Location", e);
        }
    }
}