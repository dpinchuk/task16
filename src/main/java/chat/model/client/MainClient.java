package chat.model.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static chat.utils.Const.IP_ADDRESS;
import static chat.utils.Const.IS_NOT_EXIT;

public class MainClient {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your [Nickname]: ");
        //String nickname = reader.readLine();
        System.out.println("Your IP Address: '" + IP_ADDRESS + "'");
        Client client = new Client("Dima");
        client.connect(IP_ADDRESS);
        Scanner scanner = new Scanner(System.in);
        String line;
        while (IS_NOT_EXIT) {
            line = scanner.nextLine();
            if (line.length() > 0) {
                client.sendMessage(line);
            }
        }
    }

}