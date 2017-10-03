/*
Program Nimsys and associated Java files Written by Michael Holloway 640880
*/
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;


/**
 *
 * @author mikeh
 */
public class Nimsys 
{
    
    public static Scanner keyboard = new Scanner(System.in);
    public static ObjectInputStream inputStream = null;
    public static final int MAX_PLAYERS = 100;
    public static int NUMBER_PLAYERS = 0;
    public static NimPlayer[] NimPlayerArray = new NimPlayer[MAX_PLAYERS];
    
    //Welcome message
    private static void welcome()
    {
        //Welcome Message
        System.out.println("Welcome to Nim");
        System.out.print('\n');
    }
    
    public static void ScanFile()
    {
        try 
        {
            inputStream = new ObjectInputStream(new FileInputStream("players.dat"));
            
            //Clear Array
            for (int i=0; i < NimPlayerArray.length; i++)
            {
                NimPlayerArray[i] = new NimPlayer();
            }
            
            
            int i = 0;
            //Set number of players
            int playerCount = inputStream.readInt();
            
            
            //Read 6 values that describe the player statistics into a string,
            //this string is entered into the addplayer method. The type is 
            //also read such that the appropriate type of player can be added.
            while (i < playerCount)
            {
                String UTFLine = inputStream.readUTF() + inputStream.readUTF()
                    + inputStream.readUTF() + inputStream.readUTF()
                    + inputStream.readUTF() + inputStream.readUTF();
                int type = Integer.parseInt(inputStream.readUTF());
                
                //true means that the addPlayerMethod recognises the input
                //coming from the data file
                MethodManager.addPlayerMethod(UTFLine, type, true);
                
                i++;
            }
            inputStream.close();
        }
        
        catch(EOFException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        
    }
    
    public static void WriteFile()
    {
        try
        {
            ObjectOutputStream playerData = new ObjectOutputStream(new FileOutputStream("players.dat"));
            
            //sort alphabetically
            MethodManager.sort(MethodManager.ALPHA);
            
            //Write number of players
            playerData.writeInt(NUMBER_PLAYERS);
            
            //Write player statistics
            for (int i = 0; i < NUMBER_PLAYERS; i++)
            {
                playerData.writeUTF(NimPlayerArray[i].getUserName() + ",");
                playerData.writeUTF(NimPlayerArray[i].getFamilyName() + ",");
                playerData.writeUTF(NimPlayerArray[i].getGivenName() + ",");
                playerData.writeUTF(NimPlayerArray[i].getGamesWon() + ",");
                playerData.writeUTF(NimPlayerArray[i].getGames() + ",");
                playerData.writeUTF(NimPlayerArray[i].getGamesLost() + ",");
                playerData.writeUTF(NimPlayerArray[i].getType() + "");
            }
            playerData.close();
        }
        
        catch (FileNotFoundException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
    }
/**
 * 
 * 
 * @param args 
 */
    public static void main(String[] args) 
    {
        
        
        //Initialise all spaces in array to Empty Player
        //Using default constructor
        for (int i=0; i < NimPlayerArray.length; i++)
        {
            NimPlayerArray[i] = new NimPlayer();
        }
        
        //Open connection to data file
        ScanFile();
        
        //Welcome message
        welcome();
        
        boolean on = true;
        
        //loop until exit reached
        while (on)
        {
            System.out.print("$");
            
            //Read user input
            String junk = keyboard.nextLine();
            
            //Remove blank spaces, empty strings and new line characters
            String inputLine = MethodManager.removeJunk(junk);
            
            //Break up input into string tokens
            StringTokenizer input = new StringTokenizer(inputLine, " \n");
            
            //Separate methods and their arguments
            String inputMethod = input.nextToken();
            String argument = MethodManager.getArgument(input);
            
            try
            {
                if (inputMethod.equals("addplayer"))
                {   
                    MethodManager.addPlayerMethod(argument, MethodManager.HUMAN, false);

                }
                else if (inputMethod.equals("addaiplayer"))
                {
                    MethodManager.addPlayerMethod(argument, MethodManager.AI, false);
                    
                }
                else if (inputMethod.equals("removeplayer"))
                {
                    MethodManager.removePlayerMethod(argument);

                }
                else if (inputMethod.equals("editplayer"))
                {
                    MethodManager.editPlayerMethod(argument);

                }
                else if (inputMethod.equals("resetstats"))
                {
                    MethodManager.resetStatsMethod(argument);

                }
                else if (inputMethod.equals("displayplayer"))
                {
                    MethodManager.displayPlayerMethod(argument);

                }
                else if (inputMethod.equals("rankings"))
                {
                    MethodManager.rankingsMethod(argument);

                }
                else if (inputMethod.equals("startgame"))
                {
                    NimGame newGame = new NimGame();
                    
                    newGame.startgame(argument);

                }
                else if (inputMethod.equals("exit"))
                {
                    System.out.print("\n");
                    WriteFile();
                    System.exit(0);
                    
                    on = false;
                }
                else
                {
                    on = true;
                    throw new InputMethodException(inputMethod);
                }
            }
            
            catch(InputMethodException e)
            {
                System.out.println(e.getMessage());
            }
        }
        
    }
}
