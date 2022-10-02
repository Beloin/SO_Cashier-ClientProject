package com.beloin.so_cashierclientproject;

import com.beloin.so_cashierclientproject.application.CashierAndClientGame;
import com.beloin.so_cashierclientproject.application.InitialMenu;
import com.beloin.so_cashierclientproject.application.MainCycle;
import com.beloin.so_cashierclientproject.application.model.CashierImageView;
import com.beloin.so_cashierclientproject.application.model.ClientImageView;
import com.beloin.so_cashierclientproject.application.utils.ConcurrencyPositionedList;
import com.beloin.so_cashierclientproject.config.GlobalConfiguration;
import com.beloin.so_cashierclientproject.models.*;
import com.beloin.so_cashierclientproject.models.plain.Position;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene2 = new Scene(root, 1000, 1000);

        stage.setTitle("Cashier and Clients");
        stage.setScene(scene2);
        stage.show();

        InitialMenu mn = new InitialMenu(root);
        mn.buildMenu(r -> {
            StackPane stackPane = new StackPane();

            Button startButton = new Button("Start Button");
            Button addClient = new Button("Add Client");

            HBox buttonLayout = new HBox();

            buttonLayout.setLayoutX(12);
            buttonLayout.setSpacing(10);

            buttonLayout.getChildren().add(startButton);
            buttonLayout.getChildren().add(addClient);

            stackPane.getChildren().add(buttonLayout);
            root.getChildren().add(stackPane);
            CashierAndClientGame game = new CashierAndClientGame(r, scene2, root);
            addClient.setOnAction(a -> {
                try {
                    game.createClient(5);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            startButton.setOnAction(a -> {
                game.start();
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}