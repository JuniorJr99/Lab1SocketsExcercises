/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excercise_3;

/**
 *
 * @author Jose Cespedes
 */
public class GameManager {
     private boolean playerwinner = false;

    public synchronized boolean isPlayerwinner() {
        return playerwinner;
    }

    public synchronized void setPlayerwinner(boolean playerwinner) {
        this.playerwinner = playerwinner;
    }
}
