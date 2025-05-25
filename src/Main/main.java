/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author Rashid
 */
import Controllers.FlightController;
import Controllers.PassengerController;
import Views.FlightView;
import Views.PassengerView;
import Controllers.*;
import Repository.*;
import Views.*;

public class Main {
    public static void main(String[] args) {
        // Inicializar repositorios
        PassengerRepository passengerRepo = new JsonPassengerRepository();
        FlightRepository flightRepo = new JsonFlightRepository();
        PlaneRepository planeRepo = new JsonPlaneRepository();
        LocationRepository locationRepo = new JsonLocationRepository();

        // Inicializar controladores
        PassengerController passengerController = new PassengerController(passengerRepo);
        FlightController flightController = new FlightController(flightRepo, planeRepo, locationRepo);

        // Crear vistas
        PassengerView passengerView = new PassengerView(passengerController);
        FlightView flightView = new FlightView(flightController);

        // Mostrar ventana principal
        passengerView.setVisible(true);
    }
}
