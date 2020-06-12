/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excercise_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class ServerThread extends Thread {

    private DataOutputStream output; // output stream to client
    private DataInputStream input; // input stream from client
    private Socket connection; // connection to client
    private GameManager manager;

    public ServerThread(Socket connection, GameManager manager) {
        this.connection = connection;
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            getStreams();
            processConnection();
            closeConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // get streams to send and receive data
    private void getStreams() throws IOException {
        // set up output stream for data
        output = new DataOutputStream(connection.getOutputStream());
        output.flush(); // flush output buffer to send header information   
        // set up input stream for data
        input = new DataInputStream(connection.getInputStream());
    }

    // process connection with client
    private void processConnection() throws IOException {
        boolean keepPlaying = true;
        output.writeBoolean(keepPlaying);
        while (keepPlaying) {
            Random random = new Random();
            int num1 = random.nextInt(100);
            int num2 = random.nextInt(100);
            int correctResult = num1 * num2;
            output.writeUTF("Ingrese el resultado de la operacion " + num1 + " * " + num2);
            int result = Integer.parseInt(input.readUTF());
            if (manager.isPlayerwinner() == true) {
                output.writeUTF("Ha ganado!");
                keepPlaying = false;
                output.writeBoolean(keepPlaying);
            } else if (correctResult == result) {
                output.writeUTF("Resultado correcto! Sigue el juego!");
                output.writeBoolean(keepPlaying);
            } else {
                output.writeUTF("Resultado incorrecto! Perdio el juego!");
                manager.setPlayerwinner(true);
                keepPlaying = false;
                output.writeBoolean(keepPlaying);
            }
        }
    }

    // close streams and socket
    private void closeConnection() {
        System.out.println("\nTerminating connection");
        try {
            output.close(); // close output stream
            input.close(); // close input stream
            connection.close(); // close socket  
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
