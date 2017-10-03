/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// package nimsys;

import java.util.StringTokenizer;

/**
 *
 * @author mikeh
 */
public class NimHumanPlayer extends NimPlayer
{
    
    
    public NimHumanPlayer()
    {
        this.type = MethodManager.HUMAN;
        this.userName = "";
        this.givenName = "";
        this.familyName = "";
        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }
    
}
