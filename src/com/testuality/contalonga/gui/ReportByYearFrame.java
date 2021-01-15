package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportByYearFrame extends JFrame {
    private DataModel dataModel;
    private JContalonga app;

    private JComboBox yearCombo;

    private DefaultTableModel typeTableModel;
    private DefaultTableModel subtypeTableModel;

    public ReportByYearFrame(DataModel dataModel, JContalonga app) {
        super();
        this.dataModel = dataModel;
        this.app = app;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Report by year");
        //this.setAlwaysOnTop(true);
        this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        this.setLocationRelativeTo(this.app);

        this.setLayout(new MigLayout());

        JPanel formPanel = new JPanel();

        formPanel.add(new JLabel("Year:"));
        this.yearCombo = new JComboBox();
        for (int year = 2006; year <= 2030; year++) {
            yearCombo.addItem(Integer.valueOf(year));
        }

        formPanel.add(yearCombo);
        JButton yearBtn = new JButton("Report");
        yearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReport();
            }
        });
        formPanel.add(yearBtn, "wrap");
        this.add(formPanel, "left, wrap");

        this.typeTableModel = new DefaultTableModel();
        this.typeTableModel.addColumn("Type");
        this.typeTableModel.addColumn("Amount");
        this.typeTableModel.addColumn("Percent");
        JTable typeTable = new JTable(this.typeTableModel);

        this.add(new JScrollPane(typeTable), "pushx, growx, wrap");

        this.subtypeTableModel = new DefaultTableModel();
        this.subtypeTableModel.addColumn("Type");
        this.subtypeTableModel.addColumn("Subtype");
        this.subtypeTableModel.addColumn("Amount");
        this.subtypeTableModel.addColumn("Percent");
        JTable subtypeTable = new JTable(this.subtypeTableModel);

        this.add(new JScrollPane(subtypeTable), "pushx, growx, wrap");

        JButton closeBtn = new JButton("Close");
        this.add(closeBtn, "right");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.pack();
    }

    private void createReport() {
        int year = ((Integer)this.yearCombo.getSelectedItem()).intValue();

        double totalAmount = this.dataModel.getTotalExpenseAmountByYear(year);
    }
}
