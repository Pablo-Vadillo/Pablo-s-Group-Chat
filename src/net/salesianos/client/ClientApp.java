package net.salesianos.client;

import net.salesianos.client.threads.ServerListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

import net.salesianos.client.threads.ServerListener;
import net.salesianos.utils.models.Client;

public class ClientApp {
    public static void main(String[] args) throws Exception {

        String userOption = null;
        final Scanner SCANNER = new Scanner(System.in);

        System.out.println("¿Cómo te llamas?");
        String username = SCANNER.nextLine();

        Socket socket = new Socket("172.16.101.34", 55000);
        ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream());
        objOutStream.writeUTF(username);

        ObjectInputStream objInStream = new ObjectInputStream(socket.getInputStream());
        ServerListener serverListener = new ServerListener(objInStream);
        serverListener.start();

        while (userOption != "bye") {
            System.out.print("Introduzca el mensaje ");

            Client client = new Client(username, SCANNER.nextLine());

            objOutStream.writeObject(client);

            try {
                System.out.println("Teclee bye pa salir: ");
                userOption = SCANNER.nextLine();
                SCANNER.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Continuamos...");
            }
            if (userOption.startsWith("bye")){
                SCANNER.close();
                objInStream.close();
                objOutStream.close();
                socket.close();
            }
        }

    }
}