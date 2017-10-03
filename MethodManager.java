
// package nimsys;

import java.util.StringTokenizer;

/*
    This Class holds all the auxiliary methods that support the main classes.
    Hence all the methods and variables are public.
*/

/**
 *
 * @author mikeh
 */
public class MethodManager 
{
    public static final int USR = 1;
    public static final int FAM = 2;
    public static final int GIV = 3;
    public static final int ASC = 1;
    public static final int DESC = 2;
    public static final int ALPHA = 3;
    public static final int INVALID = -1;
    public static final int TOPTEN = 10;
    public static final int HUMAN = 0;
    public static final int AI = 1;
    

    
    private static int tokenCounter(String argument)
    {
        StringTokenizer tokenCounter = new StringTokenizer(argument, ",");
        int tokensCounted = tokenCounter.countTokens();
        
        return tokensCounted;
    }
    
    
    public static void addPlayerMethod(String argument, int type, boolean fromFile)
    {
        try
        {

            //Initialise temporary values
            String userNametemp;
            String familyNametemp;
            String givenNametemp;
            int gamesWontemp;
            int gamesPlayedtemp;
            String typetemp;

            
            //Check if argument is valid
            int addSafe = 3;

            StringTokenizer nameTokens = new StringTokenizer(argument, ",");
            int numberNameTokens = tokenCounter(argument);

            //throws if argument is invalid
            safeArgument(numberNameTokens, addSafe);

            //if reading from from user input
            userNametemp = nameTokens.nextToken();
            familyNametemp = nameTokens.nextToken();
            givenNametemp = nameTokens.nextToken();
            gamesWontemp = 0;
            gamesPlayedtemp = 0;
            
            
            //If reading from data file, go here
            if (fromFile)
            {
                gamesWontemp = Integer.parseInt(nameTokens.nextToken());
                gamesPlayedtemp = Integer.parseInt(nameTokens.nextToken());
                typetemp = nameTokens.nextToken();
            }


            if (checkName(argument) && type == HUMAN)
            {
                Nimsys.NimPlayerArray[Nimsys.NUMBER_PLAYERS] = NimHumanPlayer.addplayer(userNametemp, 
                        familyNametemp, givenNametemp, gamesWontemp, gamesPlayedtemp);
                Nimsys.NUMBER_PLAYERS++;
                if (!fromFile)
                {
                    System.out.print("\n");
                }
            }
            else if (checkName(argument) && type == AI)
            {
                Nimsys.NimPlayerArray[Nimsys.NUMBER_PLAYERS] = NimAIPlayer.addplayer(userNametemp, 
                        familyNametemp, givenNametemp, gamesWontemp, gamesPlayedtemp);
                Nimsys.NUMBER_PLAYERS++;
                if (!fromFile)
                {
                    System.out.print("\n");
                }
            }
            else 
            {
                DoesExist();
            }
        }
        catch (NumberArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static void removePlayerMethod(String argument)
    {
        try
        {
            //check valid argument
            int tokenCount = tokenCounter(argument);
            int safe = 1;
            
            //throws if invalid argument
            safeArgument(tokenCount, safe);
            
            if (argument.equals(" "))
            {
                NimPlayer.removeplayer();
            }
            else
            {
                if (!checkName(argument))
                {
                    NimPlayer.removeplayer(argument);
                    System.out.print("\n");
                }
                else
                {
                    DoesNotExist();
                }
            }
        }
        
        catch (NumberArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static void editPlayerMethod(String argument)
    {
        try
        {
            //check valid argument
            int tokenCount = tokenCounter(argument);
            int safe = 1;
            
            //throws if invalid argument
            safeArgument(tokenCount, safe);
            
            
            if (!checkName(argument))
            {
                NimPlayer.editplayer(argument);
                System.out.print("\n");
            }
            else
            {
                DoesNotExist();
            }
        }
        
        catch (NumberArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static void resetStatsMethod(String argument)
    {
        try
        {
            //check valid argument
            int tokenCount = tokenCounter(argument);
            int safe = 1;
            
            //throws if invalid argument
            safeArgument(tokenCount, safe);
            
            if (argument.equals(" "))
            {
                NimPlayer.resetstats();
            }
            //player exists
            else if (!checkName(argument))
            {
                resetSingle(argument);
            }
            //player does not exist
            else
            {
                DoesNotExist();
            }
        }
        
        catch (NumberArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static void displayPlayerMethod(String argument)
    {
        try
        {
            //check valid argument
            int tokenCount = tokenCounter(argument);
            int safe = 1;
            
            //throws if invalid argument
            safeArgument(tokenCount, safe);
            
            if (argument.equals(" "))
            {
                sort(ALPHA);
                NimPlayer.displayplayer();
                System.out.print("\n");
            }
            //player exists
            else if (!checkName(argument))
            {
                int i = NimPlayer.findPlayerPosition(argument);
                Nimsys.NimPlayerArray[i].displayplayer(Nimsys.NimPlayerArray[i]);
                System.out.print("\n");
            }
            //player does not exist
            else
            {
                DoesNotExist();
            }
        }
        
        catch (NumberArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static void rankingsMethod(String argument)
    {
        try
        {
            //check valid argument
            int tokenCount = tokenCounter(argument);
            int safe = 1;
            
            //throws if invalid argument
            safeArgument(tokenCount, safe);
            
            //Can use tokenizeNames method to extract correct input argument
            //if more than one is provided
            String rankingArgument = tokenizeNames(argument, USR);
            
            if (rankingArgument.equals("asc"))
            {
                sort(ALPHA);
                sort(ASC);
                printRank();
            }
            else if (rankingArgument.equals("desc") || argument.equals(" "))
            {
                sort(ALPHA);
                sort(DESC);
                printRank();
            }
        }
        
        catch (NumberArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static String tokenizeNames(String argument, int whichName)
    {
        StringTokenizer names = new StringTokenizer(argument,",");
        
        //Clear the variables
        String userName = null;
        String familyName = null;
        String givenName = null;
        
        int tokens = names.countTokens();
        
        //if no tokens, then no argument received
        if (tokens == 0)
        {
            return "";
        }
        
        //Number of tokens counted are the different names
        if (tokens == 1)
        {
            userName = names.nextToken();
        }
        
        else if (tokens == 2)
        {
            userName = names.nextToken();
            familyName = names.nextToken();
        }
        
        else if (tokens >=3)
        {
            userName = names.nextToken();
            familyName = names.nextToken();
            givenName = names.nextToken();
        }
     
        
        //User asks for which name they want returned
        switch (whichName)
        {
            case USR:
                return userName;
            case FAM:
                return familyName;
            case GIV:
                return givenName;
            default:
                return null;
        }
        
    }
    
    //The following sort functions (sort, sortPlayer, swap)
    //using selection sort are defined in textbook
    //Absolute Java 5th Edition - W.Savitch, K. Mock
    //Chapter 6, page 390
    public static void sort(int whichWay)
    {
        int nextSmallest;
        
        for (int i = 0; i < Nimsys.NUMBER_PLAYERS; i++)
        {
            nextSmallest = sortPlayer(i, Nimsys.NimPlayerArray, whichWay);
            swap(i, nextSmallest, Nimsys.NimPlayerArray);
        }
    }
    
    //takes which way the sort needs to happen and compares entries appropriately
    private static int sortPlayer(int startIndex, NimPlayer[] player, int whichWay)
    {
        double smallest = player[startIndex].getPercentage();
        String smallestAlpha = player[startIndex].getUserName();
        int indexOfSmall = startIndex;
        int index;
        
        for (index = startIndex + 1; index < Nimsys.NUMBER_PLAYERS; index++)
        {
            if (whichWay == ASC)
            {
                if (player[index].getPercentage() < smallest)
                {
                    smallest = player[index].getPercentage();
                    indexOfSmall = index;
                }
            }
            else if (whichWay == DESC)
            {
                if (player[index].getPercentage() > smallest)
                {
                    smallest = player[index].getPercentage();
                    indexOfSmall = index;
                }
            }
            else if (whichWay == ALPHA)
            { 
                if (player[index].getUserName().compareToIgnoreCase(smallestAlpha) < 0)
                {
                    smallestAlpha = player[index].getUserName();
                    indexOfSmall = index;
                } 
            }
            
        }
        return indexOfSmall;
    }
    
    private static void swap(int i, int j, NimPlayer[] player)
    {
        NimPlayer temp;
        temp = player[i];
        player[i] = player[j];
        player[j] = temp;
    }
    
    //returns true if array does not contain a use with the same username.
    private static boolean checkName(String argument)
    {
        String userName = tokenizeNames(argument,USR);
        
        for (int i = 0; i < Nimsys.NUMBER_PLAYERS; i++)
        {
            if (userName.equals(Nimsys.NimPlayerArray[i].getUserName()))
            {
                return false;
            }
        }
        return true;
    }
    
    private static void DoesExist()
    {
        System.out.println("The player already exists.\n");
    }
    
    private static void DoesNotExist()
    {
        System.out.println("The player does not exist.\n");
    }
    
    private static void printRank()
    {
        //Only prints equal to or less than ten entries
        for (int i = 0; i < min(TOPTEN, Nimsys.NUMBER_PLAYERS); i++)
        {
            Nimsys.NimPlayerArray[i].rankings(Nimsys.NimPlayerArray[i]);
        }
        System.out.print("\n");
    }
    
    //Used to compare if the number of players is less than ten
    private static int min(int a, int b)
    {
        if (a < b)
        {
            return a;
        }
        else return b;
    }
    
    //Resets a single player
    private static void resetSingle(String argument)
    {
        int i = NimPlayer.findPlayerPosition(argument);
        Nimsys.NimPlayerArray[i].resetstats(Nimsys.NimPlayerArray[i]);
        System.out.print("\n");
    }
    
    //Method removes blank space or new line character in input line
    public static String removeJunk(String junk)
    {
        String inputLine;
        
        if (junk.equals("")||junk.equals("\n"))
        {
            inputLine = Nimsys.keyboard.nextLine();
        }
        else
        {
            inputLine = junk;
        }
        
        return inputLine;
    }
    
    //Returns the input argument and also checks if there is not argument
    public static String getArgument(StringTokenizer input)
    {
        String argument;
        
        if (input.hasMoreTokens())
        {
            //use this as input for each method
            argument = input.nextToken();
        }
        else
        {
            argument = " ";
        }
        
        return argument;
    }
    
    private static void safeArgument(int counted, int safe) throws NumberArgumentException
    {
        if (counted < safe)
        {
            throw new NumberArgumentException();
        }
    }
    
}
