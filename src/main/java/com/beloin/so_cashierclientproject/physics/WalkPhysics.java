package com.beloin.so_cashierclientproject.physics;

import com.beloin.so_cashierclientproject.application.model.PositionedView;
import com.beloin.so_cashierclientproject.models.plain.Position;

public interface WalkPhysics {
    void walk(Position from, Position to);
}
