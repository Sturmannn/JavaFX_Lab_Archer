package org.archer.game;

import com.google.gson.Gson;
import org.archer.elements.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketServerWrapper {
    Model model = BModel.getModel();
    static final AtomicInteger idGenerator = new AtomicInteger(0);
    int id;
    Game game = new Game(model, this);
    Gson gson = new Gson();
    Socket cs;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;

    public SocketServerWrapper(Socket client_socket) {
        this.cs = client_socket;
        try {
            is = cs.getInputStream();
            os = cs.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);
            id = idGenerator.getAndIncrement();
        } catch (Exception e) {
            System.err.println("Error in SocketServerWrapper constructor: " + e.getMessage());
        }
        new Thread(this::run).start();
    }

    private void run() {
        while (true) {
            Message message = receiveMessage();
            if (message == null) {
                model.removeClient(this);
                break;
            }
            handleMessageAction(message);
        }
    }
    public int getId() {
        return id;
    }
    public Message receiveMessage() {
        try {
            String messageStr = dis.readUTF();
            return gson.fromJson(messageStr, Message.class);
        } catch (Exception e) {
            System.err.println("Error in SocketServerWrapper receiveMessage: " + e.getMessage());
            return null;
        }
    }
    public void sendResponse(Response response) {
        try {
            String responseStr = gson.toJson(response);
            System.out.println("Response: " + response.getArchers());
            dos.writeUTF(responseStr);
        } catch (Exception e) {
            System.err.println("Error in SocketServerWrapper sendResponse: " + e.getMessage());
            model.removeClient(this);
            model.removeObserver((observer) -> {
                Response resp = new Response(model.getArchers(), model.getTargets());
                sendResponse(resp);
            });
            closeConnection();
        }
    }
    public void handleMessageAction(Message message) {
        MessageAction action = message.getAction();

        switch (action) {
            case CONNECT:
                model.addClient(this, this.getId());
                Archer newArcher = new Archer(40, 170, message.getArcher().getNickName());
                newArcher.setColor(PlayerColors.getColor(this.getId()));
                model.getArchers().add(newArcher);
                model.notifyObservers();
                break;
            case NICKNAME_CHECK:
                Response response = new Response(model.getArchers(), model.getTargets());
                sendResponse(response);
                break;
            case START:

                model.getArcher(this.getId()).setReady(true);
                for (Archer archer : model.getArchers()) {
                    if (!archer.isReady()) {
                        return;
                    }
                }
                game.StartGame();
                break;
            case SHOOT:
                game.Shot(this.getId());
                break;
            case PAUSE:
                game.Pause();
                break;
            case STOP:
                game.StopGame();
                break;
            case CHANGE_POSITION:
                game.ShooterChangePosition(message.getArcher(), this.getId());
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
