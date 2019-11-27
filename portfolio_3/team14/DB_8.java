package team14;

import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

//1615041 Haseon Oh
public class DB_8 extends JDialog implements ActionListener{
	JTextField t1;
	JTextField t2;
	JButton b1;
	JButton b2;
	ResultSet rs = null;
	JTable table;
	int business_id;
	//Searching Name
	public DB_8(){
		setSize(800, 400);

		setTitle("Search Name");
		
		JPanel panel = new JPanel();	
		//input data for searching name
		t1 = new JTextField(50);
		t1.setText("Search Name");
		b1 = new JButton("Confirm");
		b1.addActionListener(this);
		panel.add(t1);
		panel.add(b1);
		//button for more detail information 
		b2=new JButton("Select");
		t2=new JTextField(3);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,11));
		JPanel panel3=new JPanel();
		panel3.add(t2);
		panel3.add(b2);
		add(panel3,BorderLayout.PAGE_END);
		b2.addActionListener(this);
		
		//table made by data
		String colNames[] = {"NAME", "ADDRESS", "CALL", "OPEN", "PEAK","CLOSE","BREAK","EVALUATION","REVIEW","TV SHOW","Business_id"};
		DefaultTableModel model = new DefaultTableModel (colNames,0);
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		panel2.add(sp);
		
		add(panel, BorderLayout.PAGE_START);
		add(panel2, BorderLayout.CENTER);

		setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == b1){
			//connect mysql DB
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/team14";
			String username = "team14";
			String password  = "team14";
			try {
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement();
				
				//select data corresponded to input
				rs = stmt.executeQuery("select * from dbcourse_nameview where name = '"+ t1.getText()+"'");
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.setNumRows(0);
				String arr[]=new String[11];
				int[][] a=new int[1][];
				while(rs.next()){
					//print table
					for(int i=0;i<11;i++){
						arr[i] = rs.getString(i+1);
					}
					model.addRow(arr);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
		if(e.getSource()==b2) {
			int n=Integer.parseInt(t2.getText());
			DB_11 db11=new DB_11(Integer.parseInt((String) table.getValueAt(n-1,10)));
		}
	}
}



