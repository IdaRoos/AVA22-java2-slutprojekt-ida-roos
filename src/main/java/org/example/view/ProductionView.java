package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class ProductionView {

    private JFrame frame;
    private JButton addButton, removeButton;
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JLabel unitsLabel;

    public ProductionView() {
        // Skapar huvudfönstret
        frame = new JFrame("Produktionsregulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Skapar knapparna för att lägga till/ta bort arbetare
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("+");
        removeButton = new JButton("-");
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        // Skapar loggområdet
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        // Skapar progressbaren
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0); // Startar på 0%
        progressBar.setStringPainted(true);

        // Skapar enhetsmärkning
        unitsLabel = new JLabel("Antal tillgängliga enheter:");

        // Gruppera progressbaren och etiketten i en panel
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.add(unitsLabel, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.CENTER);

        // Lägger till komponenter till huvudfönstret
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(progressPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);
    }

    public void show() {
        frame.setVisible(true);
    }

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

//            progressBar.repaint();


        });
    }

    public void appendToLog(String message) {
        logArea.append(message + "\n");
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }
}
