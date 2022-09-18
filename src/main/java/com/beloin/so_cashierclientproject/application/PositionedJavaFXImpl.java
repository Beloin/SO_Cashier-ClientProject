package com.beloin.so_cashierclientproject.application;

import com.beloin.so_cashierclientproject.models.PositionedModel;
import com.beloin.so_cashierclientproject.models.plain.Position;
import javafx.scene.shape.Rectangle;

public class PositionedJavaFXImpl {
    public PositionedJavaFXImpl(PositionedModel model) {
        this.model = model;
        this.rectangle = new Rectangle();
        rectangle.setX(model.getPosition().getX());
        rectangle.setY(model.getPosition().getY());
        rectangle.setWidth(20);
        rectangle.setHeight(20);
    }

    private final Rectangle rectangle;
    private final PositionedModel model;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void updatePosition() {
        Position p = this.model.getPosition();
        rectangle.setX(p.getX());
        rectangle.setY(p.getY());
    }
}
