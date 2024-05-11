package org.archer.game;

import javafx.scene.layout.AnchorPane;
import org.archer.elements.Archer;
import org.archer.elements.GameStatus;
import org.archer.elements.MyPair;
import org.archer.elements.Targets;

import java.util.ArrayList;

public class Message {
//    private ArrayList<Archer> archers;
    private Archer archer;
    private MyPair mainFieldSize;
    private Targets targets;
    private GameStatus gameStatus;
    private MessageAction action;

//    public Message(ArrayList<Archer> archers, MyPair mainFieldSize, Targets targets, GameStatus gameStatus, MessageAction action) {
//        this.archers = archers;
//        this.mainFieldSize = mainFieldSize;
//        this.targets = targets;
//        this.gameStatus = gameStatus;
//        this.action = action;
//    }
    public Message(Archer archer, MessageAction action) {
        this.archer = archer;
        this.action = action;
    }

//    public ArrayList<Archer> getArchers() {
//        return archers;
//    }
    public MyPair getMainFieldSize() {
        return mainFieldSize;
    }
    public Targets getTargets() {
        return targets;
    }
    public GameStatus getGameStatus() {
        return gameStatus;
    }
    public MessageAction getAction() {
        return action;
    }
    public Archer getArcher() {
        return archer;
    }

    @Override
    public String toString() {
        return "Message{" +
                "archer=" + archer +
                ", mainFieldSize=" + mainFieldSize +
                ", targets=" + targets +
                ", gameStatus=" + gameStatus +
                ", action=" + action +
                '}';
    }
}
