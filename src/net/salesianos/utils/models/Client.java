package net.salesianos.utils.models;

public class Client {
    String name;

    public Client(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                '}';
    }
}
