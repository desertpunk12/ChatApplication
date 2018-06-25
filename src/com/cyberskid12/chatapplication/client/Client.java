package com.cyberskid12.chatapplication.client;

import com.cyberskid12.chatapplication.other.Message;

import javax.swing.*;
import java.io.IOException;

public class Client{

    private ClientSocket clientSocket;

    private JPanel pnlMain;
    private JTextArea txtSendMessage;
    private JTextArea txtMessages;
    private JButton btnSendMessage;


    public Client(String hostIP, int port){
        clientSocket = new ClientSocket(hostIP,port,txtMessages);
        clientSocket.start();
    }

    public Client(){
        this("localhost",1234);
        actionListeners();
    }

    private void actionListeners() {
        btnSendMessage.addActionListener(e -> {
//            try {
                clientSocket.sendMessage(txtSendMessage.getText());
                txtSendMessage.setText("");
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
        });
    }


    public JPanel getMainPanel(){
        return pnlMain;
    }
    public static void main(String[] args) {
        Client client = new Client();
        JFrame frame = new JFrame("Client");
        frame.setContentPane(client.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}
