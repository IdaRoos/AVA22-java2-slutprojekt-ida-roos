package org.example.controller;

import org.example.model.Buffer;
import org.example.model.Producer;
import org.example.model.Consumer;
import org.example.view.ProductionView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements PropertyChangeListener {
    private Buffer buffer;
    private List<Producer> producers;
    private List<Consumer> consumers;
    private ProductionView view;
    private Random random;

    public Controller(Buffer buffer, ProductionView view) {
        this.buffer = buffer;
        this.producers = new ArrayList<>();
        this.consumers = new ArrayList<>();
        this.view = view;
        this.random = new Random();

        // Registrera Controller-klassen som lyssnare på Buffer
        this.buffer.addPropertyChangeListener(this);

        // Lägg till action listeners för knapparna i vyn
        view.addAddButtonListener(new AddButtonListener());
        view.addRemoveButtonListener(new RemoveButtonListener());

        // Starta ett slumpmässigt antal konsumenter när programmet börjar
        startRandomConsumers();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("size".equals(evt.getPropertyName())) {
            System.out.println("Nu kör propertychange");
            int currentSize = (int) evt.getNewValue(); // Nuvarande storlek
            int referenceSize = 100; // Referensstorlek

            System.out.println("Current size: "+ currentSize + " Reference size: " + referenceSize);
            view.updateProgressBar(currentSize, referenceSize);
        }
    }




    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addProducer();
        }
    }

    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            removeProducer();
        }
    }

    public void addProducer() {
        Producer producer = new Producer(buffer);
        producers.add(producer);
        new Thread(producer).start();
    }

    public void removeProducer() {
        if(!producers.isEmpty()) {
            Producer p = producers.remove(producers.size()-1);
            p.stopRunning();
            System.out.println("removeProducer kallad");
        }
    }

    private void addConsumer() {
        if(consumers.size() < 15) { // Maximala gränsen är 15 konsumenter
            Consumer consumer = new Consumer(buffer);
            consumers.add(consumer);
            new Thread(consumer).start();
            view.appendToLog("En konsument har lagts till.");
        }
    }

    private void startRandomConsumers() {
        int randomConsumerCount = 3 + random.nextInt(13); // Slumpmässigt värde mellan 3 och 15
        for (int i = 0; i < randomConsumerCount; i++) {
            addConsumer();
        }
    }



    public int getBufferSize() {
        return buffer.getSize();
    }
}
