package com.beloin.so_cashierclientproject.application.utils;

import com.beloin.so_cashierclientproject.application.model.PositionedView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrencyPositionedList {
    private final ConcurrentLinkedQueue<PositionedView> positionedViews;

    public ConcurrencyPositionedList() {
        positionedViews = new ConcurrentLinkedQueue<>();
    }


    public void add(PositionedView v) {
        this.positionedViews.add(v);
    }

    public List<PositionedView> getList() {
        return this.positionedViews.stream().toList();
    }
}
