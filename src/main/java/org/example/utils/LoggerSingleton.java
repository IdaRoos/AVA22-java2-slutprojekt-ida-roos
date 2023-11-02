package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoggerSingleton {

    // En privat statisk instans av loggaren
    private static final Logger logger = LogManager.getLogger(LoggerSingleton.class);

    private static final PropertyChangeSupport support = new PropertyChangeSupport(logger);


    // Privat konstruktor för att förhindra att andra instanser skapas
    private LoggerSingleton() {}


    // Loggar information och notifierar alla lyssnare med det nya meddelandet
    public static void logInfo(String message) {
        logger.info(message);
        support.firePropertyChange("log", null, message);
    }

    // Lägger till en lyssnare som ska notifieras när en ny loggning sker
    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
