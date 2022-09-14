package com.beloin.so_cashierclientproject.models;

import com.beloin.so_cashierclientproject.models.plain.Position;

import java.util.concurrent.Semaphore;

public class CashierThread extends Thread implements Cashier {

    public CashierThread(Semaphore publicClientsSemaphore, Semaphore publicCashierSemaphore, ConcurrentClientQueue queue) {
        this.publicClientsSemaphore = publicClientsSemaphore;
        this.publicCashierSemaphore = publicCashierSemaphore;
        this.queue = queue;
    }

    private final Position position;

    private final Semaphore publicClientsSemaphore;
    private final Semaphore publicCashierSemaphore;
    private final ConcurrentClientQueue queue;

    @Override
    public void run() {
        publicClientsSemaphore.acquireUninterruptibly();
        Client c = queue.poll();
        c.setCashier(this);
        int attSeconds = c.getAttendantSeconds();
        Semaphore clientSemaphore = c.getOwnSemaphore();

        publicCashierSemaphore.release();

        clientSemaphore.acquireUninterruptibly();

        doWork(attSeconds);
    }

    private void doWork(int attSeconds) {
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
