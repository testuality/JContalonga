package com.testuality.contalonga.gui;

import com.testuality.contalonga.beans.BankMovement;
import com.testuality.contalonga.beans.Expense;
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

public class ExpensesPanel extends JPanel {
    private DataModel dataModel;
    private JTable table;
    private DefaultTableModel tableModel;

    public ExpensesPanel(DataModel dataModel) {
        super();
        this.dataModel = dataModel;

        this.tableModel = new DefaultTableModel();
        this.tableModel.addColumn("Date");
        this.tableModel.addColumn("Type");
        this.tableModel.addColumn("Subtype");
        this.tableModel.addColumn("Concept");
        this.tableModel.addColumn("Amount");
        this.table = new JTable(this.tableModel);
        this.table.setEnabled(false);
        this.table.getTableHeader().setVisible(true);
        this.table.getTableHeader().setName("Expenses");

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        this.table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        if (this.dataModel.getExpenses() != null) {
            addRowsToTableModel();
        }
        this.setLayout(new MigLayout());
        this.add(this.getFilterPanel(), "wrap");
        this.add(new JScrollPane(this.table), "growx, pushx");
    }

    private JPanel getFilterPanel() {
        JPanel filterPanel = new JPanel();

        JComboBox yearCombo = new JComboBox();
        yearCombo.addItem("Any");
        for (int year = 2004; year <= 2030; year++) {
            yearCombo.addItem(Integer.toString(year));
        }
        JComboBox typeCombo = new JComboBox();

        JComboBox subtypeCombo = new JComboBox();
        //subtypeCombo.addItem("Any");

        JButton filterBtn = new JButton("Filter");

        filterPanel.add(yearCombo);
        filterPanel.add(typeCombo);
        filterPanel.add(subtypeCombo);
        filterPanel.add(filterBtn);
        return filterPanel;
    }

    private void fillCombos() {
        /*
        typeCombo.addItem("Any");
        for (Type type : this.dataModel.getTypes()) {
            typeCombo.addItem(type);
        }
        
         */
    }

    private void addRowsToTableModel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat nf= NumberFormat.getInstance(Locale.UK);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        for (Expense expense : this.dataModel.getExpenses()) {
            this.tableModel.addRow(new String[]{
                    sdf.format(expense.getDate().getTime()),
                    this.dataModel.getTypeName(expense.getTypeId()),
                    this.dataModel.getSubtypeName(expense.getSubtypeId()),
                    expense.getDescription(),
                    nf.format(expense.getAmount())
            });
        }
    }

    public void reloadDataModel() {
        while (this.tableModel.getRowCount() > 0) {
            this.tableModel.removeRow(0);
        }
        if (this.dataModel.getExpenses() != null) {
            this.addRowsToTableModel();
        }
    }
}
