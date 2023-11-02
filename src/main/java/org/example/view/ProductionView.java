package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class ProductionView {

    private JFrame frame;
    private JButton addButton, removeButton, saveButton, loadButton;

    private JTextArea logArea;
    private JProgressBar progressBar;
    private JLabel unitsLabel;

    public ProductionView() {
        // Skapar huvudfönstret
        frame = new JFrame("Produktionsregulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Skapar knapparna för att lägga till/ta bort producers
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("+");
        removeButton = new JButton("-");
        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        //  Skapar loggen
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(frame.getWidth(), 100));

        // Skapar progressbaren
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0); // Startar på 0%
        progressBar.setStringPainted(true);

        // Skapar enhetsmärkning
        unitsLabel = new JLabel("Antal tillgängliga enheter:");

        // Lägg progressbaren och etiketten i en panel
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.add(unitsLabel, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.CENTER);

        // Lägger till komponenter till huvudfönstret
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(progressPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);
    }


    // visar huvudfönstret
    public void show() {
        frame.setVisible(true);
    }


// Uppdaterar progressbaren baserat på de senaste värdena av tillgängliga items
    public void updateProgressBar(int currentSize, int referenceSize) {
        SwingUtilities.invokeLater(() -> {
            int percent = (int) ((double) currentSize / referenceSize * 100);
            progressBar.setValue(percent);

            if (percent < 50) {
                progressBar.setForeground(Color.RED);
                System.out.println("Nu kör updateProgressBar under 50");
            } else {
                progressBar.setForeground(Color.GREEN);
                System.out.println("Nu kör updateProgressBar över 50");
            }


        });
    }


    // Lägger till ett meddelande i början/överst i loggtextarean.
    public void insertToLog(String message) {
        logArea.insert(message + "\n", 0);
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    public void addLoadButtonListener(ActionListener listener) {
        loadButton.addActionListener(listener);
    }

    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

}
