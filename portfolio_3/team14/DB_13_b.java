package team14;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class DB_13_b extends JFrame implements ActionListener{
	JTextField tf1;
	JTextField tf2;
	JTextField tf3;
	JPanel p=new JPanel();
	JButton b;
	JLabel l;
	Connection conn=null;
	ResultSet rs=null;
	String str;
	String printstring;
	int a;
	public DB_13_b(int a, String s) {  //get business_id,date from DB_13_a through parameter
		//frame layout
		setTitle("Reservation");
		setSize(400,200);
		JLabel l1=new JLabel("Name :");
		JLabel l2=new JLabel("Person Number :");
		JLabel l3=new JLabel("Room Number :");
		tf1=new JTextField(10);
		tf2=new JTextField(10);
		tf3=new JTextField(10);
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JPanel p3=new JPanel();
		
		b=new JButton("OK!");
		b.addActionListener(this);
		this.a=a;
		str=s;
		p1.add(l1);
		p1.add(tf1);
		p2.add(l2);
		p2.add(tf2);
		p3.add(l3);
		p3.add(tf3);
		p.add(p1);
		p.add(p2);
		p.add(p3);
		add(p);
		add(b,BorderLayout.SOUTH);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==b) {
			try {
				//mysql DB connect
				Class.forName("com.mysql.jdbc.Driver");
				conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/team14?serverTimezone=Asia/Seoul&useSSL=false","team14","team14");
				if(conn!=null) {
					System.out.println("Connected to the database");
				}
				Statement stmt=conn.createStatement();
				rs=stmt.executeQuery("select room_num,capacity from dbcourse_room where business_id="+a);
				int s[]=new int[2];
				int n1=Integer.parseInt((String)tf2.getText());
				System.out.println(n1);
				int n2=Integer.parseInt((String)tf3.getText());
				System.out.println(n2);
				int count=0;
				
				boolean bl=false;
				while(rs.next()) {
					s[0]=rs.getInt(1);
					s[1]=rs.getInt(2);
					count++;
					if(s[1]<n1) {
						printstring="The room's capacity is "+String.valueOf(s[1]);
						Print p1=new Print(printstring);
						break;
					}
				}
				if(count<n2) {
					printstring="We only have "+String.valueOf(s[0])+" room";
					Print p2=new Print(printstring);
				}
				
				else {
					String s13="insert into dbcourse_reservation values("+a+",'"+str+"','"+tf1.getText()+"',"+n1+","+n2+")";
					stmt.executeUpdate(s13);
					printstring="Success";
					Print p4 = new Print(printstring);
				}
	
			}
			catch(ClassNotFoundException e) {
				System.out.println("Could not find database driver class"+e);
			}
			catch(SQLException e) {
				System.out.println("An error occurred"+e);
				Print p3=new Print(printstring);
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
		
	} //액션 함수 끝
	
}
class Print extends JDialog{
	public Print(String s){
		setSize(300,200);
		setTitle("Wrong");
		JLabel l=new JLabel(s);
		add(l,BorderLayout.CENTER);
		setVisible(true);
	}
}
