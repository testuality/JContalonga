package com.testuality.contalonga.gui;

import com.testuality.contalonga.model.DataModel;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {

    private DataModel dataModel;
    private MovementsPanel movementsPanel;
    private ExpensesPanel expensesPanel;
    private TypesPanel typesPanel;

    public ContentPanel(DataModel dataModel) {
        super();
        this.dataModel = dataModel;
        this.movementsPanel = new MovementsPanel(this.dataModel);
        this.expensesPanel = new ExpensesPanel(this.dataModel);
        this.typesPanel = new TypesPanel(this.dataModel);

        this.setLayout(new CardLayout());
        this.add(movementsPanel, "movements");
        this.add(expensesPanel, "expenses");
        this.add(typesPanel, "types");
    }

    public void showMovementsPanel() {
        CardLayout cl = (CardLayout) this.getLayout();
        cl.show(this, "movements");
    }

    public void showExpensesPanel() {
        CardLayout cl = (CardLayout) this.getLayout();
        cl.show(this, "expenses");
    }

    public void showTypesPanel() {
        CardLayout cl = (CardLayout) this.getLayout();
        cl.show(this, "types");
    }

    public void reloadDataModel() {
        this.movementsPanel.reloadDataModel();
        this.expensesPanel.reloadDataModel();
        this.typesPanel.reloadDataModel();
    }
}
