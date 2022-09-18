package com.beloin.so_cashierclientproject;

import com.beloin.so_cashierclientproject.application.MainCycle;
import com.beloin.so_cashierclientproject.application.PositionedRectangular;
import com.beloin.so_cashierclientproject.models.Cashier;
import com.beloin.so_cashierclientproject.models.CashierThread;
import com.beloin.so_cashierclientproject.models.ClientThread;
import com.beloin.so_cashierclientproject.models.ConcurrentClientQueue;
import com.beloin.so_cashierclientproject.models.plain.Position;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        Group root = new Group();
        Scene scene2 = new Scene(root);

        // TODO: Update Queue Position dynamically

        ConcurrentClientQueue clientQueue = new ConcurrentClientQueue();
        int cashierCount = 1;
        Semaphore publicClientsSemaphore = new Semaphore(0);
        Semaphore publicCashiersSemaphore = new Semaphore(cashierCount);
        ClientThread client1 = new ClientThread(
                0, new Position(0, 0), new Position(250, 250),
                publicClientsSemaphore, publicCashiersSemaphore,
                clientQueue, 5
        );
        ClientThread client2 = new ClientThread(
                0, new Position(0, 20), new Position(250, 250),
                publicClientsSemaphore, publicCashiersSemaphore,
                clientQueue, 5
        );

        CashierThread cashier = new CashierThread(
                new Position(450, 200), publicClientsSemaphore,
                publicCashiersSemaphore, clientQueue
        );


        Node baseNode = setupBaseNode();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(baseNode);
        root.getChildren().add(stackPane);

        List<PositionedRectangular> positionedRectangulars = new ArrayList<>(2);
        PositionedRectangular clientInterface = new PositionedRectangular(client1);
        PositionedRectangular clientInterface2 = new PositionedRectangular(client2);
        PositionedRectangular cashierPositioned = new PositionedRectangular(cashier, "blue");
        positionedRectangulars.add(clientInterface);
        positionedRectangulars.add(clientInterface2);
        positionedRectangulars.add(cashierPositioned);

        root.getChildren().add(cashierPositioned.getRectangle());

        root.getChildren().add(clientInterface.getRectangle());
        root.getChildren().add(clientInterface2.getRectangle());

        MainCycle mainCycle = new MainCycle(positionedRectangulars);

        // Configure Button
        Button startButton = new Button("Start Button");
        startButton.setLayoutX(12);
        stackPane.getChildren().add(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainCycle.start();
                // Start Clients
                cashier.start();
                client1.start();
                client2.start();
            }
        });


        stage.setTitle("Hello!");
        stage.setScene(scene2);
        stage.show();
    }

    private static Node setupBaseNode() {
        Rectangle baseRect = new Rectangle();
        baseRect.setFill(Paint.valueOf("yellow"));
        baseRect.setHeight(500);
        baseRect.setWidth(500);
        return baseRect;
    }

    public static void main(String[] args) {
        launch(args);
    }
}