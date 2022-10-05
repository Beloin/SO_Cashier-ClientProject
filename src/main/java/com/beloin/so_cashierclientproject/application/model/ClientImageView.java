package com.beloin.so_cashierclientproject.application.model;

import com.beloin.so_cashierclientproject.models.ClientThread;
import com.beloin.so_cashierclientproject.models.plain.Position;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ClientImageView implements PositionedView<Node> {
    private final ImageView baseImageView;
    private final ClientThread model;
    private final Label clientId;

    public ClientImageView(ClientThread client, String resourcePath) throws FileNotFoundException {
        Image baseImage = new Image(new FileInputStream(resourcePath));
        this.baseImageView = new ImageView(baseImage);
        this.model = client;

        this.baseImageView.setX(client.getPosition().getX());
        this.baseImageView.setY(client.getPosition().getY());

        clientId = new Label("ID: " + (client.getClientId() + 1));
        clientId.setLayoutX(model.getPosition().getX() + 10);
        clientId.setLayoutY(model.getPosition().getY() - 40);

        baseImageView.setPreserveRatio(true);
        baseImageView.setFitHeight(100);
        baseImageView.setFitWidth(100);
    }

    @Override
    public void updatePosition() {
        Position p = this.model.getPosition();
        baseImageView.setX(p.getX());
        baseImageView.setY(p.getY());

        clientId.setLayoutX(model.getPosition().getX() + 10);
        clientId.setLayoutY(model.getPosition().getY() - 40);
    }

    @Override
    public ImageView getView() {
        return baseImageView;
    }

    @Override
    public Node[] getViewArray() {
        return new Node[]{baseImageView, clientId};
    }

    @Override
    public Position getPosition() {
        return this.model.getPosition();
    }
}
