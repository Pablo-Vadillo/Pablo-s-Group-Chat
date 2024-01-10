package net.salesianos.utils.models;

import java.time.LocalTime;

public class Message {
    String textMessage;
    LocalTime localtime;
    String clientName;

    public Message(Client client, String textMessage, LocalTime localtime) {
        this.textMessage = textMessage;
        this.localtime = LocalTime.now();
        this.clientName = client.getName();
    }
}
