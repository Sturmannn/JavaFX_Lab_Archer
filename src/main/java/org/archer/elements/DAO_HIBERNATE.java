package org.archer.elements;

import org.archer.db.ArcherDB;
import org.archer.db.HibernateSessionFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class DAO_HIBERNATE {
    private final ArrayList<ArcherDB> allPlayersFromDB = new ArrayList<>();

    public DAO_HIBERNATE() {
        System.out.println("DAO_HIBERNATE created");
        getAllPlayersFromDB();
        System.out.println("Players from DB: " + allPlayersFromDB.size());
    }

    public void addPlayer(ArcherDB player) {
        for (ArcherDB playerFromDB : allPlayersFromDB) {
            if (playerFromDB.getNickName().equals(player.getNickName())) {
                return;
            }
        }
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(player);
        tx1.commit();
        session.close();
    }

    public void updatePlayer(ArcherDB player) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(player); // Сначала сохраняем или обновляем сущность
        session.lock(player, LockMode.PESSIMISTIC_WRITE); // Затем блокируем ее для обновления
        session.update(player);
        tx1.commit();
        session.close();
    }

    public ArcherDB getPlayer(String nickName) {
        for (ArcherDB player : allPlayersFromDB) {
            if (player.getNickName().equals(nickName)) {
                return player;
            }
        }
        return null;
    }

    public void setVictoryCount(String nickName, int victoryCount) {
        ArcherDB player = getPlayer(nickName);
        if (player != null) {
            player.setVictoryCount(victoryCount);
            updatePlayer(player);
        }
    }

    public ArrayList<ArcherDB> getAllPlayersFromDB() {
        allPlayersFromDB.clear();
        ArrayList<ArcherDB> players = (ArrayList<ArcherDB>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From ArcherDB").list();
        allPlayersFromDB.addAll(players);
        return allPlayersFromDB;
    }
}
