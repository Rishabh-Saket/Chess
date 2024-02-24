package com.thinking.machines.chess.client;
import com.thinking.machines.nframework.client.*;
public class Main
{
    public static void main(String[] args) 
    {
        try
        {            
            String username=args[0];
            String password=args[1];
            NFrameworkClient client=new NFrameworkClient();
            //String branchName=(String)client.execute("/banking/branchName",args[0]);
            boolean isUserAuthentic=(boolean)client.execute("/ChessServer/authenticateMember",username,password);
            if(isUserAuthentic) System.out.println("Cool you are the right person");
            else System.out.println("Invalid username/password");
        }catch(Throwable t)
        {
            System.out.println(t.getMessage());
        }
    }
}