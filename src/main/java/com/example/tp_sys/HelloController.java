package com.example.tp_sys;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.io.*;
import java.net.Socket;

public class HelloController {

    @FXML
    private Button btnco;

    @FXML
    private TextField loc;

    @FXML
    private TextField port;
    ObservableList<String> listModel= FXCollections.observableArrayList();
    @FXML
    protected void conn(ActionEvent event) throws IOException {
        String host = loc.getText();
        int prt = Integer.parseInt(port.getText());
        try {
            Socket socket = new Socket(host,prt);
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
    }


}
