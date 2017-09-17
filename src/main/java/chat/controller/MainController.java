package chat.controller;

import chat.utils.SocketUtils;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static chat.utils.Const.PORT;

public class MainController {

    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private Thread thread;
    private String nickname;
    private String ipAddress;

    @FXML
    TextArea textAreaChat;
    @FXML
    TextField fieldMessage;
    @FXML
    TextField fieldNickname;
    @FXML
    TextField fieldIPAddress;
    @FXML
    Button buttonSubmit;
    @FXML
    Button buttonConnection;
    @FXML
    Button buttonCancel;

    @FXML
    public void initialize() {
        this.buttonConnection.setDefaultButton(true);
        Platform.runLater(() -> this.fieldNickname.requestFocus());
        this.buttonConnection.disableProperty().bind(
                Bindings.isEmpty(this.fieldIPAddress.textProperty())
                        .or(Bindings.isEmpty(this.fieldNickname.textProperty())));
        this.buttonSubmit.disableProperty().bind(
                Bindings.isEmpty(this.fieldIPAddress.textProperty())
                        .or(Bindings.isEmpty(this.fieldNickname.textProperty()))
                        .or(Bindings.isEmpty(this.fieldMessage.textProperty())));
    }

    @FXML
    private void сonnection() {
        this.fieldMessage.requestFocus();
        this.nickname = this.fieldNickname.getText();
        this.ipAddress = this.fieldIPAddress.getText();
        this.buttonSubmit.setDefaultButton(true);
        this.buttonConnection.disableProperty().bind(Bindings
                .isNotEmpty(this.fieldIPAddress.textProperty())
                .or(Bindings.isNotEmpty(this.fieldNickname.textProperty())));
        if ((this.nickname.length() > 0) && (this.ipAddress.length() > 0)) {
            try {
                this.socket = new Socket(this.ipAddress, PORT);
                this.printWriter = new PrintWriter(this.socket.getOutputStream());
                this.thread = new Thread(() -> {
                    try {
                        this.bufferedReader = new BufferedReader((new InputStreamReader(this.socket.getInputStream())));
                        while (true) {
                            String line;
                            while ((line = this.bufferedReader.readLine()) != null) {
                                this.textAreaChat.appendText(line);
                                this.textAreaChat.appendText("\r\n");
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Error!");
                    }
                });
                this.thread.setDaemon(true);
                this.thread.start();

            } catch (UnknownHostException e) {
                System.err.println("Error!");
            } catch (IOException e) {
                System.err.println("Error!");
            }
        }
    }

    @FXML
    private void submitMessage() {
        this.buttonSubmit.setDefaultButton(true);
        if (this.fieldMessage.getText().length() > 0) {
            SocketUtils.sendMessage(this.nickname + ": " + this.fieldMessage.getText(), this.printWriter);
            this.fieldMessage.clear();
        }
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

}