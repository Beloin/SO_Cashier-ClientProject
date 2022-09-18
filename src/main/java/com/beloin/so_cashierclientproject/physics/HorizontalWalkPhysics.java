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
        float blocksPerTime = walkSpeed / blockSize;

        boolean isVerticalForward = verticalStart <= verticalEnd;
        verticalForward(from, verticalStart, verticalEnd, blocksPerTime);

        float horizontalStart = from.getX();
        float horizontalEnd = to.getX();
        boolean isHorizontalForward = verticalStart <= verticalEnd;

        horizontalForward(from, blocksPerTime, horizontalStart, horizontalEnd);

        System.out.println("Finished Walking... " + from + " -> " + to);
    }

    private void horizontalForward(Position from, float blocksPerTime, float horizontalStart, float horizontalEnd) {
        float currentHorizontalPosition = horizontalStart;
        while (currentHorizontalPosition <= horizontalEnd) {
            from.setX(from.getX() + blocksPerTime);
            currentHorizontalPosition += blocksPerTime;
            try {
                Thread.sleep(worldTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void verticalForward(Position from, float verticalStart, float verticalEnd, float blocksPerTime) {
        float currentVerticalPosition = verticalStart;
        while (currentVerticalPosition <= verticalEnd) {
            from.setY(from.getY() + blocksPerTime);
            currentVerticalPosition += blocksPerTime;
            try {
                Thread.sleep(worldTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void verticalBackwards(Position from, float verticalStart, float verticalEnd, float blocksPerTime){
        float currentVerticalPosition = verticalStart;
        while (currentVerticalPosition > verticalEnd) {
            from.setY(from.getY() - blocksPerTime);
            currentVerticalPosition -= blocksPerTime;
            try {
                Thread.sleep(worldTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void horizontalBackwards() {}
}
