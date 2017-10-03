/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// package nimsys;


/**
 *
 * @author mikeh
 */
public class NimAIPlayer extends NimPlayer
{
    private int previousStones = 0;
    private boolean state;
    private int upperBound;
    
    
    public NimAIPlayer()
    {
        super();
        this.type = MethodManager.AI;
    }
    
    public static NimAIPlayer addplayer(String usr, String fam, String giv,
            int won, int played)
    {
        NimAIPlayer player = new NimAIPlayer();
        
        player.userName = usr;
        player.familyName = fam;
        player.givenName = giv;
        player.gamesWon = won;
        player.gamesPlayed = played;
        
        
        return player;
    }
    
    
    @Override
    public int removeStone(NimPlayer player, int stones, NimGame currentGame)
    {
        upperBound = NimGame.upperBound;
        
        System.out.print(player.givenName + "'s turn - remove how many?\n");
        
        //Check the remainder of remainder/k(upperBound + 1)
        //Want the AI to always have k(M+1) stones left
        //Include checkRemove
        int remove;
        
        //Concede defeat
        if (stones == 1)
        {
            remove = 1;
            System.out.print("\n");
            return stones - remove;
        }
        
        
        //Maintains control over the M+1 state
        if (stones%(upperBound + 1) == 0)
        {
            remove = upperBound;
            
            
            System.out.print("\n");
            state = true;
            previousStones = stones - remove;
            return stones - remove;
        }
        
        
        else
        {
            //Want to move AI into the M+1 state
            remove = upperBound - (stones%((upperBound + 1)));
            
            
            //in the k(M+1)+1 state, add round of turns to be M+1 total
            if (state)
            {
                remove = (upperBound+1) - (previousStones - stones);
            }
            
            
            //Looks for the situation where the other player is forced into
            //the k(M+1)+1 situation as prescribed in the project description
            else
            {
                for (int i = 1; i <= upperBound; i++)
                {
                    if ((stones - i)%(upperBound + 1) == 1)
                    {
                        remove = i;
                    }
                }
            }
           
            //Cannot remove less than 0
            if (remove <= 0)
            {
                remove = upperBound;
            }
            
            //Special case if in the last range of [1,M)
            if (stones < upperBound)
            {
                remove = upperBound - (upperBound + 1 - stones);
            }
            
            //Special case where the remaining stones = the upperBound
            else if (stones == upperBound)
            {
                remove = upperBound - 1;
            }
            
            //Cannot remove more than the upperBound
            if (remove > upperBound)
            {
                remove = remove - upperBound;
            }
            
            
            System.out.print("\n");
            previousStones = stones - remove;
            return stones - remove;
        }
    }
}
