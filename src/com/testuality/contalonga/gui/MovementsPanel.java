package com.testuality.contalonga.gui;

import com.testuality.contalonga.beans.BankMovement;
import com.testuality.contalonga.beans.Subtype;
import com.testuality.contalonga.beans.Type;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MovementsPanel extends JPanel {
    private DataModel dataModel;
    private JTable table;
    private DefaultTableModel tableModel;

    public MovementsPanel(DataModel dataModel) {
        super();
        this.dataModel = dataModel;

        this.tableModel = new DefaultTableModel();
        this.tableModel.addColumn("Date");
        this.tableModel.addColumn("Concept");
        this.tableModel.addColumn("Amount");
        this.table = new JTable(this.tableModel);
        this.table.setEnabled(false);
        this.table.getTableHeader().setVisible(true);
        this.table.getTableHeader().setName("Types and subtypes");

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        this.table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        if (this.dataModel.getBankMovements() != null) {
            addRowsToTableModel();
        }
        this.setLayout(new MigLayout());
        this.add(new JScrollPane(this.table), "growx, pushx");
    }

    private void addRowsToTableModel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat nf= NumberFormat.getInstance(Locale.UK);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        for (BankMovement movement : this.dataModel.getBankMovements()) {
            this.tableModel.addRow(new String[]{
                    sdf.format(movement.getDate().getTime()),
                    movement.getConcept(),
                    nf.format(movement.getAmount())});
        }
    }

    public void reloadDataModel() {
        while (this.tableModel.getRowCount() > 0) {
            this.tableModel.removeRow(0);
        }
        if (this.dataModel.getBankMovements() != null) {
            this.addRowsToTableModel();
        }
    }
}
