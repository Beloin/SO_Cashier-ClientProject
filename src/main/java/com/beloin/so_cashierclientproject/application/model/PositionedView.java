package com.beloin.so_cashierclientproject.application.model;

import com.beloin.so_cashierclientproject.models.plain.Position;

public interface PositionedView<T> {
    void updatePosition();
    T getView();
    Position getPosition();
}