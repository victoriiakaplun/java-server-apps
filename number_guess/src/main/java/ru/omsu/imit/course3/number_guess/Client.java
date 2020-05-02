package ru.omsu.imit.course3.number_guess;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] ar) {
        final int serverPort = 6666;
        final String address = "localhost";
        InetAddress ipAddress;
        try {
            ipAddress = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            return;
        }
        try (Socket socket = new Socket(ipAddress, serverPort);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in, "CP866"))) {

            System.out.println(socket.getLocalPort());
            String line = null;
            System.out.println("Welcome to the game!");
            System.out.println("You should guess number between 0 and 10000. Let`s start!");
            System.out.println("Type 'quit' to exit");

            while (true) {
                line = keyboardReader.readLine();
                System.out.println("Sending this line to the server..." + line);
                out.writeUTF(line);
                out.flush();
                if (line.equalsIgnoreCase("quit")) {
                    System.out.println("Client stopped");
                    break;
                }
                if(line.equalsIgnoreCase("Its correct number! You tried %s times!")) {
                    System.out.println(line);
                    break;
                }
                line = in.readUTF();
                System.out.println("Server answer is : " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}