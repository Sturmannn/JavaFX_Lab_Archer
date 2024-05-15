package org.archer.game;

import org.archer.elements.Archer;
import org.archer.elements.Targets;

import java.util.Map;

public class Response {
    private final Map < String, Archer > archers;
    private final Targets targets;
    private Archer winner = null;

    public Response(final Map < String, Archer > archers, final Targets targets) {
        this.archers = archers;
        this.targets = targets;
    }

    public Map < String, Archer > getArchers() {
        return archers;
    }

    public Targets getTargets() {
        return targets;
    }

    public Archer getWinner() {
        return winner;
    }

    public void setWinner(final Archer winner) {
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