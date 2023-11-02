package org.example.model;

import org.example.utils.LoggerSingleton;

import java.io.Serializable;
import java.util.Random;

public class Producer implements Runnable, Serializable {

    Buffer buffer = null;
    boolean isRunning = true;

    Random random = new Random();

    int randomNumber;

    private int itemsProduced = 0;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }


    @Override
    public void run() {

        // Genererar random nummer mellan 1-10 sekunder
        randomNumber = (random.nextInt(10) + 1)*1000;
        LoggerSingleton.logInfo("Produktionsintervall för senast skapade arbetare: " + (randomNumber/1000) + " sekunder.");

        while (isRunning) {
            try {
                Thread.sleep(randomNumber);
                System.out.println("Random producer number: " + randomNumber);

                // Lägger till nytt item i buffer listan
                buffer.add(new Item("" +(int)(Math.random()*100)));
                incrementItemsProduced();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    // Stoppar producenttråden
    public void stopRunning() {
        isRunning = false;
    }

    // Returnerar det totala antalet producerade items
    public synchronized int getItemsProduced() {
        return itemsProduced;
    }


    // Ökar countern för producerade items med ett
    public synchronized void incrementItemsProduced() {
        itemsProduced++;
    }


    // Återställer countern för producerade items till noll.
    public void resetItemsProduced() {
        itemsProduced = 0;
    }

}


