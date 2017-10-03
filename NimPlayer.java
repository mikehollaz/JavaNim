/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// package nimsys;
import java.util.StringTokenizer;
import java.text.DecimalFormat;

/**
 *
 * @author mikeh
 */

public class NimPlayer
{
    public String userName;
    public String givenName;
    public String familyName;
    public int gamesWon;
    public int gamesPlayed;
    public int type;
    
    DecimalFormat games = new DecimalFormat("#00");
    
    public void NimPlayer(NimPlayer player)
    {
        this.userName = player.userName;
        this.givenName = player.givenName;
        this.familyName = player.familyName;
        this.gamesWon = player.gamesWon;
        this.gamesPlayed = player.gamesPlayed;
    }
    
    //Default constructor
    public NimPlayer()
    {
        
        userName = "";
        givenName = "";
        familyName = "";
        gamesPlayed = 0;
        gamesWon = 0;
        
    }
    
    public static NimPlayer addplayer(String usr, String fam, String giv,
            int won, int played)
    {
        NimPlayer player = new NimPlayer();
        
        player.userName = usr;
        player.familyName = fam;
        player.givenName = giv;
        player.gamesWon = won;
        player.gamesPlayed = played;
        
        return player;
        
    }
    
    
    public static int findPlayerPosition(String argument)
    {   
        String userName = "";
        
        userName = MethodManager.tokenizeNames(argument, MethodManager.USR);
        
        for (int i = 0; i < Nimsys.NimPlayerArray.length; i++)
        {
            if (userName.equals(Nimsys.NimPlayerArray[i].getUserName()))
            {
                return i;
            }
        }
        
        return MethodManager.INVALID;
    }
    
    
    public static void removeplayer(String argument)
    {
        String userName;
        
        userName = MethodManager.tokenizeNames(argument, MethodManager.USR);
        
        int position = findPlayerPosition(userName);
        
        for (int i = position; i < Nimsys.NUMBER_PLAYERS; i++)
        {
            Nimsys.NimPlayerArray[i] = Nimsys.NimPlayerArray[i+1];
        }
        
        Nimsys.NUMBER_PLAYERS--;
    }
    
    public static void removeplayer()
    {
        System.out.print("Are you sure you want to remove all players? (y/n)\n");
        if (Nimsys.keyboard.next().equalsIgnoreCase("y"))
        {
            for (int i = 0; i < Nimsys.NUMBER_PLAYERS; i++)
            {
                Nimsys.NimPlayerArray[i] = new NimPlayer();
            }
            System.out.print("\n");
        }
        else
        {
            System.out.print("\n");
        }
        Nimsys.NUMBER_PLAYERS = 0;
    }
    
    public static void editplayer(String argument)
    {
        String userName = MethodManager.tokenizeNames(argument,MethodManager.USR);
        String newFamilyName = MethodManager.tokenizeNames(argument,MethodManager.FAM);
        String newGivenName = MethodManager.tokenizeNames(argument,MethodManager.GIV);
        
        int position = findPlayerPosition(userName);
        
        Nimsys.NimPlayerArray[position].editplayer(newFamilyName, newGivenName);
    }
    
    private void editplayer(String newFam, String newGiv)
    {
        this.familyName = newFam;
        this.givenName = newGiv;
    }
    
    public void resetstats(NimPlayer player)
    {
        player.gamesWon = 0;
        player.gamesPlayed = 0;
    }
    
    public static void resetstats()
    {
        //reset all player stats
        String response;
        System.out.print("Are you sure you want to reset all player statistics? (y/n)\n");
        response = Nimsys.keyboard.next();
        if (response.equalsIgnoreCase("y"))
        {
            for (int i = 0; i < Nimsys.NUMBER_PLAYERS; i++)
            {
                Nimsys.NimPlayerArray[i].resetstats(Nimsys.NimPlayerArray[i]);
            }
            System.out.println("");
        }
        else
        {
            //dont do anything
            System.out.println("");
        }
    }
    
    public void displayplayer(NimPlayer player)
    {
        
        System.out.print(player.userName + "," + player.givenName + 
                "," + player.familyName + "," + player.gamesPlayed + 
                " games," + player.gamesWon + " wins\n");
    }
    
    //Change argument
    public static void displayplayer()
    {
        for (int i = 0; i < Nimsys.NUMBER_PLAYERS; i++)
        {
            Nimsys.NimPlayerArray[i].displayplayer(Nimsys.NimPlayerArray[i]);
        }
    }
    
    //show player rankings
    public void rankings(NimPlayer player)
    {
        int percent = (int)Math.round(player.getPercentage());
        
        //number format games played '03'
        System.out.printf("%-5s", percent + "%");
        System.out.print("| ");
        System.out.print(games.format(player.gamesPlayed));
        System.out.print(" games" + " | " + player.givenName + " " + player.familyName + "\n");
    }
    
    //Methods for Gameplay
    
    //Method asks player how many stones they wish to remove
    public int removeStone(NimPlayer player, int stones, NimGame currentGame)
    {
        int remove, remain;
        System.out.print(player.givenName + "'s turn - remove how many?\n");
        remove = Nimsys.keyboard.nextInt();
        
        if (!currentGame.checkRemove(remove))
        {
            currentGame.printAsterisk();
            return this.removeStone(player, stones, currentGame);
        }
        
        System.out.print("\n");
        remain = stones - remove;
        return remain;
    }
    
    public void IWon(NimPlayer player)
    {
        player.gamesWon += 1;
        player.gamesPlayed += 1;
    }
    
    public void ILost(NimPlayer player)
    {
        player.gamesPlayed += 1;
    }
    
    public String getGivenName()
    {
        //return new object's given name, change to avoid privacy leak
        return givenName;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public String getFamilyName()
    {
        return familyName;
    }
    
    public double getPercentage()
    {
        if (gamesPlayed == 0)
        {
            return 0;
        }
        else
        {
            return (double)gamesWon/((double)gamesPlayed)*100;
        }
    }
    
    public int getGames()
    {
        return gamesPlayed;
    }
    
    public int getGamesWon()
    {
        return gamesWon;
    }
    
    public int getGamesLost()
    {
        return (gamesPlayed - gamesWon);
    }
    
    public int getType()
    {
        return type;
    }
}
