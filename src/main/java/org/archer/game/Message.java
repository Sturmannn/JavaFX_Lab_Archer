package org.archer.game;

import org.archer.elements.Archer;
import org.archer.elements.GameStatus;
import org.archer.elements.MyPair;
import org.archer.elements.Targets;

public class Message {
    private final Archer archer;
    private MyPair mainFieldSize;
    private Targets targets;
    private GameStatus gameStatus;
    private final MessageAction action;

    public Message(final Archer archer, final MessageAction action) {
        this.archer = archer;
        this.action = action;
    }

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