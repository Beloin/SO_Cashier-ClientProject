package com.beloin.so_cashierclientproject.models;

import com.beloin.so_cashierclientproject.models.plain.Position;
import com.beloin.so_cashierclientproject.physics.HorizontalWalkPhysics;
import com.beloin.so_cashierclientproject.physics.WalkPhysics;

import java.util.concurrent.Semaphore;

public class ClientThread extends Thread implements Client {
    public interface Callback {
        void handle();
    }

    private Callback onQueueArrivalCallback;
    private Callback onQueueWayCallback;

    private Callback onFinishCallback;

    public void setOnQueueArrivalCallback(Callback onQueueArrivalCallback) {
        this.onQueueArrivalCallback = onQueueArrivalCallback;
    }

    public void setOnQueueWayCallback(Callback onQueueWayCallback) {
        this.onQueueWayCallback = onQueueWayCallback;
    }

    public void setOnFinishCallback(Callback onFinishCallback) {
        this.onFinishCallback = onFinishCallback;
    }

    public Position getQueuePosition() {
        return queuePosition;
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
    // TODO: Change to static array using clients id
    private final Semaphore ownSemaphore;
    private final Semaphore publicClientsSemaphore;
    private final Semaphore publicCashierSemaphore;
    private final ConcurrentClientQueue queue;

    private final Position position;
    private final Position queuePosition;
    // TODO: USE WALK ANIMATIONS
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

    public int getClientId() {
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
        if (onQueueWayCallback != null) {
            onQueueWayCallback.handle();
        }

        walkPhysics.walk(position, queuePosition);

        if (onQueueArrivalCallback != null) {
            onQueueArrivalCallback.handle();
        }
    }

    private void doWork() {
        try {
            // TODO: DO SOMETHING INSTEAD OF SLEEP
            // TODO: IMPLEMENT WITH CALLBACKS OR SOMETHING LIKE STRATEGY
            Thread.sleep(attendantSeconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void goToCashier() {
        walkPhysics.walk(position, cashier.getPosition());
    }

    public Position getPosition() {
        return position;
    }
}
