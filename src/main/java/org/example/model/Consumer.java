package org.example.model;

public class Consumer implements Runnable {
    Buffer buffer = null;
    boolean isRunning = true;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        while(isRunning) {

            try {
                Thread.sleep(1000 + (long) (Math.random() * 9000));
                System.out.println("Consumed: " + buffer.remove());


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void stopRunning() {
        isRunning = false;
    }

}
