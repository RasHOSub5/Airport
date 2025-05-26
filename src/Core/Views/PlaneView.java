<<<<<<< HEAD
package Core.Views;

import Core.Controllers.PlaneController;
import Core.Models.Plane;
=======
package Views;

import Controllers.PlaneController;
import Models.Plane;
>>>>>>> 618bf3ce7120e29c31f0bdc433589ac4c49292ed
import Utils.Response;
import Utils.ResponseCode;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;  

public class PlaneView extends JFrame {
    private PlaneController controller;
    private JTextField txtId, txtBrand, txtModel, txtMaxCapacity, txtAirline;
    private JButton btnRegister, btnUpdate;
    private JTable tblPlanes;

    public PlaneView(PlaneController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestión de Aviones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos del formulario
        addField(formPanel, gbc, "ID (XXYYYYY):", txtId = new JTextField(15), 0);
        addField(formPanel, gbc, "Marca:", txtBrand = new JTextField(15), 1);
        addField(formPanel, gbc, "Modelo:", txtModel = new JTextField(15), 2);
        addField(formPanel, gbc, "Capacidad Máxima:", txtMaxCapacity = new JTextField(5), 3);
        addField(formPanel, gbc, "Aerolínea:", txtAirline = new JTextField(15), 4);

        // Botones
        JPanel buttonPanel = new JPanel();
        btnRegister = new JButton("Registrar");
        btnUpdate = new JButton("Actualizar");

        btnRegister.addActionListener(e -> registerPlane());
        btnUpdate.addActionListener(e -> updatePlane());

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnUpdate);

        // Tabla de aviones
        tblPlanes = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Marca", "Modelo", "Capacidad", "Aerolínea"}, 0
        ));
        JScrollPane scrollPane = new JScrollPane(tblPlanes);

        // Layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);

        loadPlanes();
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void registerPlane() {
        try {
            String id = txtId.getText();
            String brand = txtBrand.getText();
            String model = txtModel.getText();
            int maxCapacity = Integer.parseInt(txtMaxCapacity.getText());
            String airline = txtAirline.getText();

            Response response = controller.createPlane(id, brand, model, maxCapacity, airline);
            handleResponse(response);
            loadPlanes();
        } catch (NumberFormatException e) {
            showError("Capacidad máxima debe ser un número");
        }
    }

    private void updatePlane() {
        int selectedRow = tblPlanes.getSelectedRow();
        if (selectedRow == -1) {
            showError("Seleccione un avión");
            return;
        }

        try {
            String id = (String) tblPlanes.getValueAt(selectedRow, 0);
            String newBrand = txtBrand.getText();
            String newModel = txtModel.getText();
            String newAirline = txtAirline.getText();

            Response response = controller.updatePlane(id, newBrand, newModel, newAirline);
            handleResponse(response);
            loadPlanes();
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
        txtBrand.setText("");
        txtModel.setText("");
        txtMaxCapacity.setText("");
        txtAirline.setText("");
    }

    private void loadPlanes() {
    DefaultTableModel model = (DefaultTableModel) tblPlanes.getModel();
    model.setRowCount(0);
    
    Response<List<Plane>> response = controller.getAllPlanes();
    
    if (response.getCode() == ResponseCode.SUCCESS) {
        List<Plane> planes = response.getData();
        planes.forEach(plane -> {
            model.addRow(new Object[]{
                plane.getId(),
                plane.getBrand(),
                plane.getModel(),
                plane.getMaxCapacity(),
                plane.getAirline()
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