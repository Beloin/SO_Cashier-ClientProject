package com.beloin.so_cashierclientproject.application;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class InitialMenu {
    public interface ReturnInteger {
        void handle(int r) throws FileNotFoundException;
    }

    private final Group parent;

    public InitialMenu(Group parent) {
        this.parent = parent;
    }


    private boolean hasBeenBuild = false;
    Label label1;
    TextField textField;
    VBox vBox;
    HBox hBox;

    public void buildMenu(ReturnInteger returnFunction) {
        label1 = new Label("Quantidade de Caixas");
        textField = new TextField();
        vBox = new VBox();
        vBox.getChildren().addAll(label1, textField);
        vBox.setSpacing(10);

        hBox = new HBox();
        hBox.getChildren().add(vBox);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        Button ok = new Button("Ok!");
        ok.setOnAction(actionEvent -> {
            try {
                removeMenu(returnFunction);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        hBox.getChildren().add(ok);
        hasBeenBuild = true;
        parent.getChildren().add(hBox);
    }

    private void removeMenu(ReturnInteger returnFunction) throws FileNotFoundException {
        if (hasBeenBuild) {
            try {
                int value = Integer.parseInt(textField.getText());
                parent.getChildren().remove(hBox);
                returnFunction.handle(value);
            } catch (NumberFormatException e) {
                System.out.println("Wrong Value");
            }
        }
    }
}
