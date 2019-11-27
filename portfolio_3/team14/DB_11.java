package team14;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class DB_11 extends JFrame implements ActionListener{
	String s[]= {"Menu","Price","Menu_grade"};
	DefaultTableModel model=new DefaultTableModel(s,0);
	JTable table=new JTable(model);
	JScrollPane scroll=new JScrollPane(table);
	JButton b1=new JButton("Delivery");
	JButton b2=new JButton("Reservation");
	Connection conn=null;
	ResultSet rs=null;
	int business_id;
	
	public DB_11(int n){   //get business_id from DB_10 through parameter
		business_id=n;
	    setSize(500,530);
		setTitle("Menu List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		panel2.add(b1);
		panel2.add(b2);
		panel2.setLayout(new GridLayout(0,2));
		
		panel1.add(scroll, BorderLayout.CENTER);
		panel1.add(panel2,BorderLayout.SOUTH);
		add(panel1);
		setVisible(true);
		try {
			//mysql DB connect
		Class.forName("com.mysql.jdbc.Driver");
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/team14","team14","team14");
		if(conn!=null) {
			System.out.println("Connected to the database");
		}
		Statement stmt=conn.createStatement();
		//sql query : show restaurant's menu and information
		rs = stmt.executeQuery("select menu,price,menu_grade from dbcourse_menu where business_id="+business_id);
		//set data to table
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setNumRows(0);
		String arr[]=new String[3];
		while(rs.next()){
        	for(int i=0;i<3;i++){
				arr[i] = rs.getString(i+1);
			}					
			model.addRow(arr);
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
	}   //constructor end.
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==b1) {
			DB_12 d=new DB_12(business_id);
		}  //if end.
		if(ae.getSource()==b2) {
			DB_13_a d=new DB_13_a();
		}

	}  //action function end.
	
}   //class end.



