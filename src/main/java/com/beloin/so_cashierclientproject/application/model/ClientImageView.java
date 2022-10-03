package com.beloin.so_cashierclientproject.application.model;

import com.beloin.so_cashierclientproject.models.PositionedModel;
import com.beloin.so_cashierclientproject.models.plain.Position;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ClientImageView implements PositionedView<Node> {
    private final ImageView baseImageView;
//    private final ImageView legs;

    private final PositionedModel client;

    public ClientImageView(PositionedModel client, String resourcePath) throws FileNotFoundException {
        // TODO: USE SINGLETON IN ORDER TO REUSE IMAGES
        Image baseImage = new Image(new FileInputStream(resourcePath));
        this.baseImageView = new ImageView(baseImage);
        this.client = client;

        this.baseImageView.setX(client.getPosition().getX());
        this.baseImageView.setY(client.getPosition().getY());

        baseImageView.setPreserveRatio(true);
        baseImageView.setFitHeight(100);
        baseImageView.setFitWidth(100);
    }

    @Override
    public void updatePosition() {
        Position p = this.client.getPosition();
        baseImageView.setX(p.getX());
        baseImageView.setY(p.getY());
    }

    @Override
    public ImageView getView() {
        return baseImageView;
    }

    @Override
    public Position getPosition() {
        return this.client.getPosition();
    }
}