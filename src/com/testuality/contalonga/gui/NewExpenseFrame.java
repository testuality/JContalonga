package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.beans.Subtype;
import com.testuality.contalonga.beans.Type;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class NewExpenseFrame extends JFrame {

    private DataModel dataModel;
    private JContalonga app;

    private JTextField dateField;
    private JComboBox typeCombo;
    private JComboBox subtypeCombo;
    private JTextField conceptField;
    private JTextField amountField;
    private JButton createBtn;

    public NewExpenseFrame(DataModel dataModel, JContalonga app) {
        super();
        this.dataModel = dataModel;
        this.app = app;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("New expense");
        //this.setAlwaysOnTop(true);
        this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        this.setLocationRelativeTo(this.app);

        JPanel formPanel = new JPanel();
        formPanel.setBorder(BorderFactory.createEtchedBorder());
        formPanel.setLayout(new MigLayout());
        formPanel.add(new JLabel("Date:"));
        this.dateField = new JTextField(10);
        formPanel.add(this.dateField, "wrap");
        formPanel.add(new JLabel("Type and subtype:"));
        this.typeCombo = new JComboBox();
        this.fillTypesCombo();
        formPanel.add(this.typeCombo, "split 2");
        this.subtypeCombo = new JComboBox();
        this.fillSubtypesCombo();
        formPanel.add(this.subtypeCombo, "wrap");
        formPanel.add(new JLabel("Concept"));
        this.conceptField = new JTextField(20);
        formPanel.add(this.conceptField, "wrap");
        formPanel.add(new JLabel("Amount:"));
        this.amountField = new JTextField(10);
        formPanel.add(this.amountField, "wrap");
        this.createBtn = new JButton("Create expense");
        formPanel.add(this.createBtn, "skip, right");

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.setLayout(new MigLayout());
        this.add(formPanel, "wrap");
        this.add(closeBtn, "right");
        this.pack();

        this.typeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillSubtypesCombo();
            }
        });

        this.createBtn.addActionListener(new CreateActionListener());
    }

    private void fillTypesCombo() {
        for (com.testuality.contalonga.beans.Type type : this.dataModel.getTypes()) {
            this.typeCombo.addItem(type);
        }
    }

    private void fillSubtypesCombo() {
        com.testuality.contalonga.beans.Type type = (com.testuality.contalonga.beans.Type)this.typeCombo.getSelectedItem();
        this.subtypeCombo.removeAllItems();
        for (Subtype subtype : type.getSubtypeList()) {
            this.subtypeCombo.addItem(subtype);
        }
    }

    public class CreateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean created = false;
            try {
                System.out.println("Create action listener");
                String dateStr = dateField.getText();
                GregorianCalendar date = new GregorianCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date auxDate = sdf.parse(dateStr);
                date.setTimeInMillis(auxDate.getTime());
                String typeId = ((com.testuality.contalonga.beans.Type)typeCombo.getSelectedItem()).getId();
                String subtypeId = ((Subtype)subtypeCombo.getSelectedItem()).getId();
                String concept = conceptField.getText();
                double amount = Double.parseDouble(amountField.getText());

                System.out.println(dateStr + " " + typeId + " " + subtypeId + " " + concept + " " + amount);
                dataModel.createNewExpense(date, typeId, subtypeId, concept, amount);
                app.reloadDataModel();
                created = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (created) {
                int response = JOptionPane.showConfirmDialog(app,
                        "Expense created. OK create another one.", "Expense created",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                System.out.println("response " + response);
                if (response == JOptionPane.YES_OPTION) {
                    // Nadas
                }
                else {
                    dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(app,"The expense has not been created","Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
