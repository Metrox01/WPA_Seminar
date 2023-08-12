package com.javaseminar.view;

import com.javaseminar.controller.AppointmentController;
import com.javaseminar.model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Vector;

public class AppointmentView {
    private JFrame frame;
    private JTextField txtUserName;
    private JTextField txtTitle;
    private JTextArea txtDescription;
    private SpinnerDateModel spinnerDateModelStart;
    private SpinnerDateModel spinnerDateModelEnd;
    private JSpinner.DateEditor dateEditorStart;
    private JSpinner.DateEditor dateEditorEnd;
    private JButton btnSave;
    private JButton btnDelete;
    private JButton btnNew;
    private JTable tableAppointments;
    private AppointmentController controller;
    private Appointment currentAppointment;

    public AppointmentView(AppointmentController controller) {

        this.controller = controller;
        frame = new JFrame("Appointment Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);;
        frame.setResizable(true);
        

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Auftr.Nr.");
        columnNames.add("Name");
        columnNames.add("Fahrzeug");
        columnNames.add("Beschreibung");
        columnNames.add("Start");
        columnNames.add("Ende");
        columnNames.add("Dauer");

        Vector<Vector<Object>> data = new Vector<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
        for (Appointment appointment : controller.getAllAppointments()) {
            Vector<Object> row = new Vector<>();
            row.add(appointment.getId());
            row.add(appointment.getUserName());
            row.add(appointment.getTitle());
            row.add(appointment.getDescription());
            row.add(dateFormat.format(appointment.getStartTime()));
            row.add(dateFormat.format(appointment.getEndTime()));
            Duration duration = Duration.between(appointment.getStartTime().toInstant(), appointment.getEndTime().toInstant());
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            row.add(hours + " Stunde(n) " + minutes + " Minute(n)");
            data.add(row);
        }

        tableAppointments = new JTable(data, columnNames);

        tableAppointments.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableAppointments.getSelectedRow();
                if (selectedRow >= 0) {
                    Long id = (Long) tableAppointments.getModel().getValueAt(selectedRow, 0);
                    loadAppointment(id);
                }
            }
        });

        tableAppointments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        

        JScrollPane tableContainer = new JScrollPane(tableAppointments);
        //tableContainer.setPreferredSize(new Dimension(0,0));
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        panel.add(tableContainer, c);

        c.gridy++;
        c.gridwidth = 1;
        panel.add(new JLabel("Name:"), c);
        c.gridx++;
        txtUserName = new JTextField(20);
        panel.add(txtUserName, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(new JLabel("Fahrzeug:"), c);
        c.gridx++;
        txtTitle = new JTextField(20);
        panel.add(txtTitle, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(new JLabel("Beschreibung:"), c);
        c.gridx++;
        JScrollPane descriptionScrollPane = new JScrollPane();
        txtDescription = new JTextArea(5, 1);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        descriptionScrollPane.setViewportView(txtDescription);
        descriptionScrollPane.setPreferredSize(new Dimension(900, descriptionScrollPane.getPreferredSize().height));
        panel.add(descriptionScrollPane, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(new JLabel("Beginn:"), c);
        c.gridx++;
        spinnerDateModelStart = new SpinnerDateModel();
        JSpinner spinnerStart = new JSpinner(spinnerDateModelStart);
        dateEditorStart = new JSpinner.DateEditor(spinnerStart, "dd-MM-yyyy HH:mm");
        spinnerStart.setEditor(dateEditorStart);
        panel.add(spinnerStart, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(new JLabel("Ende:"), c);
        c.gridx++;
        spinnerDateModelEnd = new SpinnerDateModel();
        JSpinner spinnerEnd = new JSpinner(spinnerDateModelEnd);
        dateEditorEnd = new JSpinner.DateEditor(spinnerEnd, "dd-MM-yyyy HH:mm");
        spinnerEnd.setEditor(dateEditorEnd);
        panel.add(spinnerEnd, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        btnNew = new JButton("Neuer Eintrag");
        btnNew.addActionListener(e -> newAppointment());
        panel.add(btnNew, c);

        c.gridx++;
        btnSave = new JButton("Speichern");
        btnSave.addActionListener(e -> saveAppointment());
        panel.add(btnSave, c);

        c.gridx++;
        btnDelete = new JButton("Löschen");
        btnDelete.addActionListener(e -> deleteAppointment());
        panel.add(btnDelete, c);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadAppointment(Long id) {
        currentAppointment = controller.getAppointment(id);
        txtUserName.setText(currentAppointment.getUserName());
        txtTitle.setText(currentAppointment.getTitle());
        txtDescription.setText(currentAppointment.getDescription());
        spinnerDateModelStart.setValue(currentAppointment.getStartTime());
        spinnerDateModelEnd.setValue(currentAppointment.getEndTime());
    }


    private void newAppointment() {
        currentAppointment = null;
        txtUserName.setText("");
        txtTitle.setText("");
        txtDescription.setText("");
        spinnerDateModelStart.setValue(new Date());
        spinnerDateModelEnd.setValue(new Date());
    }

    private void saveAppointment() {
        if (currentAppointment == null) {
            currentAppointment = new Appointment(txtUserName.getText(), txtTitle.getText(), txtDescription.getText(),
                spinnerDateModelStart.getDate(), spinnerDateModelEnd.getDate());
            controller.createAppointment(currentAppointment);
        } else {
            currentAppointment.setUserName(txtUserName.getText());
            currentAppointment.setTitle(txtTitle.getText());
            currentAppointment.setDescription(txtDescription.getText());
            currentAppointment.setStartTime(spinnerDateModelStart.getDate());
            currentAppointment.setEndTime(spinnerDateModelEnd.getDate());
            controller.updateAppointment(currentAppointment);
        }
        refreshTableData();
    }

    private void deleteAppointment() {
        int selectedRow = tableAppointments.getSelectedRow();
        if (selectedRow >= 0 && currentAppointment != null) {
            controller.deleteAppointment(currentAppointment);
            refreshTableData();
            newAppointment(); //formulat leeren
        }
    }

    private void refreshTableData() {
        DefaultTableModel tableModel = (DefaultTableModel) tableAppointments.getModel();
        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (Appointment appointment : controller.getAllAppointments()) {
            Vector<Object> row = new Vector<>();
            row.add(appointment.getId());
            row.add(appointment.getUserName());
            row.add(appointment.getTitle());
            row.add(appointment.getDescription());
            row.add(dateFormat.format(appointment.getStartTime())); 
            row.add(dateFormat.format(appointment.getEndTime()));
            Duration duration = Duration.between(appointment.getStartTime().toInstant(), appointment.getEndTime().toInstant());
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            row.add(hours + " Stunde(n) " + minutes + " Minute(n)");
            tableModel.addRow(row);
            }
            if (currentAppointment != null) {
                loadAppointment(currentAppointment.getId());  //laden der Daten von ausgewähltem Termin
        }
    }
}
