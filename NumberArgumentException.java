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
public class NumberArgumentException extends Exception
{
    public NumberArgumentException()
    {
        super("Incorrect number of arguments supplied to command.\n");
    }
}
