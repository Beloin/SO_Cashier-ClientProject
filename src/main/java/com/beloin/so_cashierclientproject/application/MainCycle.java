package com.beloin.so_cashierclientproject.application;


import java.util.List;

import static com.beloin.so_cashierclientproject.config.GlobalConfiguration.minimumWorldTime;

class MainCycle extends Thread {

    MainCycle(List<PositionedJavaFXImpl> fxList) {
        this.positionedList = fxList;
    }

    private final List<PositionedJavaFXImpl> positionedList;

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(minimumWorldTime);
                updatePositionedItems();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePositionedItems() {
        for (PositionedJavaFXImpl positionedJavaFX : positionedList) {
            updatePositioned(positionedJavaFX);
        }
    }

    void updatePositioned(PositionedJavaFXImpl positionedJavaFX) {
        positionedJavaFX.updatePosition();
    }
}