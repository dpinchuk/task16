package chat.view;

import chat.model.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

import static chat.utils.Const.IP_ADDRESS;
import static chat.utils.Const.PORT;

public class MainApp extends Application {

    private final int wight = 300;
    private final int height = 400;

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("iChat");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        primaryStage.setMinWidth(350);
        primaryStage.setMinHeight(450);
        primaryStage.setScene(new Scene(root, wight, height));
        primaryStage.show();
    }

    public static boolean hostAvailabilityCheck() {
        try (Socket socket = new Socket(IP_ADDRESS, PORT)) {
            return true;
        } catch (IOException ex) {
        }
        return false;
    }

    public static void main(String[] args) {
        if (hostAvailabilityCheck()) {
            System.out.println("Server is already running, the download continues...");
            launch(args);
        } else {
            System.out.println("Wait for server load...");
            Server socketServer = new Server();
            socketServer.start();
            launch(args);
        }
    }

}