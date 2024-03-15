import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
class MyModel extends AbstractTableModel
{
    private Object data[][];
    private String title[]={"A","B"};
    MyModel()
    {
        data=new Object[2][2];
        data[0][0]="One";
        data[0][1]=new JButton("First Button");
        data[1][0]="Two";
        data[1][1]=new JButton("Second Button");
    }

    public int getRowCount()
    {
        return this.data.length;
    }

    public int getColumnCount()
    {
        return this.title.length;
    }

    public Object getValueAt(int row,int column)
    {
        return data[row][column];
    }

    public boolean isCellEditable(int row,int column)
    {
        if(column==1) return true;
        return false;
    }

    public Class getColumnClass(int c)
    {
        return data[c].getClass();
    }

    public void setValueAt(Object data,int r, int c)
    {
        System.out.println(r+","+c+","+data);
    }
}

class Whatever extends JFrame
{
    private JTable table;
    MyModel model;
    Whatever()
    {
        model=new MyModel();
        table=new JTable(model);
        table.getColumn("B").setCellRenderer(new ButtonRenderer());
        table.getColumn("B").setCellEditor(new ButtonCellEditor());
        Container c=getContentPane();
        c.setLayout(new BorderLayout());
        c.add(table,BorderLayout.CENTER);
        setLocation(400,500);
        setSize(500,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    class ButtonRenderer implements TableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean a, boolean b, int r,int c)
        {
            System.out.println(value.toString());
            System.out.println(r+","+c);
            return (JButton)value;
        }
    }

    class ButtonCellEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isClicked;
        private int row, column;
    
        ButtonCellEditor() {
            super(new JCheckBox());
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    System.out.println("Great");
                    fireEditingStopped();
                }
            });
        }
    
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            System.out.println("getTableCellEditorComponent got called");
            this.row = row;
            this.column = column;
            button.setForeground(Color.black);
            button.setBackground(UIManager.getColor("Button.background"));
            button.setText("whatever");
            isClicked = true;
            return button;
        }
    
        public Object getCellEditorValue() {
            System.out.println("getCellEditor value got called");
            return "cool";
        }
    
        public boolean stopCellEditing() {
            isClicked = false;
            return super.stopCellEditing();
        }
    
        public void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    
    public static void main(String[] args) 
    {
        Whatever w=new Whatever();
    }

}