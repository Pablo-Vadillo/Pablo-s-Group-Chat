package net.salesianos.client.threads;

import java.io.IOException;
import java.io.ObjectInputStream;

import net.salesianos.utils.models.Client;
public class ServerListener extends Thread {

    private ObjectInputStream objInStream;

    public ServerListener(ObjectInputStream socketObjectInputStream) {
        this.objInStream = socketObjectInputStream;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Client newServerClient = (Client) this.objInStream.readObject();
                System.out.println("El servidor registró a nuevo cliente: " + newServerClient.toString());
            }

        } catch (ClassNotFoundException e1) {
            System.out.println("No se ha encontrado la clase Cliente");
        } catch (IOException e2) {
            System.out.println("Se ha dejado de escuchar al servidor.");
        }
    }
}
