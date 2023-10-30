package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerSingleton {

    // En privat statisk instans av loggaren
    private static final Logger logger = LogManager.getLogger(LoggerSingleton.class);

    // Privat konstruktor för att förhindra att andra instanser skapas
    private LoggerSingleton() {}

    // Offentlig metod för att få logger-instansen
    public static Logger getLogger() {
        return logger;
    }
}
