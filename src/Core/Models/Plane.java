package Core.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plane implements Cloneable {
    private final String id;           // Formato XXYYYYY
    private String brand;
    private String model;
    private final int maxCapacity;
    private String airline;
    private List<Flight> flights;      // Lista mutable solo para gestión interna

    
    public Plane(String id, String brand, String model, int maxCapacity, String airline) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.maxCapacity = maxCapacity;
        this.airline = airline;
        this.flights = new ArrayList<>();
    }
    
    // Constructor privado para el Builder
    private Plane(Builder builder) {
        this.id = builder.id;
        this.brand = builder.brand;
        this.model = builder.model;
        this.maxCapacity = builder.maxCapacity;
        this.airline = builder.airline;
        this.flights = new ArrayList<>();
    }

    // --- Builder estático ---
    public static class Builder {
        private final String id;       // Campo obligatorio
        private String brand;
        private String model;
        private int maxCapacity;
        private String airline;

        public Builder(String id) {
            this.id = id;
        }

        public Builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setMaxCapacity(int maxCapacity) {
            this.maxCapacity = maxCapacity;
            return this;
        }

        public Builder setAirline(String airline) {
            this.airline = airline;
            return this;
        }

        public Plane build() {
            return new Plane(this);
        }
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getMaxCapacity() { return maxCapacity; }
    public String getAirline() { return airline; }
    public List<Flight> getFlights() { return Collections.unmodifiableList(flights); } // Lista inmutable

    // --- Setters (solo para campos modificables) ---
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setAirline(String airline) { this.airline = airline; }

    // --- Métodos de negocio ---
    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public int getNumFlights() {
        return flights.size();
    }

    @Override
    public Plane clone() {
        try {
            Plane cloned = (Plane) super.clone();
            cloned.flights = new ArrayList<>(this.flights); // Copia profunda
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar Plane", e);
        }
    }
}