package com.testuality.contalonga.gui;

import com.testuality.contalonga.beans.Subtype;
import com.testuality.contalonga.beans.Type;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Enumeration;

public class TypesPanel extends JPanel {
    private DataModel dataModel;
    private JTable table;
    private DefaultTableModel tableModel;

    public TypesPanel(DataModel dataModel) {
        super();
        this.dataModel = dataModel;

        this.tableModel = new DefaultTableModel();
        this.tableModel.addColumn("Type");
        this.tableModel.addColumn("Subtypes");
        this.table = new JTable(this.tableModel);
        this.table.setEnabled(false);
        this.table.getTableHeader().setVisible(true);
        this.table.getTableHeader().setName("Types and subtypes");
        if (this.dataModel.getTypes() != null) {
            addRowsToTableModel();
        }
        this.setLayout(new MigLayout());
        this.add(new JScrollPane(this.table), "growx, pushx");
    }

    private void addRowsToTableModel() {
        for (Type type : this.dataModel.getTypes()) {
            StringBuffer sb = new StringBuffer();
            for (Subtype subtype : type.getSubtypeList()) {
                sb.append(subtype.getName()).append(" ");
            }
            this.tableModel.addRow(new String[]{type.getName(), sb.toString()});
        }
    }

    public void reloadDataModel() {
        while (this.tableModel.getRowCount() > 0) {
            this.tableModel.removeRow(0);
        }
        if (this.dataModel.getTypes() != null) {
            this.addRowsToTableModel();
        }
    }
}
