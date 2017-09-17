package chat.model.server;

import java.io.IOException;

public class MainServer {

    public static void main(String[] args) throws IOException {
        Server socketServer = new Server();
        socketServer.start();
    }

}