package org.archer;

import org.archer.elements.Model;
import org.archer.elements.BModel;
import org.archer.game.Response;
import org.archer.game.SocketServerWrapper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServerGameApp {
    Model model = BModel.getModel();
    int port = 3124;
    InetAddress ip = null;

    public void StartServer() {
        ServerSocket server_socket = null;
        Socket client_socket = null;
        try {
            ip = InetAddress.getLocalHost();
            server_socket = new ServerSocket(port, 0, ip);
            System.out.println("Server Started. IP: " + ip.getHostAddress());
            while (true) {
                client_socket = server_socket.accept();
                SocketServerWrapper socketServerWrapper = new SocketServerWrapper(client_socket);
                System.out.println("Client " + socketServerWrapper.getId() + " connected: " + client_socket.getInetAddress().getHostAddress());
                model.addClient(socketServerWrapper, socketServerWrapper.getId());
                model.addObserver((observer) -> {
                    Response response = new Response(model.getArchers(), model.getTargets());
                    socketServerWrapper.sendResponse(response);
                });
            }
        } catch (IOException e) {
            System.err.println("Error in MainServerGameApp StartServer: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        MainServerGameApp server = new MainServerGameApp();
        server.StartServer();
    }
}
