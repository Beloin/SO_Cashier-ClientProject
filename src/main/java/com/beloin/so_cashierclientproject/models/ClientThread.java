package com.beloin.so_cashierclientproject.models;

import com.beloin.so_cashierclientproject.models.plain.Position;

import java.util.concurrent.Semaphore;

public class ClientThread extends Thread implements Client {
    public ClientThread(int clientId, Semaphore publicClientsSemaphore, Semaphore publicCashierSemaphore, ConcurrentClientQueue queue, int attendmentSeconds) {
        this.clientId = clientId;
        this.publicClientsSemaphore = publicClientsSemaphore;
        this.publicCashierSemaphore = publicCashierSemaphore;
        this.queue = queue;
        this.attendantSeconds = attendmentSeconds;
        this.ownSemaphore = new Semaphore(0);
    }

    private Cashier cashier;
    private final int clientId;
    // TODO: Change to static array using clients id
    private final Semaphore ownSemaphore;
    private final Semaphore publicClientsSemaphore;
    private final Semaphore publicCashierSemaphore;
    private final ConcurrentClientQueue queue;

    // TODO: How to Make Position?
    private final Position position;
    private final int attendantSeconds;

    @Override
    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    private boolean hasCashier() {
        return this.cashier != null;
    }

    @Override
    public Semaphore getOwnSemaphore() {
        return this.ownSemaphore;
    }

    @Override
    public int getAttendantSeconds() {
        return attendantSeconds;
    }

    @Override
    public void run() {
        this.queue.add(this);
        publicClientsSemaphore.release();

        do publicCashierSemaphore.acquireUninterruptibly();
        while (!this.hasCashier());

        goToCashier();
        ownSemaphore.release();

        doWork();
    }

    private void doWork() {
        int waitFor = attendantSeconds;
    }

    private void goToCashier() {
        Position cashierPosition = this.cashier.getPosition();

    }
}
