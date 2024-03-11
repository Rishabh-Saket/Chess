package com.thinking.machines.chess.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import com.thinking.machines.nframework.client.*;

public class ChessUI extends JFrame
{
    private String username;
    private JList availableMembersList;
    private javax.swing.Timer timer;
    private Container container;
    private NFrameworkClient client;
    public ChessUI(String username)
    {
        super("Member: "+username);
        this.username=username;
        this.client=new NFrameworkClient();
        initComponents();
        setAppearance();
        addListeners();

        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        int w=500;
        int h=400;
        setSize(w,h);
        setLocation(d.width=w/2, d.height-h/2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents()
    {
        JPanel p1=new JPanel();
        p1.setLayout(new BorderLayout());
        p1.add(new JLabel("Members"),BorderLayout.NORTH);
        availableMembersList=new JList<>();
        p1.add(availableMembersList);
        container=getContentPane();
        container.setLayout(new BorderLayout());
        container.add(p1,BorderLayout.EAST);
    }

    private void setAppearance()
    {

    }

    private void addListeners()
    {
        timer=new javax.swing.Timer(1000,new ActionListener() {
            public void actionPerformed(ActionEvent ev)
                {
                    try
                    {                                        
                        java.util.List<String> members=(java.util.List<String>)client.execute("/ChessServer/getMembers",username);
                        Vector vetcor=new Vector();
                        for(String member:members)
                        {
                            vetcor.add(member);
                        }
                        ChessUI.this.availableMembersList.setListData(vetcor);
                    }catch(Throwable t)
                    {
                        JOptionPane.showMessageDialog(ChessUI.this,t.toString());
                    }
                }
            
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                try 
                {
                    client.execute("/ChessServer/logout",username);
                } catch (Throwable t) 
                {
                    JOptionPane.showMessageDialog(ChessUI.this,t.toString());
                }
                System.exit(0);
            }
        });

        //timer is setup let's start
        timer.start();
    }

    public void showUI()
    {
        this.setVisible(true);
    }
}
