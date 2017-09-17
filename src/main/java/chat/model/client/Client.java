package chat.model.client;

import chat.utils.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static chat.utils.Const.IS_NOT_EXIT;
import static chat.utils.Const.PORT;

public class Client {

    private PrintWriter printWriter;
    private String nickname;

    public Client(String nickname) {
        this.nickname = nickname;
    }

    public void connect(String address) {
        try {
            Socket socket = new Socket(address, PORT);
            this.printWriter = new PrintWriter(socket.getOutputStream());
            new Thread(() -> {
                try {
                    BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(socket.getInputStream())));
                    while (IS_NOT_EXIT) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                    //System.err.println("Error!");
                }
            }).start();
        } catch (UnknownHostException e) {
            //e.printStackTrace();
            //System.err.println("Error!");
        } catch (IOException e) {
            //e.printStackTrace();
            //System.err.println("Error!");
        }
    }

    public void sendMessage(String message) {
        SocketUtils.sendMessage(this.nickname + ": " + message, printWriter);
    }

}