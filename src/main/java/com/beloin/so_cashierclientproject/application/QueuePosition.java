package com.beloin.so_cashierclientproject.application;

import com.beloin.so_cashierclientproject.models.plain.Position;

import java.util.ArrayList;
import java.util.List;

public class QueuePosition {
    private static final int roomSize = 3;
    private final static List<Position> spaces = new ArrayList<>(roomSize);
    private final static List<Boolean> usages = new ArrayList<>(roomSize);

    public QueuePosition() {
        // TODO: CREATE MATRIX
        spaces.add(new Position(50, 50));
        spaces.add(new Position(100, 100));
        spaces.add(new Position(150, 150));

        for (int i = 0; i < roomSize; i++) {
            usages.add(false);
        }
    }

    public Position getNextPosition() {
        for (int i = 0; i < roomSize; i++) {
            if (!usages.get(i)) {
                usages.set(i, true);
                return spaces.get(i);
            }
        }

        return new Position(0, 0);
    }
    public void freePosition(Position p) {
        for (int i = 0; i < roomSize; i++) {
            if (spaces.get(i).equals(p)) {
                usages.set(i, false);
            }
        }
    }
}
