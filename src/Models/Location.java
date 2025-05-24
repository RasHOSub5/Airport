package Models;

/**
 *
 * @author edangulo
 */
public class Location {
    private final String airportId;
    private final String airportName;
    private final String airportCity;
    private final String airportCountry;
    private final double airportLatitude;
    private final double airportLongitude;

    public Location(String airportId, String airportName, String airportCity, String airportCountry,
                    double airportLatitude, double airportLongitude) {
        this.airportId = airportId;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
        this.airportLatitude = airportLatitude;
        this.airportLongitude = airportLongitude;
    }

    public String getAirportId() {
        return airportId;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public String getAirportCountry() {
        return airportCountry;
    }

    public double getAirportLatitude() {
        return airportLatitude;
    }

    public double getAirportLongitude() {
        return airportLongitude;
    }

    public String getFullLocation() {
        return airportName + ", " + airportCity + ", " + airportCountry;
    }
}