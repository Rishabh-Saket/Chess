package com.thinking.machines.chess.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import com.thinking.machines.nframework.client.*;

public class ChessUI extends JFrame
{
    private String username;
    private JTable availableMembersList;
    private JScrollPane availableMembersListJScrollPane;
    private AvailableMembersListModel availableMembersListModel;
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
        this.availableMembersListModel=new AvailableMembersListModel();
        availableMembersList=new JTable(availableMembersListModel);
        this.availableMembersListJScrollPane=new JScrollPane(this.availableMembersList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.availableMembersList.getColumn(" ").setCellRenderer(new AvailableMembersListButtonRenderer());
        this.availableMembersList.getColumn(" ").setCellEditor(new AvailableMembersListButtonCellEditor());
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
                    timer.stop();
                    try
                    {                                        
                        java.util.List<String> members=(java.util.List<String>)client.execute("/ChessServer/getMembers",username);                        
                        ChessUI.this.availableMembersListModel.setMembers(members);
                        timer.start();
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

    private void sendInvitation(String toUsername)
    {
        try
        {
        client.execute("/ChessServer/inviteUser",username,toUsername);
        }catch(Throwable t)
        {
            JOptionPane.showMessageDialog(ChessUI.this,t);
        }
        JOptionPane.showMessageDialog(this, "Invitation sent to "+toUsername);
    }

    //inner class starts here
    class AvailableMembersListModel extends AbstractTableModel
    {
        private java.util.List<String> members;
        private String title[]={"Members"," "};
        private java.util.List<JButton> inviteButtons;
        private boolean awaitingInvitationReply;
        AvailableMembersListModel()
        {
            members=new LinkedList<>();
            inviteButtons=new LinkedList<>();
            awaitingInvitationReply=false;
        }

        public int getRowCount()
        {
            return this.members.size();
        }

        public int getColumnCount()
        {
            return this.title.length;
        }

        public String getColumnName(int columnIndex)
        {
            return title[columnIndex];
        }

        public Object getValueAt(int row,int column)
        {
            if(column==0) return this.members.get(row);
            return this.inviteButtons.get(row);
        }

        public boolean isCellEditable(int row,int column)
        {
            if(column==1) return true;
            return false;
        }

        public Class getColumnClass(int c)
        {
            if(c==0) return String.class;
            return JButton.class;
        }

        public void setMembers(java.util.List<String> members)
        {
            if(awaitingInvitationReply) return;
            this.members=members;
            this.inviteButtons.clear();
            for(int i=0;i<this.members.size();i++) this.inviteButtons.add(new JButton("invite"));
            fireTableDataChanged();
        }

        public void setValueAt(Object data, int row , int column)
        {
            if(column==1)
            {
                JButton button= this.inviteButtons.get(row);
                String text=(String)data;
                button.setText(text);
                if(text.equalsIgnoreCase("Invited"))
                {
                    awaitingInvitationReply=true;
                    for(JButton inviteButton: inviteButtons) inviteButton.setEnabled(false);
                    fireTableDataChanged();
                    ChessUI.this.sendInvitation(this.members.get(row));
                }
                else if(text.equalsIgnoreCase("Invite"))
                {
                    awaitingInvitationReply=false;
                    for(JButton inviteButton: inviteButtons) inviteButton.setEnabled(true);
                    fireTableDataChanged();
                }

            }
        }
    }


    class AvailableMembersListButtonRenderer implements TableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean a, boolean b, int r,int c)
        {
            return (JButton)value;
        }
    }

    class AvailableMembersListButtonCellEditor extends DefaultCellEditor 
    {
        private boolean isClicked;
        private int row, column;
        private ActionListener actionListener;
        private JButton button;
    
        AvailableMembersListButtonCellEditor() {
            super(new JCheckBox());
            //button.setOpaque(true);
            this.actionListener=new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    fireEditingStopped();
                }
            };
        }
    
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) 
        {    
            this.row = row;
            this.column = column;
            this.button= (JButton)availableMembersList.getValueAt(row, column);
            this.button.removeActionListener(this.actionListener);
            this.button.addActionListener(this.actionListener);
            this.button.setForeground(Color.black);
            this.button.setBackground(UIManager.getColor("Button.background"));
            this.button.setText("Invite");
            isClicked = true;
            return this.button;
        }
    
        public Object getCellEditorValue() {
            return "Invited";
        }
    
        public boolean stopCellEditing() {
            isClicked = false;
            return super.stopCellEditing();
        }
    
        public void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    

    // innner class ends here
}
