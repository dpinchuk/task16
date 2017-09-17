package chat.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static chat.utils.Const.IP_ADDRESS;
import static chat.utils.Const.PORT;

public class SocketUtils {

    public static void sendMessage(String messageLine, PrintWriter printWriter) {
        printWriter.println(messageLine);
        printWriter.flush();
    }

    public static boolean hostAvailabilityCheck1() {
        try (Socket socket = new Socket(IP_ADDRESS, PORT)) {
            System.out.println("Server is already running, the download continues...");
            return true;
        } catch (IOException ex) {
        }
        System.out.println("Wait for server load...");
        return false;
    }

}