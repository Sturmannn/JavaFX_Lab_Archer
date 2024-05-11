package org.archer.game;

import javafx.scene.layout.AnchorPane;
import org.archer.elements.Archer;
import org.archer.elements.GameStatus;
import org.archer.elements.MyPair;
import org.archer.elements.Targets;

import java.util.ArrayList;

public class Response {
    private ArrayList<Archer> archers;
//    private MyPair mainFieldSize;
    private Targets targets;

    Archer winner = null;

    public Response(ArrayList<Archer> archers, Targets targets) {
        this.archers = archers;
        this.targets = targets;
    }

    public ArrayList<Archer> getArchers() {
        return archers;
    }

    public Targets getTargets() {
        return targets;
    }

    public Archer getWinner() {
        return winner;
    }

    public void setWinner(Archer winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Response{" +
                "archers=" + archers +
                ", targets=" + targets +
                ", winner=" + winner +
                '}';
    }
}
