package com.testuality.contalonga.gui;

import com.testuality.contalonga.beans.BankMovement;
import com.testuality.contalonga.beans.Type;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MovementsPanel extends JPanel {
    private DataModel dataModel;
    private JPanel reloadableDataPanel;

    public MovementsPanel(DataModel dataModel) {
        super();
        this.dataModel = dataModel;

        this.setLayout(new BorderLayout());
        this.reloadableDataPanel = null;
        this.reloadDataModel();
    }

    public void reloadDataModel() {
        if (this.reloadableDataPanel != null) {
            this.remove(this.reloadableDataPanel);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat nf= NumberFormat.getInstance(Locale.UK);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        this.reloadableDataPanel = new JPanel();
        this.reloadableDataPanel.setLayout(new MigLayout());
        if (this.dataModel.getBankMovements() != null) {
            for (BankMovement movement : this.dataModel.getBankMovements()) {
                //this.reloadableDataPanel.add(new JLabel(movement.getId()));
                this.reloadableDataPanel.add(new JLabel(sdf.format(movement.getDate().getTime())));
                this.reloadableDataPanel.add(new JLabel(movement.getConcept()));
                this.reloadableDataPanel.add(new JLabel(nf.format(movement.getAmount())), "right, wrap");
            }
        }
        this.add(this.reloadableDataPanel, BorderLayout.CENTER);
        this.repaint();
    }
}
