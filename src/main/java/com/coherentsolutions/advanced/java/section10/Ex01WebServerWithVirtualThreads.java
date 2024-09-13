package com.coherentsolutions.advanced.java.section10;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Ex01WebServerWithVirtualThreads demonstrates a simple web server that uses virtual threads to handle requests.
 * It shows how virtual threads can be used to scale efficiently, handling numerous concurrent client connections.
 */
public class Ex01WebServerWithVirtualThreads {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        try (var serverSocket = new ServerSocket(port);
             var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            System.out.println("Server is running on port " + port);
            while (true) {
                // Accept incoming connections
                Socket clientSocket = serverSocket.accept();

                // Handle each client request in a new virtual thread
                executor.submit(() -> handleClientRequest(clientSocket));
            }
        }
    }

    // Simulate handling a client request
    private static void handleClientRequest(Socket clientSocket) {
        try (clientSocket) {
            var inputStream = clientSocket.getInputStream();
            var outputStream = clientSocket.getOutputStream();

            // Read the request (simplified)
            byte[] buffer = new byte[1024];
            inputStream.read(buffer);
            System.out.println("Received request: " + new String(buffer).trim());

            // Write a response
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\nHello, virtual thread!";
            outputStream.write(httpResponse.getBytes());

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
