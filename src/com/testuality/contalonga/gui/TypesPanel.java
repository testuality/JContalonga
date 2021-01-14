package com.testuality.contalonga.gui;

import com.testuality.contalonga.beans.Subtype;
import com.testuality.contalonga.beans.Type;
import com.testuality.contalonga.model.DataModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class TypesPanel extends JPanel {
    private DataModel dataModel;
    private JPanel reloadableDataPanel;

    public TypesPanel(DataModel dataModel) {
        super();
        this.dataModel = dataModel;

        this.setIgnoreRepaint(false);
        this.setLayout(new BorderLayout());
        this.reloadableDataPanel = null;
        this.reloadDataModel();
    }

    public void reloadDataModel() {
        if (this.reloadableDataPanel != null) {
            this.remove(this.reloadableDataPanel);
        }

        this.reloadableDataPanel = new JPanel();
        this.reloadableDataPanel.setLayout(new MigLayout());
        if (this.dataModel.getTypes() != null) {

            this.reloadableDataPanel.add(new JLabel("Types"), "center");
            this.reloadableDataPanel.add(new JLabel("Subtypes"), "center, wrap");
            for (Type type : this.dataModel.getTypes()) {
                this.reloadableDataPanel.add(new JLabel(type.getName()));
                StringBuffer sb = new StringBuffer();
                for (Subtype subtype : type.getSubtypeList()) {
                    sb.append(subtype.getName()).append(" ");
                }
                this.reloadableDataPanel.add(new JLabel(sb.toString()), "wrap");
            }
        }
        this.add(this.reloadableDataPanel, BorderLayout.CENTER);
        this.repaint();
    }
}
