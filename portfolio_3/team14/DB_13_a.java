package team14;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_13_a extends JFrame implements ActionListener{
	String s[]= {"Date","Person_number","Room_number"};  //column data for table
	DefaultTableModel model=new DefaultTableModel(s,0);
	JTable table=new JTable(model);
	JScrollPane scroll=new JScrollPane(table);
	
	JLabel l1=new JLabel("Date & Time : ");
	JTextField tx=new JTextField(20);
	JButton b1=new JButton("Search");
	JButton b2=new JButton("Make Reservation");
	Connection conn=null;
	ResultSet rs=null;
	String str;
	int business_id;
	public DB_13_a(){
		//frame layout
		setTitle("Reservation");
		setSize(500,565); 
		JPanel p=new JPanel();
		JPanel p1=new JPanel();
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		p.add(l1);
		p.add(tx);
		p.add(b1);
		p1.add(p,BorderLayout.PAGE_START);
		p1.add(scroll,BorderLayout.CENTER);
		p1.add(b2,BorderLayout.PAGE_END);
		
		add(p1);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==b1) {
			try {
				//mysql DB connect
				Class.forName("com.mysql.jdbc.Driver");
				conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/team14","team14","team14");
				if(conn!=null) {
					System.out.println("Connected to the database");
				}
				Statement stmt=conn.createStatement();
				str=tx.getText();
				//sql query
				String s13="select date,person_number,room_num,business_id from dbcourse_reservation where date='"+str+"'";
				rs = stmt.executeQuery(s13);
				//set data to table
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.setNumRows(0);
				String arr[]=new String[3];
				while(rs.next()){
		        	arr[0]=String.valueOf(rs.getTimestamp(1));
		        	arr[1]=String.valueOf(rs.getInt(2));
		        	arr[2]=String.valueOf(rs.getInt(3));
					model.addRow(arr); 		
					business_id=rs.getInt(4);
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
						if(rs!=null) {
							rs.close();
						}
						conn.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(ae.getSource()==b2) {
			DB_13_b b13=new DB_13_b(business_id,str);  //call new Dialog
		}
	}
	
}
