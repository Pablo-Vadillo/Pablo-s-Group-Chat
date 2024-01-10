package net.salesianos.utils.models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {
    String textMessage;
    LocalTime localtime;
    String clientName;

    public Message(Client client, String textMessage) {
        this.textMessage = textMessage;
        this.localtime = LocalTime.now();
        this.clientName = client.getName();
    }

    public String getMessage() {

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");

        String formattedDate = localtime.format(myFormatObj);
        return formattedDate + " " + clientName + ": " + textMessage;
    }
}
