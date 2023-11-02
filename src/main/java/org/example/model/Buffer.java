package org.example.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Buffer implements Serializable {
    private static final int MAX_CAPACITY = 100;

    private Queue<Item> buffer = new LinkedList<Item>();
    private PropertyChangeSupport support = new PropertyChangeSupport(this);


    //Lägger till ett item i bufferten, med notifiering till lyssnare.
    public synchronized void add(Item item) {
        if (buffer.size() < MAX_CAPACITY) {
            int oldSize = buffer.size();
            buffer.add(item);
            support.firePropertyChange("size", oldSize, buffer.size());
            System.out.println("Buffer list: " + buffer);
            notify();
        } else {
            System.out.println("Buffer is full. Max capacity reached.");
        }
    }


    // Tar bort och returnerar ett item från bufferten.
    public synchronized Item remove() {
        while (buffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int oldSize = buffer.size();
        Item item = buffer.remove();
        support.firePropertyChange("size", oldSize, buffer.size());
        return item;
    }

// Hämtar nuvarande storlek på bufferten.
    public synchronized int getSize() {
        return buffer.size();
    }


    // Lyssnare som kommer att notifieras om förändringar i bufferten.

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }


}
