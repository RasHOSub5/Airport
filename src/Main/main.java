/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author Rashid
 */
import Core.Controllers.FlightController;
import Core.Controllers.PassengerController;
import Core.Controllers.*;
import Core.Repository.FlightRepository;
import Core.Repository.JsonFlightRepository;
import Core.Repository.JsonLocationRepository;
import Core.Repository.JsonPassengerRepository;
import Core.Repository.JsonPlaneRepository;
import Core.Repository.PassengerRepository;
import Core.Repository.PlaneRepository;
import Core.Repository.*;
import Core.Views.FlightView;
import Core.Views.PassengerView;
import Views.*;

public class Main {
    public static void main(String[] args) {
        // Inicializar repositorios
        PassengerRepository passengerRepo = new JsonPassengerRepository();
        FlightRepository flightRepo = new JsonFlightRepository();
        PlaneRepository planeRepo = new JsonPlaneRepository();
        LocationRepository locationRepo = (LocationRepository) new JsonLocationRepository();

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
