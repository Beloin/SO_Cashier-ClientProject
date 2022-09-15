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
     * Synchronous method, updates second per second
     *
     * @param from Start Position
     * @param to   End position
     */
    @Override
    public void walk(Position from, Position to) {
        // In our ~game~ everyone will walk only in horizontal or vertical.
        // We arbitrarily start by the vertical

        float verticalStart = from.getY();
        float verticalEnd = to.getY();

        float currentVerticalPosition = verticalStart;
        while (currentVerticalPosition <= verticalEnd) {
            currentVerticalPosition += walkSpeed / blockSize;
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
            currentHorizontalPosition += walkSpeed / blockSize;
            try {
                Thread.sleep(worldTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
