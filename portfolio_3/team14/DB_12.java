package team14;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DB_12 extends JDialog implements ActionListener{
	String s[]= {"Menu","Price","Check"};  //column for table
	DefaultTableModel model=new DefaultTableModel(s,0);
	JTable table=new JTable(model);
	JScrollPane scroll=new JScrollPane(table);
	JButton b1=new JButton("Yes");
	JTextField tx=new JTextField(8);
	JCheckBox cb1=new JCheckBox("");
	JLabel j1;
	JLabel j2;
	JPanel p1;
	JPanel p2;
	int sum=0;
	int count1=0;
	int count2=0;
	int count3=0;
	int business_id;
	public DB_12(int n){  //get business_id from DB_11 through parameter
		//frame layout
		setTitle("Delivery");
		setSize(500,530);
		JLabel j=new JLabel("Total :");
		p1=new JPanel();
		p2=new JPanel();
		p2.add(j);
		p2.add(tx);
		b1.addActionListener(this);
		cb1.addActionListener(this);
		
		Connection conn=null;
		ResultSet rs=null;
	try {
		//mysql DB connect
			Class.forName("com.mysql.jdbc.Driver");
			
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/team14","team14","team14");
			if(conn!=null) {
				System.out.println("Connected to the database");
			}
			Statement stmt=conn.createStatement();
			//sql query : show data about delivery
			rs=stmt.executeQuery("select m.business_id,menu,price,delivery,delivery_fee,min_order_fee from dbcourse_menu as m,dbcourse_delivery as d where m.business_id=d.business_id and m.business_id="+n);
			//set data to table
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.setNumRows(0);
			table.getColumn("Check").setCellRenderer(dtcr1);
			table.getColumn("Check").setCellEditor(new DefaultCellEditor(cb1));
			String arr[]=new String[3];
			String ar[]=new String[3];
			while(rs.next()){    
				business_id=rs.getInt(1);
	        	arr[0]=rs.getString(2);
	        	arr[1]=String.valueOf(rs.getInt(3));
	  
	        	ar[0]=rs.getString(4);
	        	ar[1]=String.valueOf(rs.getInt(5));
	        	ar[2]=String.valueOf(rs.getInt(6));
	        	model.addRow(arr);
	        }
			System.out.println(ar[0]);
			if(ar[0].equals("Y")){  //if delivery is available
			j1=new JLabel("Delivery_fee :"+ar[1]);
			j2=new JLabel("Min_Order_Fee : "+ar[2]);
			p2.add(j1);
			p2.add(j2);
			p2.add(b1);
		}
		else {    //if delivery is not available
			j1=new JLabel("Delivery is not available!");
			p2.add(j1);
		}
		}
		catch(ClassNotFoundException e) {
			System.out.println("Could not find database driver class"+e);
			
		}
		catch(SQLException e) {
			System.out.println("An error occurred"+e);
		}
		finally {
			if(conn != null) {
				try {
					/*if(rs!=null) {
						rs.close();
					}*/
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}		
		p1.add(scroll);
		p1.add(p2);
		add(p1);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)  {
		if(ae.getSource()==b1) {
			DB_14 db_14 = new DB_14(sum,business_id);   //call class for count
		}
					if(ae.getSource()==cb1) {
						
						if(cb1.getY()==0) {   //if first checkbox is checked
							count1++;
							if(count1%2==1) {    //if checked once
								sum+=Integer.parseInt((String) table.getValueAt(0,1));
								tx.setText(Integer.toString(sum));
							}
							else {   //checked twice means cancel
								sum-=Integer.parseInt((String) table.getValueAt(0,1));
								tx.setText(Integer.toString(sum));
							}
						}
						if(cb1.getY()==16) {   //if second checkbox is checked
							count2++;
							if(count2%2==1) {
								sum+=Integer.parseInt((String) table.getValueAt(1,1));
								tx.setText(Integer.toString(sum));
							}
							else {
								sum-=Integer.parseInt((String) table.getValueAt(1,1));
								tx.setText(Integer.toString(sum));
							}
						}
						if(cb1.getY()==32) {   //if third checkbox is checked
							count3++;
							if(count3%2==1) {
								sum+=Integer.parseInt((String) table.getValueAt(2,1));
								tx.setText(Integer.toString(sum));
							}
							else {
								sum-=Integer.parseInt((String) table.getValueAt(2,1));
								tx.setText(Integer.toString(sum));
							}
						}
					}
	}
	
	DefaultTableCellRenderer dtcr1=new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table,Object value,boolean hasFocus,int row,int column) {
			JCheckBox box=new JCheckBox("");
			box.setSelected(((Boolean)value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};
}
/*class DB_12_a extends JFrame{
	public DB_12_a() {
		setSize(300,100);
		JLabel label=new JLabel();
		JPanel p=new JPanel();
		label.setText("Your order has been received!");
		p.add(label);
		add(p,BorderLayout.CENTER);
		setVisible(true);
	}
}*/