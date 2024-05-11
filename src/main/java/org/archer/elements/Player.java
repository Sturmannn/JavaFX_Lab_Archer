package org.archer.elements;

import javafx.scene.shape.Arc;

import java.net.InetAddress;

public class Player {
    Archer archer;
    private String name;

    InetAddress ip = null;

    public Player(String name, InetAddress ip, double x, double y) {
        archer = new Archer(x, y, "pedro");
        this.ip = ip;
        this.name = name;
    }

    public Archer getArcher() {
        return archer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getIp() {
        return ip;
    }
}
