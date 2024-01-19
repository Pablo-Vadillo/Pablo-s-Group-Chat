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
    private ArrayList<Client> messages;

    public ClientHandler(ObjectInputStream clientObjInStream, ObjectOutputStream clientObjOutStream, ArrayList<ObjectOutputStream> connectedObjOutputStreamList, ArrayList<Client> messages) {
        this.clientObjInStream = clientObjInStream;
        this.clientObjOutStream = clientObjOutStream;
        this.connectedObjOutputStreamList = connectedObjOutputStreamList;
        this.messages = messages;
    }

    @Override
    public void run() {
        String username = "";
        try {
            username = this.clientObjInStream.readUTF();

            sendMessagesToClient();

            while (true) {
                Client personReceived = (Client) this.clientObjInStream.readObject();
                System.out.println(username + " envía: " + personReceived.toString());

                messages.add(personReceived);

                resendMessage(personReceived);
            }
        } catch (EOFException eofException) {
            this.connectedObjOutputStreamList.remove(this.clientObjOutStream);
            System.out.println("CERRANDO CONEXIÓN CON " + username.toUpperCase());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            clientObjInStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            clientObjOutStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void sendMessagesToClient() throws IOException {
        for (Client message : messages) {
            clientObjOutStream.writeObject(message);
        }
    }

    private void resendMessage(Client message) throws IOException {
        for (ObjectOutputStream otherObjOutputStream : connectedObjOutputStreamList) {
            if (otherObjOutputStream != this.clientObjOutStream) {
                otherObjOutputStream.writeObject(message);
            }
        }
    }
}
