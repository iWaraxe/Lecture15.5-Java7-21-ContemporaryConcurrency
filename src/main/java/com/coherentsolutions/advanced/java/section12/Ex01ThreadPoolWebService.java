package com.coherentsolutions.advanced.java.section12;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ex01ThreadPoolWebService demonstrates a simple web server using traditional thread pools.
 * The task is to later replace this model with virtual threads and structured concurrency.
 */
public class Ex01ThreadPoolWebService {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ExecutorService threadPool = Executors.newFixedThreadPool(10);  // Traditional thread pool

        try (var serverSocket = new ServerSocket(port)) {
            System.out.println("Web server is running on port " + port);

            while (true) {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();

                // Handle each request in a separate thread from the thread pool
                threadPool.submit(() -> handleRequest(clientSocket));
            }
        } finally {
            threadPool.shutdown();
        }
    }

    // Simulate handling an HTTP request from the client
    private static void handleRequest(Socket clientSocket) {
        try (clientSocket) {
            var inputStream = clientSocket.getInputStream();
            var outputStream = clientSocket.getOutputStream();

            // Read the client request (simplified)
            byte[] buffer = new byte[1024];
            inputStream.read(buffer);
            String request = new String(buffer).trim();
            System.out.println("Received request: " + request);

            // Simulate handling the request and print which thread handles the request
            System.out.println("Task handled by: " + Thread.currentThread().getName());

            // Write a simple HTTP response
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\nHello from the traditional thread pool web service!";
            outputStream.write(httpResponse.getBytes());

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
