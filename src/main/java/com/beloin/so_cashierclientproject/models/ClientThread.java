package com.beloin.so_cashierclientproject.models;

import com.beloin.so_cashierclientproject.models.plain.Position;
import com.beloin.so_cashierclientproject.physics.HorizontalWalkPhysics;
import com.beloin.so_cashierclientproject.physics.WalkPhysics;

import java.util.concurrent.Semaphore;

public class ClientThread extends Thread implements Client {
    public interface Callback {
        void handle();
    }

    private Callback onFinishCallback;

    public void setOnFinishCallback(Callback onFinishCallback) {
        this.onFinishCallback = onFinishCallback;
    }

    public ClientThread(
            int clientId,
            Position initialPosition,
            Position queuePosition,
            Semaphore publicClientsSemaphore,
            Semaphore publicCashierSemaphore,
            ConcurrentClientQueue queue,
            int attendmentSeconds
    ) {
        this.clientId = clientId;
        this.publicClientsSemaphore = publicClientsSemaphore;
        this.publicCashierSemaphore = publicCashierSemaphore;
        this.queue = queue;
        this.attendantSeconds = attendmentSeconds;
        this.ownSemaphore = new Semaphore(0);
        position = initialPosition;
        this.queuePosition = queuePosition;
        walkPhysics = HorizontalWalkPhysics.DefaultFactory();
    }

    private Cashier cashier;
    private final int clientId;
    private final Semaphore ownSemaphore;
    private final Semaphore publicClientsSemaphore;
    private final Semaphore publicCashierSemaphore;
    private final ConcurrentClientQueue queue;

    private final Position position;
    private final Position queuePosition;
    private final WalkPhysics walkPhysics;
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

    public Integer getClientId() {
        return clientId;
    }

    @Override
    public void run() {
        this.queue.add(this);
        goToQueue();
        // up == release, down == acquire
        // release "Soltar", acquire "Adquirir"
        publicClientsSemaphore.release();

        do {
            // down(caixas)
            publicCashierSemaphore.acquireUninterruptibly();
            if (!this.hasCashier()) {
                publicCashierSemaphore.release();
            }
        }
        while (!this.hasCashier());

        goToCashier();
        ownSemaphore.release();

        doWork();

        exitCashier();
    }

    private void exitCashier() {
        walkPhysics.walk(position, position.subtractX(25));
        walkPhysics.walk(position, new Position(1500, 1500));

        if (onFinishCallback != null) {
            onFinishCallback.handle();
        }
    }

    private void goToQueue() {
        walkPhysics.walk(position, queuePosition);
    }

    private void doWork() {
        long current = System.currentTimeMillis();
        long lopper = current;
        while (lopper - current < 1000L * attendantSeconds) {
            lopper = System.currentTimeMillis();
            walkPhysics.walk(position, Position.of(position).subtractY(5));
            walkPhysics.walk(position, Position.of(position).appendY(5));
        }
    }

    private void goToCashier() {
        walkPhysics.walk(position, cashier.getPosition());
    }

    public Position getPosition() {
        return position;
    }
}
