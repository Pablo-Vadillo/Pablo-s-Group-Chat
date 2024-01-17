package net.salesianos.threads;

import net.salesianos.utils.models.Client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class ClientHandler extends Thread {
    private ObjectInputStream clientObjInStream;
    private ObjectOutputStream clientObjOutStream;
    private ArrayList<ObjectOutputStream> connectedObjOutputStreamList;

    public ClientHandler(ObjectInputStream clientObjInStream, ObjectOutputStream clientObjOutStream,
                         ArrayList<ObjectOutputStream> connectedObjOutputStreamList) {
        this.clientObjInStream = clientObjInStream;
        this.clientObjOutStream = clientObjOutStream;
        this.connectedObjOutputStreamList = connectedObjOutputStreamList;
    }

    @Override
    public void run() {
        String username = "";
        try {

            username = this.clientObjInStream.readUTF();

            while (true) {
                Client personReceived = (Client) this.clientObjInStream.readObject();
                System.out.println(username + " envía: " + personReceived.toString());

                for (ObjectOutputStream otherObjOutputStream : connectedObjOutputStreamList) {
                    if (otherObjOutputStream != this.clientObjOutStream) {
                        otherObjOutputStream.writeObject(personReceived);
                    }
                }

            }
        } catch (EOFException eofException) {
            this.connectedObjOutputStreamList.remove(this.clientObjOutStream);
            System.out.println("CERRANDO CONEXIÓN CON " + username.toUpperCase());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
