package ru.omsu.imit.course3.number_guess;

import ru.omsu.imit.course3.number_guess.ClientServiceThread;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerThreadPool {
    static int port = 6666;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("Server started and ready to accept client requests");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            int id = 0;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ClientServiceThread(clientSocket, id++));
            }
        }
        // executorService.shutdown();
    }
}