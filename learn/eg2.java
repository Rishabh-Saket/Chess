import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class psp
{
    private static int x;
    private static Timer t;
    public static void main(String[] args) 
    {
        t=new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent ev)
            {
                System.out.println("Hi");
                x++;
                if(x==2)
                {
                    System.out.println("Stopping");
                    t.stop();
                }
            }
        });
        t.start();
        try 
        {
            Thread.sleep(15000);    
            t.start();
            Thread.sleep(50000);
        }catch (Exception e) 
        {
            // TODO: handle exception
        }
    }
}