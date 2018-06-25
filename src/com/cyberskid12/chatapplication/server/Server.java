package com.cyberskid12.chatapplication.server;

import com.cyberskid12.chatapplication.other.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

    private ServerSocket            serverSocket;
    private int                     port;
    private ArrayList<ClientSocket> clientSockets;
    private ArrayList<Message>      messages;

    public Server(){
        this(1234);
    }

    public Server(int port){
        this.port = port;
        messages = new ArrayList<>();
        clientSockets = new ArrayList<>();
        messages.add(new Message("Server","Hello World"));
        messages.add(new Message("Server","Hello User"));
    }

    public void init() throws IOException {

        serverSocket = new ServerSocket(port);
    }

    public void listen() throws IOException {
        System.out.println("Listening on port: " + port);
        while(!serverSocket.isClosed()) {
            ClientSocket cs = new ClientSocket(serverSocket.accept(),this);
            for (Message message : this.messages) {
                cs.sendMessage(message);
            }
            cs.start();
            sendToAll(new Message("Server",cs.getHostName() + " joined the chat!"));
            clientSockets.add(cs);
            System.out.println("Added a client with IP:" + cs.getHostName());
        }
    }

    public void addMessage(Message message) throws IOException {
        this.messages.add(message);
        sendToAll(message);
    }

    public void sendToAll(Message message) throws IOException {
        for (ClientSocket cs: clientSockets) {
            cs.sendMessage(message);
        }
    }

    public void removeClient(ClientSocket clientSocket){
        clientSockets.remove(clientSocket);
    }


    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.init();
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
