/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random; 

/**
 *
 * @author nouran
 */
public class ClientHandler extends Thread {
    
    private Socket client;
    private Random rand = new Random();
    
    public ClientHandler(Socket s){
        this.client = s;
    }
    
    @Override
    public void run(){
        
        // thread body of execution
        try
        {
            //  create i/o streams
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());

            //  i/o with client
            while (true)
            {
                //  ask for the start location
                dos.writeUTF("Please enter the start location:");
                dos.flush();
                String start_loc = dis.readUTF();
                
                //  do computations on the computational nodes to get if the start point is crowded or not
                boolean crowded = IsCrowdedNow(start_loc); 
                
                //  ask for the end location
                dos.writeUTF("Please enter the end location:");
                dos.flush();
                String end_loc = dis.readUTF();
                
                //  ask for the max time to arrive
                dos.writeUTF("Please enter the max estimated arrival time:");
                dos.flush();
                String time = dis.readUTF();
                
                //  do computations on the computational nodes to get the best route
                String my_route = calculateBestRoute(start_loc,end_loc,time); 
                dos.writeUTF("The Best Route is: " + my_route + " and The Start Location " + start_loc + " is Crowded Now");
                dos.flush();
                
                //  ask if the user want another calculation
                dos.writeUTF("Successful Best Route Calculation ,Another one [y/n]?");
                dos.flush();
                String user_ans = dis.readUTF();
                
                //  apply checks
                
                if (user_ans.equals("n"))
                {
                    dos.writeUTF("exit");
                    System.out.println("Calculations Done");
                    dos.flush();
                    break;
                }
            }

            //  close connection
            dis.close();
            dos.close();
            client.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    
    //  function to calculate the best route and return it to the user
    private String calculateBestRoute(String start_loc, String end_loc, String time) {
        
        String[] directions = {"go to the right", "go to the left", "go back","rotate"};
        int n = directions.length-1;
        return directions[rand.nextInt()%n];
    }

    private boolean IsCrowdedNow(String start_loc) {
        return true;
    }
    
}
