package com.beloin.so_cashierclientproject.application;


import com.beloin.so_cashierclientproject.application.model.PositionedView;
import com.beloin.so_cashierclientproject.application.utils.ConcurrencyPositionedList;

public class MainCycle extends Thread {

    public MainCycle(ConcurrencyPositionedList fxList) {
        this.positionedList = fxList;
    }

    private final ConcurrencyPositionedList positionedList;

    @Override
    public void run() {
        while (true) {
            try {
//                Thread.sleep(minimumWorldTime);
                updatePositionedItems();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void updatePositionedItems() {
        for (PositionedView positioned : positionedList.getList()) {
            updatePositioned(positioned);
        }
    }

    void updatePositioned(PositionedView positioned) {
        positioned.updatePosition();
    }
}