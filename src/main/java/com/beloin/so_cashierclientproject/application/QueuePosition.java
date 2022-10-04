package com.beloin.so_cashierclientproject.application;

import com.beloin.so_cashierclientproject.models.plain.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QueuePosition {
    private static final int roomSize = 18;
    private final static List<Position> spaces = new ArrayList<>(roomSize);
    public QueuePosition() {
        // TODO: CREATE MATRIX

        for (int i = 0; i < 6; i++) {
            spaces.add(new Position(60 + 60*i, 50));
        }

        for (int i = 0; i < 6; i++) {
            spaces.add(new Position(60 + 60*i, 100));
        }

        for (int i = 0; i < 6; i++) {
            spaces.add(new Position(60 + 60*i, 150));
        }

        for (int i = 0; i < 6; i++) {
            spaces.add(new Position(60 + 60*i, 200));
        }


    }

    private final Random random = new Random();
    public Position getNextPosition() {
        int pos = Math.abs(random.nextInt() % roomSize);
        return  spaces.get(pos);
    }
}
