package com.beloin.so_cashierclientproject.models;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentClientQueue {
    public ConcurrentClientQueue() {
        this.clients = new ConcurrentLinkedQueue<>();
    }

    private final Queue<Client> clients;


    public Client poll() {
        return clients.poll();
    }

    public void add(Client c) {
        clients.add(c);
    }
}
