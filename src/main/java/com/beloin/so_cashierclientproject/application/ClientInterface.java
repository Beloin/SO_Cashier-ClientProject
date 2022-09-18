package com.beloin.so_cashierclientproject.application;

import com.beloin.so_cashierclientproject.models.ClientThread;
import com.beloin.so_cashierclientproject.models.plain.Position;
import javafx.scene.shape.Rectangle;


import static com.beloin.so_cashierclientproject.config.GlobalConfiguration.minimumWorldTime;

public class ClientInterface extends Thread {
    private final Rectangle rectangle;
    private final ClientThread client;

    public ClientInterface(ClientThread client) {
        this.client = client;
        this.rectangle = new Rectangle();
        rectangle.setX(client.getPosition().getX());
        rectangle.setY(client.getPosition().getY());
        rectangle.setWidth(20);
        rectangle.setHeight(20);
    }

    // TODO: Set this work inside a new class

    @Override
    public void run() {
        try {
            while (true){
                Thread.sleep(minimumWorldTime);
                updatePosition();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void start() {
        client.start();
        super.start();
    }

    private void updatePosition() {
        Position p = this.client.getPosition();
        rectangle.setX(p.getX());
        rectangle.setY(p.getY());
    }

    public void setX(float x) {
        this.client.getPosition().setX(x);
        rectangle.setX(x);
    }

    public void setY(float y) {
        this.client.getPosition().setY(y);
        rectangle.setY(y);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
