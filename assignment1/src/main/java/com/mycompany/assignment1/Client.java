/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author nouran
 */
public class Client {
    
    public static void main(String[] args){
    
    Scanner scanner = new Scanner(System.in);
        try
        {
            //  portnumber: 2000
            Socket s = new Socket("127.0.0.1", 2000);
            
            //  create i/o streams 
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            
            //  i/o with the server
            while (true)
            {
               
                String server_response = dis.readUTF();
                System.out.println(server_response);
                if(server_response.equals("exit")){
                    System.out.print("Connection Closed");
                    break;
                }

                String user_message = scanner.next();
                dos.writeUTF(user_message);
                
                //flush the buffer
                dos.flush();
                   
            }
            //  close connections
            dis.close();
            dos.close();
            s.close();
            
        } 
        //  exception handling
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }   
    }
    
}
