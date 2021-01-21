package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;
import com.testuality.contalonga.mongodb.Mongodb;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MenuBar extends JMenuBar {

    private JContalonga app;
    private JMenuItem newTypeMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem newExpenseMenuItem;
    private JMenuItem reportsMenuItem;

    public MenuBar(JContalonga jContalonga) {
        super();
        this.app = jContalonga;
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open...");
        this.saveMenuItem = new JMenuItem("Save");
        this.saveMenuItem.setEnabled(false);
        this.saveAsMenuItem = new JMenuItem("Save as...");
        this.saveAsMenuItem.setEnabled(false);
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        this.add(fileMenu);

        JMenu viewMenu = new JMenu("View");
        JMenuItem movementsMenuItem = new JMenuItem("Bank movements");
        JMenuItem expensesMenuItem = new JMenuItem("Expenses");
        JMenuItem typesMenuItem = new JMenuItem("Types");

        this.reportsMenuItem = new JMenuItem("Reports");
        this.reportsMenuItem.setEnabled(false);
        viewMenu.add(movementsMenuItem);
        viewMenu.add(expensesMenuItem);
        viewMenu.add(typesMenuItem);
        viewMenu.addSeparator();
        viewMenu.add(this.reportsMenuItem);
        this.add(viewMenu);

        JMenu actionsMenu = new JMenu("Actions");
        this.newTypeMenuItem = new JMenuItem("New type/subtype");
        this.newTypeMenuItem.setEnabled(false);
        actionsMenu.add(this.newTypeMenuItem);
        this.newExpenseMenuItem = new JMenuItem("New expense");
        this.newExpenseMenuItem.setEnabled(false);
        actionsMenu.add(this.newExpenseMenuItem);
        this.add(actionsMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About...");
        helpMenu.add(aboutMenuItem);
        this.add(helpMenu);

        exitMenuItem.addActionListener(new ExitActionListener(this.app));
        openMenuItem.addActionListener(new OpenFileActionListener(this.app));
        saveMenuItem.addActionListener(new SaveActionListener(this.app));
        saveAsMenuItem.addActionListener(new SaveAsActionListener(this.app));

        movementsMenuItem.addActionListener(new ViewMovementsActionListener(this.app));
        expensesMenuItem.addActionListener(new ViewExpensesActionListener(this.app));
        typesMenuItem.addActionListener(new ViewTypesActionListener(this.app));
        this.reportsMenuItem.addActionListener(new ReportsActionListener());
        this.newTypeMenuItem.addActionListener(new NewTypeActionListener(this.app));
        this.newExpenseMenuItem.addActionListener((new NewExpenseActionListener(this.app)));

        aboutMenuItem.addActionListener(new AboutActionListener(this.app));
    }

    public void enableNewTypeMenuItem(boolean enabled) {
        this.newTypeMenuItem.setEnabled(enabled);
        this.newExpenseMenuItem.setEnabled(enabled);

        this.reportsMenuItem.setEnabled(enabled);
    }

    public void enableSavesMenuItems(boolean enabled) {
        this.saveMenuItem.setEnabled(enabled);
        this.saveAsMenuItem.setEnabled(enabled);
    }

    class ReportsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            app.showReportsForm();
        }
    }

    class NewExpenseActionListener implements ActionListener {
        private JContalonga app;

        public NewExpenseActionListener(JContalonga app) {
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.app.showNewExpenseDialog();
        }
    }

    class NewTypeActionListener implements ActionListener {
        private JContalonga app;

        public NewTypeActionListener(JContalonga app) {
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.app.showNewTypeForm();
        }
    }

    class ViewMovementsActionListener implements ActionListener {
        private JContalonga app;

        public ViewMovementsActionListener(JContalonga app) {
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.app.showBankMovements();
        }
    }

    class ViewExpensesActionListener implements ActionListener {
        private JContalonga app;

        public ViewExpensesActionListener(JContalonga app) {
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.app.showExpenses();
        }
    }

    class ViewTypesActionListener implements ActionListener {
        private JContalonga app;

        public ViewTypesActionListener(JContalonga app) {
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.app.showTypes();
        }
    }

    class SaveActionListener implements ActionListener {
        private JContalonga app;

        public SaveActionListener(JContalonga app) {
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            GregorianCalendar now = new GregorianCalendar();
            now.setTimeInMillis(System.currentTimeMillis());
            String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(now.getTime());
            File dir = this.app.getWorkingDirectory();

            File file = Path.of(dir.getPath(), "contalonga-" + dateStr + ".json").toFile();
            System.out.println("Saving to file " + file.getPath());
            this.app.saveDataToFile(file);
        }
    }

    class SaveAsActionListener implements ActionListener {
        private JContalonga app;

        public SaveAsActionListener(JContalonga app) {
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogTitle("Save file of data");
            chooser.setMultiSelectionEnabled(false);
            chooser.setCurrentDirectory(this.app.getWorkingDirectory());
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JSON file", "json", "JSON");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to save this file: " +
                        chooser.getSelectedFile().getName());
                File file = chooser.getSelectedFile();
                File dir = file.getParentFile();
                if (dir.isDirectory()) {
                    this.app.setWorkingDirectory(dir);
                }
                this.app.saveDataToFile(file);
            }
        }
    }

    class OpenFileActionListener implements ActionListener {
        private JContalonga app;

        public OpenFileActionListener(JContalonga app) {
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogTitle("Open file of data");
            chooser.setMultiSelectionEnabled(false);
            chooser.setCurrentDirectory(this.app.getWorkingDirectory());
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JSON file", "json", "JSON");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getPath());
                File file = chooser.getSelectedFile();
                File dir = file.getParentFile();
                if (dir.isDirectory()) {
                    this.app.setWorkingDirectory(dir);
                }
                this.app.readDataFromFile(file);
            }
        }
    }

    class ExitActionListener implements ActionListener {
        private JContalonga app;

        public ExitActionListener(JContalonga app) {
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.app.exit();
        }
    }

    class AboutActionListener implements ActionListener {
        private JContalonga app;

        public AboutActionListener(JContalonga app) {
            this.app = app;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(this.app, "Version " + JContalonga.getVersionNumber() + " of date " +
                    JContalonga.getVersionDate(), "About JContalonga", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

