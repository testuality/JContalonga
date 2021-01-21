package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChartByYearFrame extends JFrame {
    private DataModel dataModel;
    private JContalonga app;
    private int year;

    private JPanel chartsPanel;

    public ChartByYearFrame(DataModel dataModel, JContalonga app, int year) {
        super();
        this.dataModel = dataModel;
        this.app = app;
        this.year = year;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Chart by type for year " + this.year);
        //this.setAlwaysOnTop(true);
        this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        this.setLocationRelativeTo(this.app);

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());

        panel.add(new JLabel("Chart by type for year " + this.year), "wrap");

        this.chartsPanel = new JPanel();
        this.chartsPanel.setLayout(new MigLayout());
        panel.add(this.chartsPanel, "wrap");

        JButton closeBtn = new JButton("Close");
        panel.add(closeBtn, "right");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.add(new JScrollPane(panel));
        this.createChart();
        this.pack();
    }

    private void createChart() {
    }
}
