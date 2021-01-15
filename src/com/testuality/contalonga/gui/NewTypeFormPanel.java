package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.beans.Type;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class NewTypeFormPanel extends JPanel {
    private DataModel dataModel;
    private JContalonga app;
    private JTextField newTypeNameField;
    private JComboBox typeCombo;
    private JTextField newSubtypeNameField;

    public NewTypeFormPanel(DataModel dataModel, JContalonga app) {
        super();
        this.dataModel = dataModel;
        this.app = app;

        JPanel newTypeForm = new JPanel();
        newTypeForm.setBorder(BorderFactory.createTitledBorder("New type"));
        newTypeForm.setLayout(new MigLayout());
        newTypeForm.add(new JLabel("Type name:"));
        this.newTypeNameField = new JTextField(20);
        newTypeForm.add(this.newTypeNameField, "wrap");
        JButton newTypeBtn = new JButton("Create type");
        newTypeForm.add(newTypeBtn, "skip");

        JPanel newSubtypeForm = new JPanel();
        newSubtypeForm.setBorder(BorderFactory.createTitledBorder("New subtype"));
        newSubtypeForm.setLayout(new MigLayout());
        newSubtypeForm.add(new JLabel("Type:"));
        this.typeCombo = new JComboBox();
        this.reloadTypes();
        newSubtypeForm.add(typeCombo, "wrap");
        newSubtypeForm.add(new JLabel("Subtype name:"));
        this.newSubtypeNameField = new JTextField(20);
        newSubtypeForm.add(this.newSubtypeNameField, "wrap");
        JButton newSubtypeBtn = new JButton("Create subtype");
        newSubtypeForm.add(newSubtypeBtn, "skip");

        this.setLayout(new MigLayout());
        this.add(newTypeForm, "wrap");
        this.add(newSubtypeForm);

        newTypeBtn.addActionListener(new NewTypeActionListener());
        newSubtypeBtn.addActionListener((new NewSubtypeActionListener()));
    }

    public void reloadTypes() {
        this.typeCombo.removeAllItems();
        if (this.dataModel.getTypes() != null) {
            for (Type type : this.dataModel.getTypes()) {
                this.typeCombo.addItem(type);
            }
        }
    }

    class NewTypeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String typeName = newTypeNameField.getText();
            boolean created = false;
            if (typeName != null){
                String trimmedTypeName = typeName.trim();
              if (trimmedTypeName.length() > 0) {
                  dataModel.createNewType(trimmedTypeName.toUpperCase());
                  created = true;
              }
            }
            if (created) {
                JOptionPane.showMessageDialog(app,
                        "The type " + typeName.trim().toUpperCase() + " was created",
                        "Type created",
                        JOptionPane.INFORMATION_MESSAGE);
                        app.reloadTypes();
                        reloadTypes();
                        newTypeNameField.setText("");
            }
            else {
                JOptionPane.showMessageDialog(app,
                        "The type was not created",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class NewSubtypeActionListener implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Type type = (Type)typeCombo.getSelectedItem();
            String subtypeName = newSubtypeNameField.getText();
            boolean created = false;
            if (subtypeName != null){
                String trimmedSubtypeName = subtypeName.trim();
                if (trimmedSubtypeName.length() > 0) {
                    dataModel.createNewSubtype(type.getId(), trimmedSubtypeName.toUpperCase());
                    created = true;
                }
            }
            if (created) {
                JOptionPane.showMessageDialog(app,
                        "The subtype " + subtypeName.trim().toUpperCase() + " was created",
                        "Subtype created",
                        JOptionPane.INFORMATION_MESSAGE);
                app.reloadTypes();
                newSubtypeNameField.setText("");
            }
            else {
                JOptionPane.showMessageDialog(app,
                        "The subtype was not created",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
