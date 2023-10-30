package org.example.model;

public class Producer implements Runnable {

    Buffer buffer = null;
    boolean isRunning = true;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

//	public void start() {
//this.start();
//	}

    @Override
    public void run() {

        while (isRunning) {
            try {
                Thread.sleep(1000 + (long) (Math.random() * 9000));

                buffer.add(new Item("" +(int)(Math.random()*100)));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void stopRunning() {
        isRunning = false;
    }

}
