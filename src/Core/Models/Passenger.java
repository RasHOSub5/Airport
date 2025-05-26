package Core.Models;

<<<<<<< HEAD
import Core.Models.Flight;
=======
import Models.Flight;
>>>>>>> 618bf3ce7120e29c31f0bdc433589ac4c49292ed
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Passenger implements Cloneable {
    private long id;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private int countryPhoneCode;
    private long phone;
    private String country;
    private List<Flight> flights;

    public Passenger(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.countryPhoneCode = countryPhoneCode;
        this.phone = phone;
        this.country = country;
        this.flights = new ArrayList<>();
    }
    
    // Constructor privado para el Builder
    private Passenger(Builder builder) {
        this.id = builder.id;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.birthDate = builder.birthDate;
        this.countryPhoneCode = builder.countryPhoneCode;
        this.phone = builder.phone;
        this.country = builder.country;
        this.flights = new ArrayList<>();
    }

    // --- Builder estático ---
    public static class Builder {
        private final long id;  // Campo obligatorio
        private String firstname;
        private String lastname;
        private LocalDate birthDate;
        private int countryPhoneCode;
        private long phone;
        private String country;

        public Builder(long id) {
            this.id = id;
        }

        public Builder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder setCountryPhoneCode(int countryPhoneCode) {
            this.countryPhoneCode = countryPhoneCode;
            return this;
        }

        public Builder setPhone(long phone) {
            this.phone = phone;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Passenger build() {
            return new Passenger(this);
        }
    }

    // --- Setters (para compatibilidad con la vista) ---
    public void setId(long id) { this.id = id; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setCountryPhoneCode(int countryPhoneCode) { this.countryPhoneCode = countryPhoneCode; }
    public void setPhone(long phone) { this.phone = phone; }
    public void setCountry(String country) { this.country = country; }

    // --- Getters ---
    public long getId() { return id; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public LocalDate getBirthDate() { return birthDate; }
    public int getCountryPhoneCode() { return countryPhoneCode; }
    public long getPhone() { return phone; }
    public String getCountry() { return country; }
    public List<Flight> getFlights() { return new ArrayList<>(flights); } // Copia defensiva

    // --- Métodos de negocio ---
    public void addFlight(Flight flight) {
        flights.add(flight);
    }
    
    public String getFullname() {
        return firstname + " " + lastname;
    }
    
    public String generateFullPhone() {
        return "+" + countryPhoneCode + " " + phone;
    }
    
    public int calculateAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    public int getNumFlights() {
        return flights.size();
    }

    @Override
    public Passenger clone() {
        try {
            Passenger cloned = (Passenger) super.clone();
            cloned.flights = new ArrayList<>(this.flights); // Copia profunda
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar Passenger", e);
        }
    }
}