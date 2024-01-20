package com.thinking.machines.chess.server.dl;
import java.util.*;
import com.thinking.machines.chess.server.dl.MemberDTO;
public class MemberDAO
{
    public List<MemberDTO> getAll()
    {
        List<MemberDTO> members=new LinkedList<>();
        MemberDTO m=new MemberDTO();
        m.username="Amit";
        m.password="amit";
        members.add(m);
        m=new MemberDTO();
        m.username="Bobby";
        m.password="bobby";
        members.add(m);
        m=new MemberDTO();
        m.username="Eshan";
        m.password="eshan";
        members.add(m);
        m=new MemberDTO();
        m.username="Chentan";
        m.password="chentan";
        members.add(m);
        m=new MemberDTO();
        m.username="Dinesh";
        m.password="dinesh";  
        members.add(m);
        m=new MemberDTO();
        m.username="Fatima";
        m.password="fatima";  
        members.add(m);
        m=new MemberDTO();
        m.username="Gopal";
        m.password="gopal";  
        members.add(m);
        m=new MemberDTO();
        m.username="Harish";
        m.password="harish";  
        members.add(m);    
        m=new MemberDTO();
        m.username="Jack";
        m.password="jack";  
        members.add(m);
        m=new MemberDTO();
        m.username="Ishan";
        m.password="ishan";  
        members.add(m);
        return members;
    }
}