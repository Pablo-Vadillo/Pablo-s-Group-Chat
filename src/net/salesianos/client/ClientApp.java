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

        int userOption = 0;
        final Scanner SCANNER = new Scanner(System.in);

        System.out.println("¿Cómo te llamas?");
        String username = SCANNER.nextLine();

        Socket socket = new Socket("172.16.101.34", 55000);
        ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream());
        objOutStream.writeUTF(username);

        ObjectInputStream objInStream = new ObjectInputStream(socket.getInputStream());
        ServerListener serverListener = new ServerListener(objInStream);
        serverListener.start();

        while (userOption != -1) {

            System.out.println("Va a enviar datos de persona al servidor.");
            System.out.print("Introduzca el mensaje ");

            Client client = new Client(username, SCANNER.nextLine());

            objOutStream.writeObject(client);

            try {
                System.out.println("Pulse -1 pa salir o cualquier cosa para continuar: ");
                userOption = SCANNER.nextInt();
                SCANNER.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Continuamos...");
            }
        }

        SCANNER.close();
        objInStream.close();
        objOutStream.close();
        socket.close();
    }
}