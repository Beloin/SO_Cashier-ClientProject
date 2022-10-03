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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        BorderPane borderPane = new BorderPane();
        Group root = new Group();
        borderPane.setCenter(root);
        Scene scene2 = new Scene(borderPane, 1000, 1000);

        stage.setTitle("Cashier and Clients");
        stage.setScene(scene2);
        stage.show();

        InitialMenu mn = new InitialMenu(root);
        mn.buildMenu(r -> {
            StackPane stackPane = new StackPane();

            Button addClientButton = new Button();

            addClientButton.setText("Adicionar Cliente");
            HBox buttonLayout = new HBox();
            buttonLayout.getChildren().add(addClientButton);
            buttonLayout.setLayoutX(12);
            buttonLayout.setSpacing(10);
            stackPane.getChildren().add(buttonLayout);
            CashierAndClientGame game = new CashierAndClientGame(r, scene2, root);
            root.getChildren().add(stackPane);

            addClientButton.setOnAction(
                    event -> {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(stage);
                        VBox dialogVbox = new VBox(20);


                        Label label1 = new Label("Tempo de Atendimento (Em segundos):");
                        TextField textField = new TextField();

                        dialogVbox.setPadding(new Insets(0, 10, 0, 10));
                        dialogVbox.getChildren().addAll(label1, textField);
                        dialogVbox.setSpacing(10);
                        dialogVbox.setAlignment(Pos.CENTER);

                        Button ok = new Button("Ok!");
                        ok.setOnAction(actionEvent -> {
                            try {
                                int attendment = Integer.parseInt(textField.getText());
                                ClientThread c = game.createClient(attendment);
                                c.start();
                                dialog.close();
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            } catch (NumberFormatException e) {
                                label1.setTextFill(Color.ORANGERED);
                            }
                        });

                        dialogVbox.getChildren().add(ok);

                        Scene dialogScene = new Scene(dialogVbox, 300, 200);
                        dialog.setScene(dialogScene);
                        dialog.show();
                    }
            );

            game.start();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}