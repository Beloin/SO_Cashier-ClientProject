package com.beloin.so_cashierclientproject.application;


import com.beloin.so_cashierclientproject.application.model.PositionedView;

import java.util.List;

import static com.beloin.so_cashierclientproject.config.GlobalConfiguration.minimumWorldTime;

public class MainCycle extends Thread {

    public MainCycle(List<PositionedView> fxList) {
        this.positionedList = fxList;
    }

    private final List<PositionedView> positionedList;

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
        for (PositionedView positioned : positionedList) {
            updatePositioned(positioned);
        }
    }

    void updatePositioned(PositionedView positioned) {
        positioned.updatePosition();
    }
}