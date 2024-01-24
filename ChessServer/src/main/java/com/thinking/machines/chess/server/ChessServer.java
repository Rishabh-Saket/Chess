package com.thinking.machines.chess.server;
import com.thinking.machines.chess.server.dl.*;
import java.util.*;
public class ChessServer
{
    private Map<String,Member> members;
    private Set<String> loggedInMembers;    
    private Set<String> playingMembers;
    private Map<String,List<Message>> inboxes;
    private Map<String,Game> games;
    public ChessServer()
    {
        populateDataStructures();
    }

    
    public void populateDataStructures()
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
            this.members.put(member.username,member);
        }
        this.loggedInMembers=new HashSet<>();
        this.playingMembers=new HashSet<>();
        this.inboxes=new HashMap<>();
        this.games=new HashMap<>();
    }

    public boolean isUserAuthentic(String username,String password)
    {
        Member member=this.members.get(username);
        if(member==null) return false;
        boolean b= password.equals(member.password);
        if(b)
        {
            this.loggedInMembers.add(username);
        }
        return b;
    }

    public void logout(String username)
    {
        this.loggedInMembers.remove(username);

    }

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
        List<Message> messages=this.inboxes.get(username);
        if(messages!=null && messages.size()>0)
        {
            this.inboxes.put(username, new LinkedList<>());
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
}