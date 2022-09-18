package com.beloin.so_cashierclientproject.models.plain;

public class Position {
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    private float x;
    private float y;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Position appendX(float x) {
        this.x += x;
        return this;
    }

    public Position appendY(float y) {
        this.y += y;
        return this;
    }

    public Position subtractX(float x) {
        return appendX(-x);
    }

    public Position subtractY(float y) {
        return appendY(-y);
    }

    public static Position of(Position p) {
        return new Position(p.getX(), p.getY());
    }
}
