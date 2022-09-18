package com.beloin.so_cashierclientproject.application;


import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.beloin.so_cashierclientproject.config.GlobalConfiguration.minimumWorldTime;

public class MainCycle extends Thread {

    public MainCycle(List<PositionedRectangular> fxList) {
        this.positionedList = fxList;
    }

    private final List<PositionedRectangular> positionedList;

    @Override
    public void run() {
        while (true) {
            try {
                // TODO: This generates problem:
                // TODO: Trying to access positionedList while is being used
                Thread.sleep(minimumWorldTime);
                updatePositionedItems();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void updatePositionedItems() {
        // TODO: Problem with concurrency
        for (PositionedRectangular positioned : positionedList) {
            updatePositioned(positioned);
        }
    }

    void updatePositioned(PositionedRectangular positioned) {
        positioned.updatePosition();
    }
}