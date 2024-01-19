package net.salesianos.client;

import net.salesianos.client.threads.ServerListener;
import net.salesianos.utils.models.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) throws Exception {

        String userOption = null;
        final Scanner SCANNER = new Scanner(System.in);

        System.out.println("¿Cómo te llamas?");
        String username = SCANNER.nextLine();

        try (Socket socket = new Socket("192.168.154.2", 55000);
             ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objInStream = new ObjectInputStream(socket.getInputStream())) {

            objOutStream.writeUTF(username);

            ServerListener serverListener = new ServerListener(objInStream);
            serverListener.start();

            while ((userOption) != "bye") {
                String inputMessage = SCANNER.nextLine();

                if (inputMessage.startsWith("bye")) {
                    userOption = "bye";
                } else {

                    if (inputMessage.startsWith("msg:")) {
                        Client client = new Client(username, inputMessage);
                        objOutStream.writeObject(client);
                    } else {
                        System.out.println("Por favor, incluye 'msg:' antes del mensaje o escribe 'bye' para salir.");
                    }
                }
            }
        }
        SCANNER.close();
    }
}
