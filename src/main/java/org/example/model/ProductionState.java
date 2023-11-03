package org.example.model;

import java.io.*;
import java.util.List;

public class ProductionState implements Serializable {

    private List<Producer> producers;
    private List<Consumer> consumers;
    private Buffer buffer;

    public ProductionState(List<Producer> producers, List<Consumer> consumers, Buffer buffer) {
        this.producers = producers;
        this.consumers = consumers;
        this.buffer = buffer;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public Buffer getBuffer() {
        return buffer;
    }


    // Sparar produktionstillstånd till objektfil
    public static void saveStateToFile(ProductionState state, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(state);
        }
    }

    // Hämtar produktionstillstånd från objektfil
    public static ProductionState loadStateFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (ProductionState) in.readObject();
        }
    }
}
