package org.archer.elements;

import java.util.ArrayList;

public class DAO {
    private ArrayList<Arrow> arrows = new ArrayList<>();
    private Targets targets;
    private Score score;
    private GameStatus gameStatus;

    public DAO() {}
    public ArrayList<Arrow> getArrows() {
        return arrows;
    }
    public void addArrow(Arrow arrow) {
        arrows.add(arrow);
    }
    public void setArrows(ArrayList<Arrow> arrows) {
        this.arrows = arrows;
    }
    public Targets getTargets() {
        return targets;
    }
    public void setTargets(Targets targets) {
        this.targets = targets;
    }
    public Score getScore() {
        return score;
    }
    public void setScore(Score score) {
        this.score = score;
    }


}
