package com.beloin.so_cashierclientproject.application.utils;

import com.beloin.so_cashierclientproject.application.model.PositionedView;
import javafx.scene.Node;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrencyPositionedList {
    private final ConcurrentLinkedQueue<PositionedView<Node>> positionedViews;

    public ConcurrencyPositionedList() {
        positionedViews = new ConcurrentLinkedQueue<>();
    }


    public void add(PositionedView<Node> v) {
        this.positionedViews.add(v);
    }

    public List<PositionedView<Node>> getList() {
        return this.positionedViews.stream().toList();
    }

    public void remove(PositionedView<Node> obj){
        positionedViews.remove(obj);
    }
}
