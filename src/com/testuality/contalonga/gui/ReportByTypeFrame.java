package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.model.DataModel;

import javax.swing.*;
import java.awt.*;

public class ReportByTypeFrame extends JFrame {
    private DataModel dataModel;
    private JContalonga app;

    private JTextField dateField;
    private JComboBox typeCombo;
    private JComboBox subtypeCombo;
    private JTextField conceptField;
    private JTextField amountField;
    private JButton createBtn;

    public ReportByTypeFrame(DataModel dataModel, JContalonga app) {
        super();
        this.dataModel = dataModel;
        this.app = app;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Report by type");
        //this.setAlwaysOnTop(true);
        this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        this.setLocationRelativeTo(this.app);

        this.add(new JLabel("Report by type"));
        this.pack();
    }
}
