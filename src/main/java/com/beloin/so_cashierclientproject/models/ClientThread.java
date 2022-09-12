package com.beloin.so_cashierclientproject.models;

import java.util.concurrent.Semaphore;

public class ClientThread extends Thread implements Client {
    public ClientThread() {
        this.clientSemaphore = new Semaphore(1);
    }

    private Cashier cashier;
    private final Semaphore clientSemaphore;

    @Override
    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    private boolean hasCashier() {
        return this.cashier != null;
    }

    @Override
    public Semaphore getSemaphore() {
        return this.clientSemaphore;
    }

    @Override
    public void run() {

    }
}
