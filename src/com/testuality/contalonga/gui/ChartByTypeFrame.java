package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChartByTypeFrame extends JFrame {
    private DataModel dataModel;
    private JContalonga app;
    private com.testuality.contalonga.beans.Type type;

    private JPanel chartsPanel;

    public ChartByTypeFrame(DataModel dataModel, JContalonga app,
                             com.testuality.contalonga.beans.Type type) {
        super();
        this.dataModel = dataModel;
        this.app = app;
        this.type = type;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Chart by year fot type");
        //this.setAlwaysOnTop(true);
        this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        this.setLocationRelativeTo(this.app);
        this.setSize(400,400);

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());

        panel.add(new JLabel("Chart by year for type " + this.type.getName()), "wrap");

        this.chartsPanel = this.getChartPanel();
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
        //this.pack();
    }

    private JPanel getChartPanel() {
        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };

        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        return new XChartPanel<>(chart);
    }
}

class ChartPanel extends JPanel {

    @Override
    public void paint(Graphics g) {
        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };

        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart",
                "X", "Y", "y(x)", xData, yData);
        chart.paint((Graphics2D) g, 300,300);

    }
}
