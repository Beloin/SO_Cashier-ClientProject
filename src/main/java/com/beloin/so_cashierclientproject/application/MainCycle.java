package com.beloin.so_cashierclientproject.application;


import com.beloin.so_cashierclientproject.application.model.PositionedView;
import com.beloin.so_cashierclientproject.application.utils.ConcurrencyPositionedList;

import java.util.List;

import static com.beloin.so_cashierclientproject.config.GlobalConfiguration.minimumWorldTime;

public class MainCycle extends Thread {

    public MainCycle(ConcurrencyPositionedList fxList) {
        this.positionedList = fxList;
    }

    private final ConcurrencyPositionedList positionedList;

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
        for (PositionedView positioned : positionedList.getList()) {
            updatePositioned(positioned);
        }
    }

    void updatePositioned(PositionedView positioned) {
        positioned.updatePosition();
    }
}