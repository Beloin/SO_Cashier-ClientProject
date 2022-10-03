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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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

            Button btn = new Button();
            btn.setText("Open Dialog");
            btn.setOnAction(
                    event -> {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(stage);
                        VBox dialogVbox = new VBox(20);
                        dialogVbox.getChildren().add(new Text("This is a Dialog"));
                        Label label1 = new Label("Tempo de Atendimento");
                        TextField textField = new TextField();
                        dialogVbox.getChildren().addAll(label1, textField);
                        dialogVbox.setSpacing(10);

                        Button ok = new Button("Ok!");
                        ok.setOnAction(actionEvent -> {
                            dialog.close();
                        });

                        dialogVbox.getChildren().add(ok);

                        Scene dialogScene = new Scene(dialogVbox, 300, 200);
                        dialog.setScene(dialogScene);
                        dialog.show();
                        // TODO ADD TEXT INPUT TO THIS
                    });

            HBox buttonLayout = new HBox();
            buttonLayout.getChildren().add(btn);

            buttonLayout.setLayoutX(12);
            buttonLayout.setSpacing(10);

            buttonLayout.getChildren().add(startButton);
            buttonLayout.getChildren().add(addClient);

            stackPane.getChildren().add(buttonLayout);
            CashierAndClientGame game = new CashierAndClientGame(r, scene2, root);
            root.getChildren().add(stackPane);
            addClient.setOnAction(a -> {
                try {
                    ClientThread c = game.createClient(5);
                    c.start();
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