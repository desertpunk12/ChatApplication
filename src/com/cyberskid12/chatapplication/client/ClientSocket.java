package com.cyberskid12.chatapplication.client;

import com.cyberskid12.chatapplication.other.Message;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by dpunk12 on 6/25/2018.
 */
public class ClientSocket extends Thread{

    private String              myIp;
    private String              hostIP;
    private int                 hostPort;
    private Socket              socket;
    private ObjectOutputStream  outputStream;
    private ObjectInputStream   inputStream;

    private JTextArea           textMessages;

    public ClientSocket(String hostIP, int hostPort, JTextArea textMessages){
        this.hostIP     =   hostIP;
        this.hostPort   =   hostPort;
        this.textMessages = textMessages;

        try {
            socket =        new Socket(this.hostIP,this.hostPort);
            outputStream =  new ObjectOutputStream(socket.getOutputStream());
            inputStream =   new ObjectInputStream(socket.getInputStream());
            this.myIp =     socket.getInetAddress().getHostAddress();
            System.out.println("Connected to " + hostIP + ":" + hostPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!socket.isClosed()){
            try {
                Message message = (Message) inputStream.readObject();
                textMessages.append(message + "\n");
                inputStream.
            } catch (EOFException e) {
                e.printStackTrace();
            }catch ( IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendMessage(Message message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

    public void sendMessage(String message){
        new Thread(() -> {
            try {
                Message m = new Message(myIp, message);
                outputStream.writeObject(m);
                outputStream.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();

    }
}
