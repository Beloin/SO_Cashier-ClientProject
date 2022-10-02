package com.beloin.so_cashierclientproject.application;

import com.beloin.so_cashierclientproject.application.model.CashierImageView;
import com.beloin.so_cashierclientproject.application.model.ClientImageView;
import com.beloin.so_cashierclientproject.application.utils.ConcurrencyPositionedList;
import com.beloin.so_cashierclientproject.config.GlobalConfiguration;
import com.beloin.so_cashierclientproject.models.CashierThread;
import com.beloin.so_cashierclientproject.models.ClientThread;
import com.beloin.so_cashierclientproject.models.ConcurrentClientQueue;
import com.beloin.so_cashierclientproject.models.plain.Position;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class CashierAndClientGame {
    final ConcurrentClientQueue clientQueue = new ConcurrentClientQueue();
    final ConcurrencyPositionedList positionedList = new ConcurrencyPositionedList();
    private final int cashierCount;
    private final Random random = new Random();
    private int clientIdCounter = 0;

    private final MainCycle mainCycle;
    private final QueuePosition queuePosition = new QueuePosition();

    final Semaphore publicClientsSemaphore;
    final Semaphore publicCashiersSemaphore;

    private final Scene scene;
    private final Group root;

    public CashierAndClientGame(int cashierCount, Scene scene, Group root) throws FileNotFoundException {
        this.cashierCount = cashierCount;
        publicClientsSemaphore = new Semaphore(0);
        publicCashiersSemaphore = new Semaphore(cashierCount);
        mainCycle = new MainCycle(positionedList);
        Node baseNode = setupBaseNode();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(baseNode);
        root.getChildren().add(stackPane);

        this.scene = scene;
        this.root = root;

        createCashiers();
    }

    public void start() {
        mainCycle.start();
    }


    private void createCashiers() throws FileNotFoundException {
        String cashierPath = GlobalConfiguration.imagePath + "cashier.png";
        int y = 0;
        for (int i = 0; i < cashierCount; i++) {
            CashierThread cashier = new CashierThread(
                    new Position(450, y += 100), publicClientsSemaphore,
                    publicCashiersSemaphore, clientQueue
            );

            CashierImageView cashierImageView = new CashierImageView(cashier, cashierPath);

            // TODO: Add this ?
//            positionedList.add(cashierImageView);

            this.root.getChildren().add(cashierImageView.getView());
        }
    }

    public synchronized void createClient(int attendmentTime) throws FileNotFoundException {
        ClientThread client = new ClientThread(
                clientIdCounter++,
                new Position(0, 0), queuePosition.getNextPosition(),
                publicClientsSemaphore,
                publicCashiersSemaphore,
                clientQueue, attendmentTime
        );

        String clientPath;
        if (random.nextFloat() >= 0.5) {
            clientPath = GlobalConfiguration.imagePath + "woman.png";
        } else {
            clientPath = GlobalConfiguration.imagePath + "man.png";
        }

        ClientImageView clientImageView = new ClientImageView(client, clientPath);
        positionedList.add(clientImageView);

        this.root.getChildren().add(clientImageView.getView());
    }

    private static Node setupBaseNode() {
        Rectangle baseRect = new Rectangle();
        baseRect.setFill(Paint.valueOf("yellow"));
        baseRect.setHeight(1000);
        baseRect.setWidth(1000);
        return baseRect;
    }
}
