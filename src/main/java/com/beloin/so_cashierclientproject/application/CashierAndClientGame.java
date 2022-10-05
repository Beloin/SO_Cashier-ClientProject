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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
    private final List<CashierThread> cashiers;

    // TODO: ADD COUNTER

    // TODO: ADD QUEUE TICKET

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
        root.getChildren().add(baseNode);


        this.scene = scene;
        this.root = root;

        cashiers = new ArrayList<>(cashierCount);
        createCashiers();
    }

    public void start() {
        mainCycle.start();
        startCashiers();
    }

    private void startCashiers() {
        for (int i = 0; i < cashierCount; i++) {
            cashiers.get(i).start();
        }
    }


    private void createCashiers() throws FileNotFoundException {
        String cashierPath = GlobalConfiguration.imagePath + "cashier.png";
        int y = 0;
        for (int i = 0; i < cashierCount; i++) {
            CashierThread cashier = new CashierThread(
                    new Position(750, y += 125), publicClientsSemaphore,
                    publicCashiersSemaphore, clientQueue
            );

            CashierImageView cashierImageView = new CashierImageView(cashier, cashierPath);
            positionedList.add(cashierImageView);

            Node[] a = cashierImageView.getViewArray();
            for (Node node : a) {
                this.root.getChildren().add(node);
            }
            cashiers.add(cashier);
        }
    }

    public ClientThread createClient(int attendmentTime) throws FileNotFoundException {
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

        Node[] nodes = clientImageView.getViewArray();
        for (Node node : nodes) {
            this.root.getChildren().add(node);
        }

        client.setOnFinishCallback(() -> {
            positionedList.remove(clientImageView);
            for (Node node : nodes) {
                this.root.getChildren().remove(node);
            }
        });

        return client;
    }

    private static Node setupBaseNode() {
        Rectangle baseRect = new Rectangle();
        baseRect.setFill(Paint.valueOf("yellow"));
        baseRect.setHeight(1000);
        baseRect.setWidth(1000);
        return baseRect;
    }

    // TODO: COULD NOT IMPLEMENT THIS
    private Image manImage;
    private Image womanImage;

    private Image getClientImage() throws FileNotFoundException {
        if (womanImage == null || manImage == null) {
            this.createClientImage();
        }

        if (random.nextFloat() >= 0.5) {
            return manImage;
        } else {
            return womanImage;
        }
    }

    private void createClientImage() throws FileNotFoundException {
        String womanPath = GlobalConfiguration.imagePath + "woman.png";
        String manPath = GlobalConfiguration.imagePath + "man.png";
        manImage = new Image(new FileInputStream(manPath));
        womanImage = new Image(new FileInputStream(womanPath));
    }
}
