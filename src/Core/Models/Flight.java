package Core.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Flight implements Cloneable {
    private final String id; // Formato XXXYYY (3 letras + 3 dígitos)
    private final Plane plane;
    private final Location departureLocation;
    private final Location arrivalLocation;
    private LocalDateTime departureDate;
    private final int hoursDurationArrival;
    private final int minutesDurationArrival;
    
    public Flight(String id, Plane plane, Location departureLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival) {
        this.id = id;
        this.passengers = new ArrayList<>();
        this.plane = plane;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.hoursDurationArrival = hoursDurationArrival;
        this.minutesDurationArrival = minutesDurationArrival;
        
        this.plane.addFlight(this);
    }

    public Flight(String id, Plane plane, Location departureLocation, Location scaleLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival, int hoursDurationScale, int minutesDurationScale) {
        this.id = id;
        this.passengers = new ArrayList<>();
        this.plane = plane;
        this.departureLocation = departureLocation;
        this.scaleLocation = scaleLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.hoursDurationArrival = hoursDurationArrival;
        this.minutesDurationArrival = minutesDurationArrival;
        this.hoursDurationScale = hoursDurationScale;
        this.minutesDurationScale = minutesDurationScale;
        this.plane.addFlight(this);
    }
    
    // Campos opcionales para escalas
    private Location scaleLocation;
    private int hoursDurationScale;
    private int minutesDurationScale;
    
    private List<Passenger> passengers;

    // Constructor privado para el Builder
    private Flight(Builder builder) {
        this.id = builder.id;
        this.plane = builder.plane;
        this.departureLocation = builder.departureLocation;
        this.arrivalLocation = builder.arrivalLocation;
        this.departureDate = builder.departureDate;
        this.hoursDurationArrival = builder.hoursDurationArrival;
        this.minutesDurationArrival = builder.minutesDurationArrival;
        this.scaleLocation = builder.scaleLocation;
        this.hoursDurationScale = builder.hoursDurationScale;
        this.minutesDurationScale = builder.minutesDurationScale;
        this.passengers = new ArrayList<>();
    }

    // --- Builder estático ---
    public static class Builder {
        // Campos obligatorios
        private final String id;
        private final Plane plane;
        private final Location departureLocation;
        private final Location arrivalLocation;
        private final LocalDateTime departureDate;
        private final int hoursDurationArrival;
        private final int minutesDurationArrival;
        
        // Campos opcionales
        private Location scaleLocation;
        private int hoursDurationScale;
        private int minutesDurationScale;

        public Builder(String id, Plane plane, Location departureLocation, 
                      Location arrivalLocation, LocalDateTime departureDate, 
                      int hoursDurationArrival, int minutesDurationArrival) {
            this.id = id;
            this.plane = plane;
            this.departureLocation = departureLocation;
            this.arrivalLocation = arrivalLocation;
            this.departureDate = departureDate;
            this.hoursDurationArrival = hoursDurationArrival;
            this.minutesDurationArrival = minutesDurationArrival;
        }

        public Builder setScaleLocation(Location scaleLocation) {
            this.scaleLocation = scaleLocation;
            return this;
        }

        public Builder setScaleDuration(int hours, int minutes) {
            this.hoursDurationScale = hours;
            this.minutesDurationScale = minutes;
            return this;
        }

        public Flight build() {
            return new Flight(this);
        }
    }

    // --- Getters (sin setters para campos inmutables) ---
    public String getId() { return id; }
    public Plane getPlane() { return plane; }
    public Location getDepartureLocation() { return departureLocation; }
    public Location getArrivalLocation() { return arrivalLocation; }
    public LocalDateTime getDepartureDate() { return departureDate; }
    public int getHoursDurationArrival() { return hoursDurationArrival; }
    public int getMinutesDurationArrival() { return minutesDurationArrival; }
    public Location getScaleLocation() { return scaleLocation; }
    public int getHoursDurationScale() { return hoursDurationScale; }
    public int getMinutesDurationScale() { return minutesDurationScale; }
    public List<Passenger> getPassengers() { return Collections.unmodifiableList(passengers); }

    // --- Setters para campos modificables ---
    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    // --- Métodos de negocio ---
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public LocalDateTime calculateArrivalDate() {
        LocalDateTime arrival = departureDate
            .plusHours(hoursDurationArrival)
            .plusMinutes(minutesDurationArrival);
        
        if (scaleLocation != null) {
            arrival = arrival
                .plusHours(hoursDurationScale)
                .plusMinutes(minutesDurationScale);
        }
        return arrival;
    }

    public void delay(int hours, int minutes) {
        this.departureDate = this.departureDate.plusHours(hours).plusMinutes(minutes);
    }
    
    public int getNumPassengers() {
        return passengers.size();
    }
    
    @Override
    public Flight clone() {
        try {
            Flight cloned = (Flight) super.clone();
            cloned.passengers = new ArrayList<>(this.passengers); // Copia profunda
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar Flight", e);
        }
    }
}