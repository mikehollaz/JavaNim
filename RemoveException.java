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
public class RemoveException extends Exception
{
    public RemoveException(int bound)
    {
        super("\nInvalid move. You must remove between 1 " + 
                        "and " + bound + " stones.\n");
    }
}
