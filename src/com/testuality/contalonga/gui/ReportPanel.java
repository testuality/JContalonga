package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.beans.Type;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.fillTypesCombo();
        JButton byTypeBtn = new JButton("Create report");
        JButton byTypeChartBtn = new JButton("Create chart");
        byTypePanel.add(new JLabel("Type"));
        byTypePanel.add(this.typesCombo);
        byTypePanel.add(byTypeBtn);
        byTypePanel.add(byTypeChartBtn);

        JPanel byYearPanel = new JPanel();
        byYearPanel.setBorder(BorderFactory.createTitledBorder("Report by type for year"));
        this.yearCombo = new JComboBox();
        this.fillYearsCombo();
        JButton byYearBtn = new JButton("Create report");
        JButton byYearChartBtn = new JButton("Create chart");
        byYearPanel.add(new JLabel("Year"));
        byYearPanel.add(this.yearCombo);
        byYearPanel.add(byYearBtn);
        byYearPanel.add(byYearChartBtn);

        this.add(byTypePanel, "wrap");
        this.add(byYearPanel);

        byTypeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Type type =(Type)typesCombo.getSelectedItem();
                JFrame f = new ReportByTypeFrame(dataModel, app, type);
                f.setVisible(true);
            }
        });

        byTypeChartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Type type =(Type)typesCombo.getSelectedItem();
                JFrame f = new ChartByTypeFrame(dataModel, app, type);
                f.setVisible(true);
            }
        });

        byYearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer year = (Integer)yearCombo.getSelectedItem();
                JFrame f = new ReportByYearFrame(dataModel, app, year.intValue());
                f.setVisible(true);
            }
        });

        byYearChartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer year = (Integer)yearCombo.getSelectedItem();
                JFrame f = new ChartByYearFrame(dataModel, app, year.intValue());
                f.setVisible(true);
            }
        });
    }

    private void fillTypesCombo() {
        this.typesCombo.removeAllItems();
        if (this.dataModel.getTypes() != null) {
            for (com.testuality.contalonga.beans.Type type : this.dataModel.getTypes()) {
                this.typesCombo.addItem(type);
            }
        }
    }

    private void fillYearsCombo() {
        this.yearCombo.removeAllItems();
        if (this.dataModel.getExpenses() != null) {
            int yearIni = 2006; //this.dataModel.getExpenses().get(0).getDate().get(Calendar.YEAR);
            int yearEnd = this.dataModel.getExpenses().get(this.dataModel.getExpenses().size() - 1).getDate().get(Calendar.YEAR) + 1;

            for (int year = yearIni; year <= yearEnd; year++) {
                this.yearCombo.addItem(Integer.valueOf(year));
            }
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
