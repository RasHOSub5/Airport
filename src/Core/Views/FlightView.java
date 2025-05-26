package Core.Views;

import Core.Controllers.FlightController;
import Core.Models.Flight;
import Utils.Response;
import Utils.ResponseCode;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class FlightView extends JFrame {
    private FlightController controller;
    private JTextField txtFlightId, txtDepartureDate, txtDurationHours, txtDurationMinutes;
    private JComboBox<String> cmbPlanes, cmbDeparture, cmbArrival;
    private JButton btnCreate, btnDelay;
    private JTable tblFlights;

    public FlightView(FlightController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestión de Vuelos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.add(new JLabel("ID Vuelo:"));
        formPanel.add(txtFlightId = new JTextField());
        formPanel.add(new JLabel("Avión:"));
        formPanel.add(cmbPlanes = new JComboBox<>());
        formPanel.add(new JLabel("Salida:"));
        formPanel.add(cmbDeparture = new JComboBox<>());
        formPanel.add(new JLabel("Llegada:"));
        formPanel.add(cmbArrival = new JComboBox<>());
        formPanel.add(new JLabel("Fecha Salida (yyyy-MM-dd HH:mm):"));
        formPanel.add(txtDepartureDate = new JTextField());
        formPanel.add(new JLabel("Duración (horas):"));
        formPanel.add(txtDurationHours = new JTextField());
        formPanel.add(new JLabel("Duración (minutos):"));
        formPanel.add(txtDurationMinutes = new JTextField());

        // Botones
        JPanel buttonPanel = new JPanel();
        btnCreate = new JButton("Crear Vuelo");
        btnDelay = new JButton("Retrasar Vuelo");

        btnCreate.addActionListener(e -> createFlight());
        btnDelay.addActionListener(e -> delayFlight());

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnDelay);

        // Tabla de vuelos
        tblFlights = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Salida", "Llegada", "Avión"}, 0
        ));
        JScrollPane scrollPane = new JScrollPane(tblFlights);

        // Layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);

        loadFlights();
        loadCombos();
    }

    private void createFlight() {
        try {
            String flightId = txtFlightId.getText();
            // Lógica para obtener avión y ubicaciones desde los combos
            Response response = controller.createFlight(
                flightId,
                null, // Reemplazar con objeto Plane
                null, // Reemplazar con Location salida
                null, // Reemplazar con Location llegada
                LocalDateTime.parse(txtDepartureDate.getText()),
                Integer.parseInt(txtDurationHours.getText()),
                Integer.parseInt(txtDurationMinutes.getText())
            );
            handleResponse(response);
            loadFlights();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void delayFlight() {
        int selectedRow = tblFlights.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un vuelo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String flightId = (String) tblFlights.getValueAt(selectedRow, 0);
        // Lógica para obtener horas/minutos de retraso
        Response response = controller.delayFlight(flightId, 1, 30);
        handleResponse(response);
        loadFlights();
    }

    private void handleResponse(Response response) {
        // Similar a PassengerView
    }

    private void loadFlights() {
        // Cargar datos desde el controlador
    }

    private void loadCombos() {
        // Cargar aviones y ubicaciones desde repositorios
    }
}