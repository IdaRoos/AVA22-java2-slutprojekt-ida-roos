package org.example;

import org.example.controller.Controller;
import org.example.model.Buffer;
import org.example.view.ProductionView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        Buffer buffer = new Buffer();

        ProductionView view = new ProductionView();

        // Skapa en ny controller och skicka buffer och view som argument
        Controller controller = new Controller(buffer, view);

        // Visa användargränssnittet
        javax.swing.SwingUtilities.invokeLater(() -> {
            view.show();
        });
    }
}
