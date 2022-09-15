package com.beloin.so_cashierclientproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        Button btn = new Button("Say Hello to My Little Friend");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Say Hello my Broda!");
            }
        });

        Group root = new Group();
        root.getChildren().add(btn);
        Scene scene2 = new Scene(root);

        Rectangle rect = new Rectangle();
        rect.setX(10);
        rect.setY(20);
        rect.setWidth(100);
        rect.setHeight(100);
        root.getChildren().add(rect);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setScene(scene2);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}