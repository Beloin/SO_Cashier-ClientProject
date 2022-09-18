package com.beloin.so_cashierclientproject.physics;

import com.beloin.so_cashierclientproject.config.GlobalConfiguration;
import com.beloin.so_cashierclientproject.models.plain.Position;

public class HorizontalWalkPhysics implements WalkPhysics {
    public HorizontalWalkPhysics(float walkSpeed, float blockSize, int worldTime) {
        this.walkSpeed = walkSpeed;
        this.blockSize = blockSize;
        this.worldTime = worldTime;
    }

    public static HorizontalWalkPhysics DefaultFactory() {
        return new HorizontalWalkPhysics(GlobalConfiguration.speed, GlobalConfiguration.blockSize, GlobalConfiguration.worldTime);
    }

    private final float walkSpeed;
    private final float blockSize;
    private final int worldTime;

    /**
     * Synchronous method, updates `worldTime` per `worldTime`
     *
     * @param from Start Position
     * @param to   End position
     */
    @Override
    public void walk(Position from, Position to) {
        // In our ~game~ everyone will walk only in horizontal or vertical.
        // We arbitrarily start by the vertical
        System.out.println("Walking... " + from + " -> " + to);
        // TODO: BUG: This only works if `to` bigger then `from`
        float verticalStart = from.getY();
        float verticalEnd = to.getY();
        float blocksWalked = walkSpeed / blockSize;

        float currentVerticalPosition = verticalStart;
        while (currentVerticalPosition <= verticalEnd) {
            from.setY(from.getY() + blocksWalked);
            currentVerticalPosition += blocksWalked;
            try {
                Thread.sleep(worldTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        float horizontalStart = from.getX();
        float horizontalEnd = to.getX();
        float currentHorizontalPosition = horizontalStart;
        while (currentHorizontalPosition <= horizontalEnd) {
            from.setX(from.getX() + blocksWalked);
            currentHorizontalPosition += blocksWalked;
            try {
                Thread.sleep(worldTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Finished Walking... " + from + " -> " + to);
    }
}
