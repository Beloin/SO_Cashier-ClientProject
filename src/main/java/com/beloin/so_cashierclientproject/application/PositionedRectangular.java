package com.beloin.so_cashierclientproject.application;

import com.beloin.so_cashierclientproject.models.PositionedModel;
import com.beloin.so_cashierclientproject.models.plain.Position;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class PositionedRectangular {
    public PositionedRectangular(PositionedModel model) {
        this.model = model;
        this.rectangle = new Rectangle();
        rectangle.setX(model.getPosition().getX());
        rectangle.setY(model.getPosition().getY());
        rectangle.setWidth(20);
        rectangle.setHeight(20);
    }

    public PositionedRectangular(PositionedModel model, String color) {
        this(model);
        rectangle.setFill(Paint.valueOf(color));
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
