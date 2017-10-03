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
public class InputMethodException extends Exception
{
    public InputMethodException(String inputMethod)
    {
        super("'" + inputMethod + "' is not a valid command.\n");
    }
}
