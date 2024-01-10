package net.salesianos.test;

import java.util.ArrayList;

import net.salesianos.utils.models.Client;
import net.salesianos.utils.models.Message;

public class BasicTest1 {
    public static void main(String[] args) {
        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Message> messagges = new ArrayList<>();

        Client pedro = new Client("Pedro");
        Message message = new Message(pedro, "Hola");

        Message message2 = new Message(pedro, "Suuuuuuuuuu");
        messagges.add(message2);
        messagges.add(message);
        clients.add(pedro);

        for (int i = 0; i < messagges.size(); i++) {
            System.out.println(messagges.get(i).getMessage());
        }

    }
}
