package com.beloin.so_cashierclientproject.application;

import com.beloin.so_cashierclientproject.models.plain.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QueuePosition {
    private static final int roomSize = 6 * 4;
    private final static List<Position> spaces = new ArrayList<>(roomSize);

    private Random random = new Random();

    public QueuePosition() {
        for (int i = 0; i < 6; i++) {
            spaces.add(new Position((60 + 60 * i) + random.nextInt() % 10, 50));
        }

        for (int i = 0; i < 6; i++) {
            spaces.add(new Position((60 + 60 * i) + random.nextInt() % 10, 120));
        }

        for (int i = 0; i < 6; i++) {
            spaces.add(new Position((60 + 60 * i) + random.nextInt() % 10, 190));
        }

        for (int i = 0; i < 6; i++) {
            spaces.add(new Position((60 + 60 * i) + random.nextInt() % 10, 260));
        }


    }

    public Position getNextPosition() {
        int pos = Math.abs(random.nextInt() % roomSize);
        return spaces.get(pos);
    }
}
