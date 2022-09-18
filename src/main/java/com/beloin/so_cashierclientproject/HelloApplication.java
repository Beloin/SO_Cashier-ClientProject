package com.beloin.so_cashierclientproject;

import com.beloin.so_cashierclientproject.application.MainCycle;
import com.beloin.so_cashierclientproject.application.PositionedRectangular;
import com.beloin.so_cashierclientproject.models.*;
import com.beloin.so_cashierclientproject.models.plain.Position;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class HelloApplication extends Application {

    private int clientIdCounter = 0;
    private Position queuePosition = new Position(250, 250);
    ConcurrentClientQueue clientQueue = new ConcurrentClientQueue();

    // TODO: THIS GENERATES CONCURRENCY PROBLEMS. THIS SHOULD BE USED WITH SEMAPHORES
    // TODO: This will be changed conform we choose the interface method
    List<PositionedRectangular> positionedRectangulars = new ArrayList<>(10);

    int cashierCount = 2;
    Semaphore publicClientsSemaphore = new Semaphore(0);
    Semaphore publicCashiersSemaphore = new Semaphore(cashierCount);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        Group root = new Group();
        Scene scene2 = new Scene(root);

        ClientThread client1 = new ClientThread(
                clientIdCounter++, new Position(0, 0), new Position(250, 250),
                publicClientsSemaphore, publicCashiersSemaphore,
                clientQueue, 5
        );
        ClientThread client2 = new ClientThread(
                clientIdCounter++, new Position(0, 20), new Position(250, 250),
                publicClientsSemaphore, publicCashiersSemaphore,
                clientQueue, 5
        );

        CashierThread cashier = new CashierThread(
                new Position(450, 200), publicClientsSemaphore,
                publicCashiersSemaphore, clientQueue
        );

       CashierThread cashier2 = new CashierThread(
                new Position(450, 170), publicClientsSemaphore,
                publicCashiersSemaphore, clientQueue
        );
       CashierThread cashier3 = new CashierThread(
                new Position(450, 140), publicClientsSemaphore,
                publicCashiersSemaphore, clientQueue
        );


        Node baseNode = setupBaseNode();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(baseNode);
        root.getChildren().add(stackPane);

        PositionedRectangular clientInterface = new PositionedRectangular(client1);
        PositionedRectangular clientInterface2 = new PositionedRectangular(client2);
        PositionedRectangular cashierPositioned = new PositionedRectangular(cashier, "blue");
        PositionedRectangular cashierPositioned2 = new PositionedRectangular(cashier2, "blue");
        PositionedRectangular cashierPositioned3 = new PositionedRectangular(cashier3, "blue");
        positionedRectangulars.add(clientInterface);
        positionedRectangulars.add(clientInterface2);
        positionedRectangulars.add(cashierPositioned);
        positionedRectangulars.add(cashierPositioned2);
        positionedRectangulars.add(cashierPositioned3);

        root.getChildren().add(cashierPositioned.getRectangle());
        root.getChildren().add(cashierPositioned2.getRectangle());
        root.getChildren().add(cashierPositioned3.getRectangle());

        root.getChildren().add(clientInterface.getRectangle());
        root.getChildren().add(clientInterface2.getRectangle());

        MainCycle mainCycle = new MainCycle(positionedRectangulars);

        // Configure Button
        Button startButton = new Button("Start Button");
        Button addClientButton = new Button("Add Client");
        HBox buttonLayout = new HBox();
        buttonLayout.setLayoutX(12);
        buttonLayout.getChildren().add(startButton);
        buttonLayout.getChildren().add(addClientButton);
        stackPane.getChildren().add(buttonLayout);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainCycle.start();
                // Start Clients
                cashier.start();
                cashier2.start();
                cashier3.start();
                client1.start();
                client2.start();
            }
        });

        addClientButton.setOnAction(actionEvent -> {
            ClientThread c = this.createClient(root);
            c.start();
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

    private synchronized ClientThread createClient(Group root) {
        Position initialPosition = new Position(0, 0);
        ClientThread clientThread = new ClientThread(clientIdCounter++, initialPosition, getQueuePosition(),
                publicClientsSemaphore, publicCashiersSemaphore, clientQueue, 2
        );
        PositionedRectangular clientInterface = new PositionedRectangular(clientThread);
        positionedRectangulars.add(clientInterface);
        root.getChildren().add(clientInterface.getRectangle());
        return clientThread;
    }

    private synchronized Position getQueuePosition() {
        int positionAppender = 20 * clientQueue.getSize();
        return Position.of(queuePosition).subtractX(positionAppender);
    }

    public static void main(String[] args) {
        launch(args);
    }
}