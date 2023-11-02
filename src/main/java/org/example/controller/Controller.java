package org.example.controller;

import org.example.model.Buffer;
import org.example.model.Producer;
import org.example.model.Consumer;
import org.example.model.ProductionState;
import org.example.utils.LoggerSingleton;
import org.example.view.ProductionView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Controller implements PropertyChangeListener {
    private Buffer buffer;
    private LinkedList<Producer> producers;
    private List<Consumer> consumers;
    private ProductionView view;
    private Random random;

    public Controller(Buffer buffer, ProductionView view) {
        this.buffer = buffer;
        this.producers = new LinkedList<>();
        this.consumers = new ArrayList<>();
        this.view = view;
        this.random = new Random();

        // Registrerar Controller-klassen som lyssnare på Buffer
        this.buffer.addPropertyChangeListener(this);

        // Lägg till action listeners för knapparna i view
        view.addAddButtonListener(new AddButtonListener());
        view.addRemoveButtonListener(new RemoveButtonListener());

        view.addLoadButtonListener(new LoadButtonListener());
        view.addSaveButtonListener(new SaveButtonListener());

        addRandomConsumers();

        // Lyssnare till LoggerSingleton
        LoggerSingleton.addPropertyChangeListener(evt -> {
            if ("log".equals(evt.getPropertyName())) {
                view.insertToLog((String) evt.getNewValue());
            }
        });

        // Kallar på logItemRatios metoden var 10:e sekund
        Timer logTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logItemRatios();
            }
        });
        logTimer.start();

    }


    // Uppdaterar progressbaren och loggar när enheter är under 10 eller över 90
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("size".equals(evt.getPropertyName())) {
            int currentSize = (int) evt.getNewValue();
            int referenceSize = 100;
            int percent = (currentSize * 100) / referenceSize;
            view.updateProgressBar(currentSize, referenceSize);

            if (percent < 10) {
                LoggerSingleton.logInfo("Antalet tillgängliga enheter är lägre än 10 procent");
            } else if (percent > 90) {
                LoggerSingleton.logInfo("Antalet tillgängliga enheter är högre än 90 procent");
            }
        }
    }


    // Inre klass som lyssnar på "add"-knappens klick-event
    // Lägger till en producer när knappen klickas
    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addProducer();
        }
    }
// Inre klass som lyssnar på "remove"-knappens klick-event
    // Tar bort producer när knappen klickas
    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            removeProducer();
        }
    }


    // Inre klass som lyssnar på "load"-knappens klick-event
    // Laddar sparade produktionstillstånd från objektfil när knappen klickas
    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadState("files/production_state.dat");
        }
    }

    // Inre klass som lyssnar på "save"-knappens klick-event
    // Sparar nuvarande produktionstillstånd till objektfil när knappen klickas
    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveCurrentState("files/production_state.dat");
        }
    }


    // Skapar en ny producer
    public void addProducer() {
        Producer producer = new Producer(buffer);
        producers.add(producer);
        new Thread(producer).start();
        LoggerSingleton.logInfo("Totalt antal arbetare: " + producers.size());
        LoggerSingleton.logInfo("En arbetare lades till");
    }


    // Tar bort producer
    public void removeProducer() {
        if(!producers.isEmpty()) {
            producers.removeLast().stopRunning();
            LoggerSingleton.logInfo("Totalt antal arbetare: " + producers.size());
            LoggerSingleton.logInfo("En arbetare togs bort");
        }
    }


    // Lägger till ett randomiserat antal consumers mellan 3-15 till lista
    private void addRandomConsumers() {
        int randomConsumerCount = 3 + random.nextInt(13);
        for (int i = 0; i < randomConsumerCount; i++) {
            Consumer consumer = new Consumer(buffer);
            consumers.add(consumer);
            new Thread(consumer).start();
        }
        System.out.println("Amount of consumers created: " + randomConsumerCount);
    }


    //  beräknar och loggar det genomsnittliga antalet konsumerade items baserat på totalt producerade och konsumerade items
    public void logItemRatios() {
        int totalProduced = 0;
        int totalConsumed = 0;
        double ratio;

        for (Producer producer : producers) {
            totalProduced += producer.getItemsProduced();
            System.out.println("Test totalproduced: " + totalProduced);
        }

        for (Consumer consumer : consumers) {
            totalConsumed += consumer.getItemsConsumed();
            System.out.println("Test totalconsumed: " + totalConsumed);

        }
if(totalConsumed == 0 && totalProduced == 0) {
     ratio = 0;
} else{
    ratio = ((double) totalConsumed / totalProduced) * 100;
}
        LoggerSingleton.logInfo("Genomsnittligt antal konsumerade enheter av totalen (konsumerade/producerade): " + ratio + "%");

        // Nollställer räknarna för producerade och konsumerade items
        for (Producer producer : producers) {
            producer.resetItemsProduced();
        }

        for (Consumer consumer : consumers) {
            consumer.resetItemsConsumed();
        }

    }

    // Sparar producers, consumers, intervaller samt bufferlistan till objektfil
    public void saveCurrentState(String filename) {
        ProductionState state = new ProductionState(producers, consumers, buffer);
        try {
            state.saveStateToFile(state, filename);
            LoggerSingleton.logInfo("Produktionstillstånd sparades till " + filename);
        } catch (IOException e) {
            LoggerSingleton.logInfo("Kunde inte spara tillståndet: " + e.getMessage());
        }
    }


    // Hämtar senast sparade producers, consumers, intervaller samt bufferlistan från objektfil
    public void loadState(String filename) {
        try {
            // Stoppar alla aktiva producent- och konsumenttrådar
            for (Producer producer : producers) {
                producer.stopRunning();
            }
            for (Consumer consumer : consumers) {
                consumer.stopRunning();
            }

            ProductionState loadedState = ProductionState.loadStateFromFile(filename);

            // Uppdaterar nuvarande produktionstillstånd med loadedState
            this.producers = new LinkedList<>(loadedState.getProducers());
            this.consumers = loadedState.getConsumers();
            this.buffer = loadedState.getBuffer();
            this.buffer.addPropertyChangeListener(this);
            int currentSize = this.buffer.getSize();
            int referenceSize = 100;
            view.updateProgressBar(currentSize, referenceSize);


            // Starta nya trådar för varje producer och consumer i de uppdaterade listorna
            for (Producer producer : producers) {
                new Thread(producer).start();
            }
            for (Consumer consumer : consumers) {
                new Thread(consumer).start();
            }

            LoggerSingleton.logInfo("Produktionstillstånd laddades från " + filename);
        } catch (IOException | ClassNotFoundException e) {
            LoggerSingleton.logInfo("Kunde inte ladda tillståndet: " + e.getMessage());
        }
    }





}
