package org.example.model;

import java.io.Serializable;
import java.util.Random;

public class Consumer implements Runnable, Serializable {
    Buffer buffer = null;
    boolean isRunning = true;

    Random random = new Random();

    private int itemsConsumed = 0;


    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        // Genererar random nummer mellan 1-10 sekunder
        int randomNumber = (random.nextInt(10) + 1)*1000;

        while(isRunning) {

            try {
                Thread.sleep(randomNumber);
                // Tar bortitem i buffer listan
                System.out.println("Consumed: " + buffer.remove());
                incrementItemsConsumed();



            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Stoppar konsumenttråden
    public void stopRunning() {
        isRunning = false;
    }

    // Returnerar det totala antalet konsumerade items
    public synchronized int getItemsConsumed() {
        return itemsConsumed;
    }


    // Ökar countern för konsumerade items med ett
    public synchronized void incrementItemsConsumed() {
        itemsConsumed++;
    }

    // Återställer countern för konsumerade items till noll.
    public void resetItemsConsumed() {
        itemsConsumed = 0;
    }



}


