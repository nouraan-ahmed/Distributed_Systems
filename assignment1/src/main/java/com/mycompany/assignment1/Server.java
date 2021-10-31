/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author nouran
 */
public class Server {
    public static void main(String[] args)
    {
        try
        {
            //  open server socket
            ServerSocket sv = new ServerSocket(2000);
            System.out.println("Connection started Server is Running...");
       
                while (true)
                {
                    //  accept connection
                    Socket s = sv.accept();
                    System.out.println("Connection Established Client is Accepted...");
             
                    ClientHandler thr = new ClientHandler(s);
                    thr.start();
                }

        }
            
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
