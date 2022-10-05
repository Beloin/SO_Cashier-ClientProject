package com.beloin.so_cashierclientproject.models;

import com.beloin.so_cashierclientproject.models.plain.Position;
import com.beloin.so_cashierclientproject.physics.HorizontalWalkPhysics;
import com.beloin.so_cashierclientproject.physics.WalkPhysics;

import java.util.concurrent.Semaphore;

public class CashierThread extends Thread implements Cashier {
    public interface Callback {
        void handle(int nextClientNumber);
    }


    public CashierThread(
            Position position,
            Semaphore publicClientsSemaphore,
            Semaphore publicCashierSemaphore,
            ConcurrentClientQueue queue
    ) {
        this.position = position;
        this.publicClientsSemaphore = publicClientsSemaphore;
        this.publicCashierSemaphore = publicCashierSemaphore;
        this.queue = queue;
    }

    private final Position position;
    private final Semaphore publicClientsSemaphore;
    private final Semaphore publicCashierSemaphore;
    private final ConcurrentClientQueue queue;

    WalkPhysics walkPhysics = HorizontalWalkPhysics.DefaultFactory();

    @Override
    public void run() {
        while (true) {
            publicClientsSemaphore.acquireUninterruptibly();
            Client c = queue.poll();
            c.setCashier(this);

            publicCashierSemaphore.release();
            int attSeconds = c.getAttendantSeconds();
            Semaphore clientSemaphore = c.getOwnSemaphore();
            clientSemaphore.acquireUninterruptibly();
            doWork(attSeconds);
        }
    }

    private void doWork(int attSeconds) {
        long current = System.currentTimeMillis();
        long lopper = current;
        while (lopper - current < 1000L * attSeconds) {
            lopper = System.currentTimeMillis();
            walkPhysics.walk(position, Position.of(position).appendY(5));
            walkPhysics.walk(position, Position.of(position).subtractY(5));
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
