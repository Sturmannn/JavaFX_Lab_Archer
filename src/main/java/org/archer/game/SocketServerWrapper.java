package org.archer.game;

import com.google.gson.Gson;
import org.archer.elements.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketServerWrapper {
    private final Model model = BModel.getModel();
    private final Game game = new Game(model);
    private final Gson gson = new Gson();
    private final Socket cs;
    private final DataInputStream dis;
    private final DataOutputStream dos;

    public SocketServerWrapper(final Socket client_socket) {
        this.cs = client_socket;
        DataInputStream tempDis;
        DataOutputStream tempDos;
        try {
            tempDis = new DataInputStream(cs.getInputStream());
            tempDos = new DataOutputStream(cs.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error in SocketServerWrapper constructor: " + e.getMessage());
            closeConnection();
            throw new RuntimeException(e);
        }
        dis = tempDis;
        dos = tempDos;
        new Thread(this::run).start();
    }

    private void run() {
        while (true) {
            try {
                Message message = receiveMessage();
                if (message == null) {
                    break;
                }
                handleMessageAction(message);
            } catch (Exception e) {
                System.err.println("Error in SocketServerWrapper run: " + e.getMessage());
                closeConnection();
                break;
            }
        }
    }

    public Message receiveMessage() {
        try {
            String messageStr = dis.readUTF();
            return gson.fromJson(messageStr, Message.class);
        } catch (IOException e) {
            System.err.println("Error in SocketServerWrapper receiveMessage: " + e.getMessage());
            closeConnection();
            return null;
        }
    }

    public void sendResponse(final Response response) {
        try {
            String responseStr = gson.toJson(response);
            dos.writeUTF(responseStr);
        } catch (IOException e) {
            System.err.println("Error in SocketServerWrapper sendResponse: " + e.getMessage());
            model.removeObserver((observer) -> {
                    Response resp = new Response(model.getArchers(), model.getTargets());
            sendResponse(resp);
            });
            closeConnection();
        }
    }

    public void handleMessageAction(final Message message) {
        final MessageAction action = message.getAction();

        switch (action) {
            case CONNECT:
                Archer newArcher = new Archer(40, 170, message.getArcher().getNickName());
                newArcher.setColor(PlayerColors.getColor(model.getArchers().size()));
                model.getArchers().put(newArcher.getNickName(), newArcher);
                model.notifyObservers();
                break;
            case NICKNAME_CHECK:
                Response response = new Response(model.getArchers(), model.getTargets());
                sendResponse(response);
                break;
            case START:
                model.getArcher(message.getArcher().getNickName()).setReady(true);
                for (Archer archer: model.getArchers().values()) {
                    if (!archer.isReady()) {
                        return;
                    }
                }
                game.StartGame();
                break;
            case SHOOT:
                game.Shot(message.getArcher().getNickName());
                break;
            case PAUSE:
                game.Pause();
                break;
            case STOP:
                game.StopGame();
                break;
            case CHANGE_POSITION:
                game.ShooterChangePosition(message.getArcher(), message.getArcher().getNickName());
                break;
            default:
                System.err.println("Unknown action: " + action);
                break;
        }
    }

    private void closeConnection() {
        try {
            if (dis != null) {
                dis.close();
            }
            if (dos != null) {
                dos.close();
            }
            if (cs != null && !cs.isClosed()) {
                cs.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}