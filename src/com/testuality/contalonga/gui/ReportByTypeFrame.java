package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.beans.ReportForType;
import com.testuality.contalonga.beans.ReportItem;
import com.testuality.contalonga.beans.Type;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportByTypeFrame extends JFrame {
    private DataModel dataModel;
    private JContalonga app;

    private JComboBox typeCombo;
private JPanel tablesPanel;

    public ReportByTypeFrame(DataModel dataModel, JContalonga app) {
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
        this.typeCombo = new JComboBox();
        for (com.testuality.contalonga.beans.Type type : this.dataModel.getTypes()) {
            typeCombo.addItem(type);
        }

        formPanel.add(typeCombo);
        JButton yearBtn = new JButton("Report");
        yearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReport();
            }
        });
        formPanel.add(yearBtn, "wrap");
        this.add(formPanel, "left, wrap");

        this.tablesPanel = new JPanel();
        this.tablesPanel.setLayout(new MigLayout());
        this.add(this.tablesPanel, "wrap");

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
        com.testuality.contalonga.beans.Type selectedType = (com.testuality.contalonga.beans.Type)this.typeCombo.getSelectedItem();
        ReportForType report = this.dataModel.getReportForType(selectedType);

        this.tablesPanel.removeAll();

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        DefaultTableModel typeTableModel = new DefaultTableModel();
        typeTableModel.addColumn("Year");
        typeTableModel.addColumn("Amount");
        typeTableModel.addColumn("Percent");
        JTable typeTable = new JTable(typeTableModel);
        typeTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        typeTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        this.tablesPanel.add(new JScrollPane(typeTable), "wrap");

        for (java.util.List<ReportItem> list : report.getSubtypeLists()) {
            this.tablesPanel.add(new JLabel(selectedType.getName()), "wrap");
            // this.tablesPanel.add(new JLabel(selectedType.getName()), "wrap");
            JTable subtypeTable = new JTable(typeTableModel);
            subtypeTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
            subtypeTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            this.tablesPanel.add(new JScrollPane(subtypeTable), "wrap");
        }
        this.tablesPanel.repaint();
    }
}
