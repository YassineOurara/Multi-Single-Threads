package com.example.tp_sys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientChat extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStge) throws Exception {
		primaryStge.setTitle("Client");
		BorderPane borderPane = new BorderPane();
		TextField textFieldHost = new TextField("localhost");
		textFieldHost.setBackground(new Background(new BackgroundFill(Color.GRAY,null,null)));
		TextField textFieldPort = new TextField("Port");
		textFieldPort.setBackground(new Background(new BackgroundFill(Color.GRAY,null,null)));
		Button btn = new Button("Connecter");
		btn.setMaxWidth(100);
		btn.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));

		HBox hBox = new HBox();
		hBox.setSpacing(10);
		hBox.setPadding(new Insets(100));
		hBox.getChildren().addAll(textFieldHost,textFieldPort,btn);
		borderPane.setTop(hBox);
		VBox vBox2 = new VBox();
		vBox2.setSpacing(100);
		hBox.setPadding(new Insets(40));

		ObservableList<String> listModel=FXCollections.observableArrayList();
		ListView<String> listView = new ListView<String>(listModel);
		borderPane.setCenter(listView);
		vBox2.getChildren().add(listView);
		borderPane.setCenter(vBox2);
		Scene scene = new Scene(borderPane, 540, 400);
		primaryStge.setScene(scene);
		primaryStge.show();

		btn.setOnAction((event)->{
			String host = textFieldHost.getText();
			int port = Integer.parseInt(textFieldPort.getText());
			try {
				Socket socket = new Socket(host,port);
				InputStream inputStream = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(isr);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				new Thread(()->{
					try {
						while(true) {
							String response = bufferedReader.readLine();
							listModel.add(response);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}).start();
			} catch (IOException e) {
				 e.printStackTrace();
			}
		});
	}
}
