package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.model.DataModel;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {

    private DataModel dataModel;
    private JContalonga app;
    private MovementsPanel movementsPanel;
    private ExpensesPanel expensesPanel;
    private TypesPanel typesPanel;
    private NewTypeFormPanel newTypeFormPanel;

    public ContentPanel(DataModel dataModel, JContalonga app) {
        super();
        this.dataModel = dataModel;
        this.app = app;
        this.movementsPanel = new MovementsPanel(this.dataModel);
        this.expensesPanel = new ExpensesPanel(this.dataModel);
        this.typesPanel = new TypesPanel(this.dataModel);
        this.newTypeFormPanel = new NewTypeFormPanel(this.dataModel, this.app);

        this.setLayout(new CardLayout());
        this.add(movementsPanel, "movements");
        this.add(expensesPanel, "expenses");
        this.add(typesPanel, "types");
        this.add(newTypeFormPanel, "newtypeform");
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

    public void showNewTypeForm() {
        CardLayout cl = (CardLayout) this.getLayout();
        cl.show(this, "newtypeform");
    }

    public void reloadDataModel() {
        this.movementsPanel.reloadDataModel();
        this.expensesPanel.reloadDataModel();
        this.typesPanel.reloadDataModel();
        this.newTypeFormPanel.reloadTypes();
    }

    public void reloadTypes() {
        this.typesPanel.reloadDataModel();
    }

}
