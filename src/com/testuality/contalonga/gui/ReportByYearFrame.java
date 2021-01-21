package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.beans.ReportForYear;
import com.testuality.contalonga.beans.ReportItem;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class ReportByYearFrame extends JFrame {
    private DataModel dataModel;
    private JContalonga app;
    private int year;

    private DefaultTableModel typeTableModel;
    private DefaultTableModel subtypeTableModel;

    public ReportByYearFrame(DataModel dataModel, JContalonga app, int year) {
        super();
        this.dataModel = dataModel;
        this.app = app;
        this.year = year;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Report by type for year " + this.year);
        //this.setAlwaysOnTop(true);
        this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        this.setLocationRelativeTo(this.app);

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());

        panel.add(new JLabel("Report by type for year " + this.year), "wrap");

        this.typeTableModel = new DefaultTableModel();
        this.typeTableModel.addColumn("Type");
        this.typeTableModel.addColumn("Amount");
        this.typeTableModel.addColumn("Percent");
        // this.typeTableModel.addColumn("Percent");
        JTable typeTable = new JTable(this.typeTableModel);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        typeTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        typeTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        panel.add(new JScrollPane(typeTable), "pushx, growx, wrap");

        this.subtypeTableModel = new DefaultTableModel();
        this.subtypeTableModel.addColumn("Type");
        this.subtypeTableModel.addColumn("Subtype");
        this.subtypeTableModel.addColumn("Amount");
        this.subtypeTableModel.addColumn("Percent");
        // this.subtypeTableModel.addColumn("Percent");
        JTable subtypeTable = new JTable(this.subtypeTableModel);

        subtypeTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        subtypeTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        panel.add(new JScrollPane(subtypeTable), "pushx, growx, wrap");

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
        ReportForYear report = this.dataModel.getReportForYear(this.year);
        // double totalAmount = this.dataModel.getTotalExpenseAmountByYear(year);

        NumberFormat nf= NumberFormat.getInstance(Locale.UK);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

        while (this.typeTableModel.getRowCount() > 0) {
            this.typeTableModel.removeRow(0);
        }
        while (this.subtypeTableModel.getRowCount() > 0 ) {
            this.subtypeTableModel.removeRow(0);
        }

        // Rellenar la tabla
        for (ReportItem item : report.getByTypeList()) {
            // JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)Math.round(item.getPercent()));
            this.typeTableModel.addRow(new Object[] {item.getType().getName(),
                    nf.format(item.getAmount()), nf.format(item.getPercent())});
        }

        for (ReportItem item : report.getByTypeSubtypeList()) {
            // JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)Math.round(item.getPercent()));
            this.subtypeTableModel.addRow(new Object[] {
                    item.getType().getName(), item.getSubtype().getName(),
                    nf.format(item.getAmount()), nf.format(item.getPercent())
            });
        }
    }
}
