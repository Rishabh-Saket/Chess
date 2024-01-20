package com.thinking.machines.chess.server;
import com.thinking.machines.chess.server.dl.*;
import java.util.*;
public class ChessServer
{
    private Map<String,String> members;
    private Set<String> loggedInMembers;    
    private Set<String> playingMembers;
    private Map<String,Message> inboxes;
    private Map<String,Game> games;
    public ChessServer()
    {
        populateDataStructures();
    }

    
    public void populateDataStructures()
    {
        //something
    }

    //create services to enable log-in,log-out,etc..
}