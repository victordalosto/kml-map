package com.dnit.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.dnit.kml.GenerateKMLMap;

public class Gui extends JFrame {

    private JPanel panel = new JPanel();
    private JButton button = new JButton("Search");
    private static ImageIcon img = new ImageIcon("assets\\img\\google-earth-icon.jpg");

    private GenerateKMLMap controllerKML;


    public Gui(GenerateKMLMap controllerKML) {
        this.controllerKML = controllerKML;
        setTitle("Mapear");
        addButtonEvent(button);
        addElements();
        setupGUILayout();
    }



    /* Add Button Event to open a .csv file */
    private void addButtonEvent(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.showOpenDialog(chooser);
                    chooser.setVisible(true);
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        controllerKML.generate(path);
                    } catch (RuntimeException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Generated KML");
                } catch (NullPointerException exc) {
                    System.out.println("Import canceled");
                }
    }});}



    /* Add elements to JPanel */
    private void addElements() {
        panel.setBackground(Color.WHITE);
        panel.add(button);
        getContentPane().add(panel);
    }



    /* Defines Gui Layout */
    private void setupGUILayout() {
        setSize(250, 70);
        setResizable(false);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setIconImage(img.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

}
