package Views;

import Controllers.PassengerController;
import Models.Passenger;
import Utils.Response;
import Utils.ResponseCode;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PassengerView extends JFrame {
    private PassengerController controller;
    private JTextField txtId, txtFirstName, txtLastName, txtCountryCode, txtPhone, txtCountry;
    private JButton btnRegister, btnUpdate, btnShowFlights;
    private JTable tblPassengers;

    public PassengerView(PassengerController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestión de Pasajeros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos del formulario
        addField(formPanel, gbc, "ID:", txtId = new JTextField(15), 0);
        addField(formPanel, gbc, "Nombre:", txtFirstName = new JTextField(15), 1);
        addField(formPanel, gbc, "Apellido:", txtLastName = new JTextField(15), 2);
        addField(formPanel, gbc, "Código País:", txtCountryCode = new JTextField(5), 3);
        addField(formPanel, gbc, "Teléfono:", txtPhone = new JTextField(10), 4);
        addField(formPanel, gbc, "País:", txtCountry = new JTextField(15), 5);

        // Botones
        JPanel buttonPanel = new JPanel();
        btnRegister = new JButton("Registrar");
        btnUpdate = new JButton("Actualizar");
        btnShowFlights = new JButton("Ver Vuelos");

        btnRegister.addActionListener(e -> registerPassenger());
        btnUpdate.addActionListener(e -> updatePassenger());
        btnShowFlights.addActionListener(e -> showFlights());

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnShowFlights);

        // Tabla de pasajeros
        tblPassengers = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Nombre", "País", "Teléfono"}, 0
        ));
        JScrollPane scrollPane = new JScrollPane(tblPassengers);

        // Layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);

        loadPassengers();
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void registerPassenger() {
        try {
            long id = Long.parseLong(txtId.getText());
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            int countryCode = Integer.parseInt(txtCountryCode.getText());
            long phone = Long.parseLong(txtPhone.getText());
            String country = txtCountry.getText();

            Response response = controller.createPassenger(
                id, firstName, lastName, LocalDate.now(), // Fecha dummy, ajustar
                countryCode, phone, country
            );

            handleResponse(response);
            loadPassengers();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePassenger() {
        // Lógica similar a register, usando controller.updatePassenger(...)
    }

    private void showFlights() {
        int selectedRow = tblPassengers.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pasajero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        long id = (Long) tblPassengers.getValueAt(selectedRow, 0);
        // Abrir vista de vuelos del pasajero
    }

    private void handleResponse(Response response) {
        if (response.getCode() == ResponseCode.SUCCESS) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtCountryCode.setText("");
        txtPhone.setText("");
        txtCountry.setText("");
    }

    private void loadPassengers() {
    DefaultTableModel model = (DefaultTableModel) tblPassengers.getModel();
    model.setRowCount(0);
    
    Response<List<Passenger>> response = controller.getAllPassengers();
    
    if (response.getCode() == ResponseCode.SUCCESS) {
        List<Passenger> passengers = response.getData();
        passengers.forEach(p -> {
            model.addRow(new Object[]{
                p.getId(),
                p.getFirstname(),
                p.getLastname(),
                p.getCountry(),
                p.getPhone()
            });
        });
    } else {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}