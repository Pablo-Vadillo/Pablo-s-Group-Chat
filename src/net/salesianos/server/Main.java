package net.salesianos.server;

import net.salesianos.threads.ClientHandler;
import net.salesianos.utils.models.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        final int SERVER_PORT = 55000;

        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Client> messagges = new ArrayList<>();

        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        ArrayList<ObjectOutputStream> connectedObjOutputStream = new ArrayList<>();

        while (true) {
            System.out.println("Esperando conexión...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("CONEXION ESTABLECIDA");

            ObjectOutputStream clientObjOutStream = new ObjectOutputStream(clientSocket.getOutputStream());
            connectedObjOutputStream.add(clientObjOutStream);

            ObjectInputStream clientObjInStream = new ObjectInputStream(clientSocket.getInputStream());
            ClientHandler clientHandler = new ClientHandler(clientObjInStream, clientObjOutStream,
                    connectedObjOutputStream);
            clientHandler.start();
        }
        // serverSocket.close();
    }
}