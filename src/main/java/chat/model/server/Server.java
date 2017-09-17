package chat.model.server;

import chat.utils.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static chat.utils.Const.IS_NOT_EXIT;
import static chat.utils.Const.PORT;

public class Server {

    private List<PrintWriter> printWriterList = new ArrayList<>();
    private List<String> history = new ArrayList<>();

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started...");
            System.out.println("Listening to the port [" + PORT + "]");
            System.out.println("Please run the program again to activate the Client...");
            while (IS_NOT_EXIT) {
                Socket socket = serverSocket.accept();
                System.out.println("New user joined...");
                new Thread(() -> {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                        this.printWriterList.add(printWriter);
                        this.history.forEach(a -> SocketUtils.sendMessage(a.toString(), printWriter));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println(line);
                            this.history.add(line);
                            for (int i = 0; i < this.printWriterList.size(); i++) {
                                SocketUtils.sendMessage(line, this.printWriterList.get(i));
                            }
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                        //System.err.println("Error!");
                    }
                }).start();
            }
        } catch (IOException e) {
            //e.printStackTrace();
            //System.err.println("Error!");
        }
    }

}