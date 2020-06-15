/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excercise_3;

import Excercise_3.ServerThread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ronny Mairena B64062 Junior Corrales B72388
 */
public class Server {

    private Socket connection; // connection to client
    private ServerSocket server; // server socket
    private final int PORT = 12345;
    private Socket[] players = new Socket[2];
    GameManager manager = new GameManager();

    // set up and run server
    public void runServer() {
        try {
            server = new ServerSocket(PORT); // create ServerSocket
            int count = 0;
            while (count <= 2) {
                connection = waitForConnection();
                players[count] = connection;
                if (count == 1) {
                    for (int i = 0; i < 2; i++) {
                        ServerThread serverThread = new ServerThread(players[i], manager);
                        serverThread.start();
                    }
                }
                count++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();// close connection            
        }
    }

    // wait for connection to arrive, then display connection info
    private Socket waitForConnection() throws IOException {
        System.out.println("Waiting for connection...\n");
        connection = server.accept(); // allow server to accept connection
        System.out.println("Connection received from: " + connection.getInetAddress().getHostName());
        return connection;
    }

    // close streams and socket
    private void closeConnection() {
        System.out.println("\nTerminating connection");
        try {
            connection.close(); // close socket  
            server.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().runServer();
    }
}

