package Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author edangulo
 */
public class Flight {

    private final String id;
    private final Plane plane;
    private final Location departureLocation;
    private final Location scaleLocation;
    private final Location arrivalLocation;
    private LocalDateTime departureDate;
    private final int hoursDurationArrival;
    private final int minutesDurationArrival;
    private final int hoursDurationScale;
    private final int minutesDurationScale;
    private final List<Passenger> passengers;
    private final FlightTimeCalculator timeCalculator;

    public Flight(String id, Plane plane, Location departureLocation, Location arrivalLocation,
            LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival) {
        this(id, plane, departureLocation, null, arrivalLocation, departureDate,
                hoursDurationArrival, minutesDurationArrival, 0, 0);
    }

    public Flight(String id, Plane plane, Location departureLocation, Location scaleLocation, Location arrivalLocation,
            LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival,
            int hoursDurationScale, int minutesDurationScale) {
        this.id = id;
        this.plane = plane;
        this.departureLocation = departureLocation;
        this.scaleLocation = scaleLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.hoursDurationArrival = hoursDurationArrival;
        this.minutesDurationArrival = minutesDurationArrival;
        this.hoursDurationScale = hoursDurationScale;
        this.minutesDurationScale = minutesDurationScale;
        this.passengers = new ArrayList<>();
        this.timeCalculator = new FlightTimeCalculator();
        plane.addFlight(this);
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    public String getId() {
        return id;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public Location getScaleLocation() {
        return scaleLocation;
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public List<Passenger> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    public int getHoursDurationArrival() {
        return hoursDurationArrival;
    }

    public int getMinutesDurationArrival() {
        return minutesDurationArrival;
    }

    public int getHoursDurationScale() {
        return hoursDurationScale;
    }

    public int getMinutesDurationScale() {
        return minutesDurationScale;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime calculateArrivalDate() {
        return timeCalculator.calculateArrivalDate(departureDate, hoursDurationScale, minutesDurationScale,
                hoursDurationArrival, minutesDurationArrival);
    }

    public void delay(int hours, int minutes) {
        this.departureDate = this.departureDate.plusHours(hours).plusMinutes(minutes);
    }

    public int getNumPassengers() {
        return passengers.size();
    }

}

class FlightTimeCalculator {

    public LocalDateTime calculateArrivalDate(LocalDateTime departureDate, int hoursScale, int minutesScale,
            int hoursArrival, int minutesArrival) {
        return departureDate.plusHours(hoursScale)
                .plusMinutes(minutesScale)
                .plusHours(hoursArrival)
                .plusMinutes(minutesArrival);
    }
}