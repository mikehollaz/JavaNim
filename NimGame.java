


import java.util.StringTokenizer;

/**
 *
 * @author mikeh
 */
public class NimGame 
{
    private static int initialStone = 0;
    private int remainder = 0;
    public static int upperBound = 0;
    private final int MINIMUM = 1;
    
    //Method to begin games. Takes argument and splits 
    //into numbers and userNames.
    public void startgame(String argument)
    {
        StringTokenizer inputArgument = new StringTokenizer(argument, ",");
        
        NimPlayer P1, P2;
        
        initialStone = Integer.parseInt(inputArgument.nextToken());
        upperBound = Integer.parseInt(inputArgument.nextToken());
        String player1UserName = inputArgument.nextToken();
        String player2UserName = inputArgument.nextToken();
        
        int player1Position = NimPlayer.findPlayerPosition(player1UserName);
        int player2Position = NimPlayer.findPlayerPosition(player2UserName);
        
        //check if the players exist
        if (player1Position == MethodManager.INVALID  || player2Position == MethodManager.INVALID)
        {
            System.out.println("One of the players does not exist.\n");
        }
        
        //else, the players do exist, so keep going :)
        else
        {
        
            P1 = Nimsys.NimPlayerArray[player1Position];
            P2 = Nimsys.NimPlayerArray[player2Position];
            

            printInfo(P1, P2);

            //remainder will reduce as the players take turns
            this.remainder = initialStone;

            //Display initial number of stones as asterisks. 
            printAsterisk();

            //Initiates the loop for the players taking turns
            takeTurns(P1, P2);
        }
    }
    
    
    //Check if the remove amount requested is within the bounds
    public boolean checkRemove(int remove)
    {
        try
        {
            if (remove > upperBound)
            {
                throw new RemoveException(upperBound);
            }
            if (remove > remainder || remove < MINIMUM)
            {
                throw new RemoveException(remainder);
            }
        }
        
        catch (RemoveException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    //Method prints the remainder of stones as asterisks. Loops until
    //the amount of stones is reached
    public void printAsterisk () 
    {
        int i;
        System.out.print(this.remainder + " stones left:");

        for (i = 0; i < this.remainder; i++) 
        {
            System.out.print(" *");
        }
        
        System.out.println("");
    }
    
    
    //Method initiates player turns, the loop will end when remainder reaches zero
    private void takeTurns(NimPlayer PlayerOne, NimPlayer PlayerTwo)
    {
        //Initialise string Winner
        NimPlayer Winner = null;
        NimPlayer Loser = null;

        //Loop alternates player turns, 
        while (remainder > 0) 
        {
            //Player 1 turn
            this.remainder = PlayerOne.removeStone(PlayerOne, this.remainder, this);
            Winner = PlayerTwo;
            Loser = PlayerOne;

            //exit loop if remainder is 0
            if (remainder == 0) 
            {
                break;
            }

            //Show how many stones are left
            printAsterisk();


            //Player 2 turn
            this.remainder = PlayerTwo.removeStone(PlayerTwo, this.remainder, this);
            Winner = PlayerOne;
            Loser = PlayerTwo;

            //exit loop if remainder is 0
            if (remainder == 0) 
            {
                break;
            }

            //Show how many stones are left
            printAsterisk();
        }

        //Once loop is finished remainder is zero means game over & winner output 
        System.out.println("Game Over");
        System.out.println(Winner.getGivenName() + " " + Winner.getFamilyName() + " wins!\n");
        Winner.IWon(Winner);
        Loser.ILost(Loser);
    }
    
    public void printInfo(NimPlayer P1, NimPlayer P2)
    {
        System.out.print("\n");
        System.out.println("Initial stone count: " + initialStone);
        System.out.println("Maximum stone removal: " + upperBound);
        System.out.println("Player 1: " + P1.getGivenName() + " " + P1.getFamilyName());
        System.out.println("Player 2: " + P2.getGivenName() + " " + P2.getFamilyName());
        System.out.print("\n");
    }
}
