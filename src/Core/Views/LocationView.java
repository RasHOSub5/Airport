package Core.Views;

import Core.Controllers.LocationController;
import Core.Models.Location;
import Utils.Response;
import Utils.ResponseCode;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LocationView extends JFrame {
    private LocationController controller;
    private JTextField txtId, txtName, txtCity, txtCountry, txtLatitude, txtLongitude;
    private JButton btnRegister, btnUpdate;
    private JTable tblLocations;

    public LocationView(LocationController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestión de Aeropuertos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos del formulario
        addField(formPanel, gbc, "ID (AAA):", txtId = new JTextField(5), 0);
        addField(formPanel, gbc, "Nombre:", txtName = new JTextField(15), 1);
        addField(formPanel, gbc, "Ciudad:", txtCity = new JTextField(15), 2);
        addField(formPanel, gbc, "País:", txtCountry = new JTextField(15), 3);
        addField(formPanel, gbc, "Latitud:", txtLatitude = new JTextField(10), 4);
        addField(formPanel, gbc, "Longitud:", txtLongitude = new JTextField(10), 5);

        // Botones
        JPanel buttonPanel = new JPanel();
        btnRegister = new JButton("Registrar");
        btnUpdate = new JButton("Actualizar");

        btnRegister.addActionListener(e -> registerLocation());
        btnUpdate.addActionListener(e -> updateLocation());

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnUpdate);

        // Tabla de ubicaciones
        tblLocations = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Ciudad", "País", "Latitud", "Longitud"}, 0
        ));
        JScrollPane scrollPane = new JScrollPane(tblLocations);

        // Layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);

        loadLocations();
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void registerLocation() {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String city = txtCity.getText();
            String country = txtCountry.getText();
            double latitude = Double.parseDouble(txtLatitude.getText());
            double longitude = Double.parseDouble(txtLongitude.getText());

            Response response = controller.createLocation(id, name, city, country, latitude, longitude);
            handleResponse(response);
            loadLocations();
        } catch (NumberFormatException e) {
            showError("Latitud/Longitud deben ser números");
        }
    }

    private void updateLocation() {
        int selectedRow = tblLocations.getSelectedRow();
        if (selectedRow == -1) {
            showError("Seleccione un aeropuerto");
            return;
        }

        try {
            String id = (String) tblLocations.getValueAt(selectedRow, 0);
            String newName = txtName.getText();
            String newCity = txtCity.getText();
            String newCountry = txtCountry.getText();

            Response response = controller.updateLocation(id, newName, newCity, newCountry);
            handleResponse(response);
            loadLocations();
        } catch (Exception e) {
            showError("Error al actualizar");
        }
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
        txtName.setText("");
        txtCity.setText("");
        txtCountry.setText("");
        txtLatitude.setText("");
        txtLongitude.setText("");
    }

    private void loadLocations() {
    DefaultTableModel model = (DefaultTableModel) tblLocations.getModel();
    model.setRowCount(0);
    
    Response<List<Location>> response = controller.getAllLocations();
    
    if (response.getCode() == ResponseCode.SUCCESS) {
        List<Location> locations = response.getData(); 
        locations.forEach(location -> {
            model.addRow(new Object[]{
                location.getId(),
                location.getName(),
                location.getCity(),
                location.getCountry(),
                location.getLatitude(),
                location.getLongitude()
            });
        });
    } else {
        JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}