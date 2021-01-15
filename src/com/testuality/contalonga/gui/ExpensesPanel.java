package com.testuality.contalonga.gui;

import com.testuality.contalonga.beans.BankMovement;
import com.testuality.contalonga.beans.Expense;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExpensesPanel extends JPanel {
    private DataModel dataModel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox yearCombo;
    private JComboBox typeCombo;
    private JComboBox subtypeCombo;
    private JButton filterBtn;

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

        this.typeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillSubtypeCombo();
                System.out.println("action combo");
            }
        });
        this.filterBtn.addActionListener(new FilterActionListener());
    }

    private JPanel getFilterPanel() {
        JPanel filterPanel = new JPanel();

        this.yearCombo = new JComboBox();
        yearCombo.addItem("Any");
        for (int year = 2004; year <= 2030; year++) {
            yearCombo.addItem(Integer.valueOf(year));
        }
        this.typeCombo = new JComboBox();
        this.typeCombo.addItem("Any");

        this.subtypeCombo = new JComboBox();
        this.subtypeCombo.addItem("Any");

        this.filterBtn = new JButton("Filter");

        filterPanel.add(yearCombo);
        filterPanel.add(typeCombo);
        filterPanel.add(subtypeCombo);
        filterPanel.add(filterBtn);
        return filterPanel;
    }

    private void fillFilterCombos() {
        this.typeCombo.removeAllItems();
        typeCombo.addItem("Any");
        for (Type type : this.dataModel.getTypes()) {
            this.typeCombo.addItem(type);
        }
        this.subtypeCombo.removeAllItems();
        this.subtypeCombo.addItem("Any");
    }

    private void fillSubtypeCombo() {
        Object selectedItem = this.typeCombo.getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem instanceof Type)  {
                Type selectedType = (Type)selectedItem;
                this.subtypeCombo.removeAllItems();
                this.subtypeCombo.addItem("Any");
                for (Subtype subtype : selectedType.getSubtypeList()) {
                    this.subtypeCombo.addItem(subtype);
                }
            }
            else if (selectedItem instanceof String) {
                this.subtypeCombo.removeAllItems();
                this.subtypeCombo.addItem("Any");
            }
        }
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

    private void addFilteredRowsToTableModel(Integer year, String typeId, String subtypeId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat nf= NumberFormat.getInstance(Locale.UK);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        for (Expense expense : this.dataModel.getExpenses()) {
            int expenseYear = expense.getDate().get(Calendar.YEAR);

            if ((year == null || year.intValue() == expenseYear) &&
                (typeId == null || typeId.equals(expense.getTypeId()) &&
                (subtypeId == null || subtypeId.equals(expense.getSubtypeId())))) {
                this.tableModel.addRow(new String[]{
                        sdf.format(expense.getDate().getTime()),
                        this.dataModel.getTypeName(expense.getTypeId()),
                        this.dataModel.getSubtypeName(expense.getSubtypeId()),
                        expense.getDescription(),
                        nf.format(expense.getAmount())
                });
            }
        }
    }

    public void reloadDataModel() {
        this.fillFilterCombos();
        while (this.tableModel.getRowCount() > 0) {
            this.tableModel.removeRow(0);
        }
        if (this.dataModel.getExpenses() != null) {
            this.addRowsToTableModel();
        }
    }

    private void reloadFilteredDataModel(Integer year, String typeId, String subtypeId) {
        while (this.tableModel.getRowCount() > 0) {
            this.tableModel.removeRow(0);
        }
        if (this.dataModel.getExpenses() != null) {
            this.addFilteredRowsToTableModel(year, typeId, subtypeId);
        }
    }

    class FilterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Integer year = null;
            String typeId = null;
            String subtypeId = null;
            Object selectedYear = yearCombo.getSelectedItem();
            if (selectedYear instanceof Integer) {
                year = (Integer)selectedYear;
            }

            Object selectedType = typeCombo.getSelectedItem();
            if (selectedType instanceof Type) {
                typeId = ((Type)selectedType).getId();

                Object selectedSubtype = subtypeCombo.getSelectedItem();
                if (selectedSubtype instanceof Subtype) {
                    subtypeId = ((Subtype)selectedSubtype).getId();
                }
            }
            reloadFilteredDataModel(year, typeId, subtypeId);
        }
    }
}
