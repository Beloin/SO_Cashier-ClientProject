package com.beloin.so_cashierclientproject.physics;

import com.beloin.so_cashierclientproject.application.model.PositionedView;
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
        float blocksPerTime = walkSpeed / blockSize;

        float verticalStart = from.getY();
        float verticalEnd = to.getY();
        boolean isVerticalForward = verticalStart <= verticalEnd;

        if (isVerticalForward && verticalEnd - verticalStart >= 0.000001) {
            verticalForward(from, verticalStart, verticalEnd, blocksPerTime);
        } else {
            verticalBackwards(from, verticalStart, verticalEnd, blocksPerTime);
        }

        float horizontalStart = from.getX();
        float horizontalEnd = to.getX();
        boolean isHorizontalForward = horizontalStart <= horizontalEnd;

        if (isHorizontalForward && horizontalEnd - horizontalStart >= 0.000001) {
            horizontalForward(from, blocksPerTime, horizontalStart, horizontalEnd);
        } else {
            horizontalBackwards(from, blocksPerTime, horizontalStart, horizontalEnd);
        }

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
        from.setX(horizontalEnd);
    }

    private void horizontalBackwards(Position from, float blocksPerTime, float horizontalStart, float horizontalEnd) {
        float currentHorizontalPosition = horizontalStart;
        while (currentHorizontalPosition > horizontalEnd) {
            from.setX(from.getX() - blocksPerTime);
            currentHorizontalPosition -= blocksPerTime;
            try {
                Thread.sleep(worldTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        from.setX(horizontalEnd);
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

        from.setY(verticalEnd);
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
        from.setY(verticalEnd);
    }
}
