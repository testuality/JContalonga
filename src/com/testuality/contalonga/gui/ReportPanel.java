package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.Calendar;

public class ReportPanel extends JPanel {

    private DataModel dataModel;
    private JContalonga app;
    private JComboBox typesCombo;
    private JComboBox yearCombo;

    public ReportPanel(DataModel dataModel, JContalonga app) {
        super();
        this.dataModel = dataModel;
        this.app = app;

        this.setLayout(new MigLayout());

        JPanel byTypePanel = new JPanel();
        byTypePanel.setBorder(BorderFactory.createTitledBorder("Report by year for type"));
        this.typesCombo = new JComboBox();

        JButton byTypeBtn = new JButton("Create report");
        byTypePanel.add(new JLabel("Type"));
        byTypePanel.add(this.typesCombo);
        byTypePanel.add(byTypeBtn);

        JPanel byYearPanel = new JPanel();
        byYearPanel.setBorder(BorderFactory.createTitledBorder("Report by type for year"));
        this.yearCombo = new JComboBox();

        JButton byYearBtn = new JButton("Create report");
        byYearPanel.add(new JLabel("Year"));
        byYearPanel.add(this.yearCombo);
        byYearPanel.add(byYearBtn);

        this.add(byTypePanel, "wrap");
        this.add(byYearPanel);
    }

    private void fillTypesCombo() {
        // TODO
        for (com.testuality.contalonga.beans.Type type : this.dataModel.getTypes()) {
            this.typesCombo.addItem(type);
        }
    }

    private void fillYearsCombo() {
        // TODO
        int yearIni = this.dataModel.getExpenses().get(0).getDate().get(Calendar.YEAR);
        int yearEnd = this.dataModel.getExpenses().get(this.dataModel.getExpenses().size() - 1).getDate().get(Calendar.YEAR) + 1;

        for (int year = yearIni; year <= yearEnd; year++) {
            this.yearCombo.addItem(Integer.valueOf(year));
        }
    }

    public void reloadTypes() {
        this.fillTypesCombo();
    }

    public void reloadDataModel() {
        this.fillYearsCombo();
        this.fillTypesCombo();
    }
}
