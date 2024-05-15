package org.archer.db;

import javax.persistence.*;

@Entity
@Table(name = "Winner")
public class ArcherDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Player")
    private String nickName;

    @Column(name = "Victories_num")
    private int victoryCount;

    public ArcherDB() {
        this.nickName = null;
        this.victoryCount = 0;
    }

    public ArcherDB(final String nickName, final int victoryCount) {
        this.nickName = nickName;
        this.victoryCount = victoryCount;
    }

    public String getNickName() {
        return nickName;
    }

    public int getVictoryCount() {
        return victoryCount;
    }

    public void setVictoryCount(int victoryCount) {
        this.victoryCount = victoryCount;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
