package net.salesianos.utils.models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Client implements java.io.Serializable {
    String name;
    String message;
    LocalTime localtime;

    public Client(String name, String message) {
        this.name = name;
        this.message = message;
        this.localtime = LocalTime.now();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = localtime.format(myFormatObj);
        return formattedDate + " " + name + ": " + message;
    }
}
