package com.thinking.machines.chess.server;
import com.thinking.machines.chess.server.dl.*;
import com.thinking.machines.nframework.server.*;
import com.thinking.machines.nframework.server.annotations.*;
import java.util.*;
@Path("/ChessServer")
public class ChessServer
{
    private static Map<String,Member> members;
    private static Set<String> loggedInMembers;    
    private static Set<String> playingMembers;
    private static Map<String,List<Message>> inboxes;
    private static Map<String,Game> games;
    static
    {
        populateDataStructures();
    }
    
    private static void populateDataStructures()
    {
        MemberDAO memberDAO=new MemberDAO();
        List<MemberDTO> dlMembers=memberDAO.getAll();
        Member member;
        members=new HashMap<>();
        for(MemberDTO memberDTO:dlMembers)
        {
            member=new Member();
            member.username=memberDTO.username;
            member.password=memberDTO.password;
            members.put(member.username,member);
        }
        loggedInMembers=new HashSet<>();
        playingMembers=new HashSet<>();
        inboxes=new HashMap<>();
        games=new HashMap<>();
    }

    @Path("/authenticateMember")
    public boolean isMemberAuthentic(String username,String password)
    {
        Member member=members.get(username);
        if(member==null) return false;
        boolean b= password.equals(member.password);
        if(b)
        {
            loggedInMembers.add(username);
        }
        return b;
    }

    @Path("/logout")
    public void logout(String username)
    {
        loggedInMembers.remove(username);

    }
    @Path("/getMembers")
    public List<String> getAvailableUser(String username)
    {
        List<String> availableUser=new LinkedList<>();
        for(String user:loggedInMembers)
        {
            if(playingMembers.contains(user)==false && user.equals(username)==false) availableUser.add(user);
        }
        return availableUser;
    }


    public String getGameId(String username)
    {
        return null;
    }

    public void inviteUser(String fromUsername,String toUsername)
    {
        Message message=new Message();
        message.fromUsername=fromUsername;
        message.toUsername=toUsername;
        message.type=MESSAGE_TYPE.CHALLENGE;

        List<Message> messages=inboxes.get(toUsername);
        if(messages==null)
        {
            messages=new LinkedList<>();
        }
        messages.add(message);        
        inboxes.put(toUsername, messages);
    }

    public List<Message> getMessages(String username)
    {
        List<Message> messages=inboxes.get(username);
        if(messages!=null && messages.size()>0)
        {
            inboxes.put(username, new LinkedList<>());
        }
        return messages;
    }

    public boolean canIPlay(String gameId,String username)
    {
        return false;
    }

    public boolean submitMove(String byUsername,byte piece, int fromX,int fromY,int toX,int toY)
    {
        return false;
    }

    public Move getOpponentsMove(String username)
    {
        return  null;
    }



    //create services to enable log-in,log-out,etc..

    /*
     * /ChessServer
     * /authenticateMember args: username and password
     * /logout args: username 
     * /getMembers args: username
     * Port 5502
     */
}