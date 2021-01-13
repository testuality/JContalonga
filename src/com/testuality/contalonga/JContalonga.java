package com.testuality.contalonga;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class JContalonga {
    public static void main(String[] args) {
        JSwingGui app = new JSwingGui();
        app.run();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem("About..."));
        menuBar.add(helpMenu);
        return menuBar;
    }

    private JPanel createButtonsContentPanel() {
        JPanel contentPanel = new JPanel();

        JButton btn1 = new JButton("Button 1");
        JButton btn2 = new JButton("Button 2");
        JButton btn3 = new JButton("Button 3");
        JButton btn4 = new JButton("Button 4");
        JButton btn5 = new JButton("Button 5");
        JButton btn6 = new JButton("Button 6");
        JButton btn7 = new JButton("Button 7");
        JButton btn8 = new JButton("Button 8");
        JButton btn9 = new JButton("Button 9");

        contentPanel.setLayout(new MigLayout("debug"));

        contentPanel.add(btn1);
        contentPanel.add(btn2);
        contentPanel.add(btn3, "wrap");

        contentPanel.add(btn4, "");
        contentPanel.add(btn5, "wrap");

        contentPanel.add(btn6, "span, center, wrap");

        contentPanel.add(btn7);
        contentPanel.add(btn8);
        contentPanel.add(btn9);

        return contentPanel;
    }

    private JPanel createEmailContentPanel() {
        // https://youtu.be/sLIveQh2ZUg
        JPanel contentPanel = new JPanel();

        contentPanel.setLayout(new MigLayout("debug"));

        contentPanel.setBorder(BorderFactory.createTitledBorder("New message"));

        contentPanel.add(new JLabel("From:"), "right");
        contentPanel.add(new JTextField(20), "wrap");
        contentPanel.add(new JLabel("To:"), "right");
        contentPanel.add(new JTextField(20), "wrap");
        contentPanel.add(new JLabel("Subject:"), "right");
        contentPanel.add(new JTextField(20), "wrap");
        contentPanel.add(new JLabel("Message:"), "wrap");
        JTextArea textArea = new JTextArea(40,40);
        textArea.setBorder(BorderFactory.createEtchedBorder());
        contentPanel.add(textArea, "span");

        contentPanel.add(new JButton("Send"), "right split span");
        contentPanel.add(new JButton("Cancel"), "right");
        return contentPanel;
    }

    private JPanel createAdressCardContentPanel() {
        // https://youtu.be/hWtiSRnXz3w
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new MigLayout("debug"));

        return contentPanel;
    }

    private void run() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);


        JMenuBar menuBar = this.createMenuBar();

        JPanel contentPanel = this.createEmailContentPanel();

        this.setLayout(new BorderLayout());
        this.add(menuBar, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }
}
