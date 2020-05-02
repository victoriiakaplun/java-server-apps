package ru.omsu.imit.course3.number_guess;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class ClientServiceThread extends Thread {
    private Socket clientSocket;
    private int clientID;

    public ClientServiceThread(Socket socket, int id) {
        clientSocket = socket;
        clientID = id;
    }

    public void run() {
        int randomNumber = new Random().nextInt(10000);
        int counter = 0;
        int guess;
        System.out.println(
                "Accepted client in game: ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
            while (true) {
                counter++;
                String clientCommand = in.readUTF();
                System.out.println("Client "  + clientID + " says :" + clientCommand);
                if (clientCommand.equalsIgnoreCase("quit")) {
                    System.out.println("Stopping client thread for client : " + clientID);
                    System.out.println("Client " + clientID + " tried " + counter + " times");
                    out.flush();
                    break;
                }
                guess = Integer.parseInt(clientCommand);
                if(guess == randomNumber) {
                    System.out.println("Client win! Client " + clientID + " tried " + counter + " times");
                    out.writeUTF("Its correct number! You tried " + counter + " times!");
                    out.flush();
                    break;
                }
                if(guess < randomNumber) {
                    out.writeUTF("Less than need!");
                    out.flush();
                }
                if(guess > randomNumber) {
                    out.writeUTF("More than need!");
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Closing socket");
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
