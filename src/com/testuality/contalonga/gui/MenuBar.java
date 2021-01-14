package com.testuality.contalonga.gui;

import com.testuality.contalonga.JContalonga;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MenuBar extends JMenuBar {

    JContalonga app;

    public MenuBar(JContalonga jContalonga) {
        super();
        this.app = jContalonga;
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open...");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save as...");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        this.add(fileMenu);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem("About..."));
        this.add(helpMenu);

        exitMenuItem.addActionListener(new ExitActionListener(this.app));
        openMenuItem.addActionListener(new OpenFileActionListener(this.app));
        saveMenuItem.addActionListener(new SaveActionListener(this.app));
        saveAsMenuItem.addActionListener(new SaveAsActionListener(this.app));
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

        File file = Path.of(dir.getPath(), "contalonga-" + dateStr+".json").toFile();
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
                    chooser.getSelectedFile().getName());
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
