package com.beloin.so_cashierclientproject.application;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InitialMenu {
    private final Group parent;

    InitialMenu(Group parent) {
        this.parent = parent;
    }


    private boolean hasBeenBuild = false;
    Label label1;
    TextField textField;
    VBox vBox;
    HBox hBox;

    public void buildMenu() {
        label1 = new Label("Quantidade de Caixas");
        textField = new TextField();
        hBox = new HBox();
        vBox = new VBox();
        vBox.getChildren().addAll(label1, textField);
        vBox.setSpacing(10);

        hBox.getChildren().add(vBox);

        hBox.setSpacing(10);

        hBox.getChildren().add(vBox);

        hasBeenBuild = true;
        parent.getChildren().add(vBox);
    }

    public String removeMenu() {
        if (!hasBeenBuild) return "";
        parent.getChildren().remove(vBox);
        return textField.getText();
    }
}
