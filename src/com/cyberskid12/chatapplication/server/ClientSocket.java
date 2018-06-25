package com.cyberskid12.chatapplication.server;

import com.cyberskid12.chatapplication.other.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClientSocket extends Thread{

    private String              hostName;
    private Socket              socket;
    private Server              server;
    private ObjectOutputStream  outputStream;
    private ObjectInputStream   inputStream;

    public ClientSocket(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        hostName = socket.getInetAddress().getHostName();
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream  = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!socket.isClosed() && socket.isConnected()){
            try {
                Message m = (Message)inputStream.readObject();
                server.addMessage(m);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void sendMessage(Message message) throws IOException{
        outputStream.writeObject(message);
        outputStream.flush();
        outputStream.reset();
    }

    public String getHostName() {
        return hostName;
    }
}
