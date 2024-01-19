package net.salesianos.client.threads;

import net.salesianos.utils.models.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ServerListener extends Thread {

    private ObjectInputStream objInStream;
    private ArrayList<ObjectInputStream> connectedObjInputStreamList;

    public ServerListener(ObjectInputStream socketObjectInputStream) {
        this.objInStream = socketObjectInputStream;
        this.connectedObjInputStreamList = connectedObjInputStreamList;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Client newServerClient = (Client) this.objInStream.readObject();
                System.out.println(newServerClient.toString());
            }

        } catch (ClassNotFoundException e1) {
            System.out.println("No se ha encontrado la clase Cliente");
        } catch (IOException e2) {
            System.out.println("Se ha dejado de escuchar al servidor.");
        }
    }
}
