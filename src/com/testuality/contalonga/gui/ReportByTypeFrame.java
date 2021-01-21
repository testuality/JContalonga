package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.beans.ReportForType;
import com.testuality.contalonga.beans.ReportItem;
import com.testuality.contalonga.beans.Subtype;
import com.testuality.contalonga.beans.Type;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class ReportByTypeFrame extends JFrame {
    private DataModel dataModel;
    private JContalonga app;
    private com.testuality.contalonga.beans.Type type;

    private JPanel tablesPanel;

    public ReportByTypeFrame(DataModel dataModel, JContalonga app,
                             com.testuality.contalonga.beans.Type type) {
        super();
        this.dataModel = dataModel;
        this.app = app;
        this.type = type;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Report by year fot type");
        //this.setAlwaysOnTop(true);
        this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        this.setLocationRelativeTo(this.app);

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());

        panel.add(new JLabel("Report by year for type " + this.type.getName()), "wrap");

        this.tablesPanel = new JPanel();
        this.tablesPanel.setLayout(new MigLayout());
        panel.add(this.tablesPanel, "wrap");

        JButton closeBtn = new JButton("Close");
        panel.add(closeBtn, "right");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.add(new JScrollPane(panel));
        this.createReport();
        this.pack();
    }

    private void createReport() {
        ReportForType report = this.dataModel.getReportForType(this.type);

        NumberFormat nf= NumberFormat.getInstance(Locale.UK);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

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
        for (ReportItem item : report.getByTypeList()) {
            typeTableModel.addRow(new Object[]{item.getYear(),
                    nf.format(item.getAmount()),
                    nf.format(item.getPercent())});
        }

        for (java.util.List<ReportItem> list : report.getBySubtypeLists()) {
            Subtype subtype = list.get(0).getSubtype();
            this.tablesPanel.add(new JLabel(this.type.getName() + " " + subtype.getName()), "wrap");
            // this.tablesPanel.add(new JLabel(selectedType.getName()), "wrap");

            DefaultTableModel subtypeTableModel = new DefaultTableModel();
            subtypeTableModel.addColumn("Year");
            subtypeTableModel.addColumn("Amount");
            subtypeTableModel.addColumn("Percent");

            JTable subtypeTable = new JTable(subtypeTableModel);
            subtypeTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
            subtypeTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            this.tablesPanel.add(new JScrollPane(subtypeTable), "wrap");

            for (ReportItem item : list) {
                subtypeTableModel.addRow(new Object[]{item.getYear(),
                        nf.format(item.getAmount()),
                        nf.format(item.getPercent())});
            }
        }
        this.tablesPanel.repaint();
    }
}
