package net.salesianos.server;

import net.salesianos.threads.ClientHandler;
import net.salesianos.utils.models.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        final int SERVER_PORT = 55000;
        ArrayList<Client> messages = new ArrayList<>();

        ServerSocket serverSocket;
        ArrayList<ObjectOutputStream> connectedObjOutputStream = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Servidor en espera de conexiones...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("CONEXIÓN ESTABLECIDA");

                ObjectOutputStream clientObjOutStream = new ObjectOutputStream(clientSocket.getOutputStream());
                connectedObjOutputStream.add(clientObjOutStream);

                ObjectInputStream clientObjInStream = new ObjectInputStream(clientSocket.getInputStream());
                ClientHandler clientHandler = new ClientHandler(clientObjInStream, clientObjOutStream, connectedObjOutputStream, messages);
                clientHandler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
