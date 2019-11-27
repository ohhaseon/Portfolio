package team14;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class DB_7_DeleteActionListener implements ActionListener{
	//1615046 Gibbeum Lee
	JTable table;
	JTextField name;
	
	DB_7_DeleteActionListener(JTable table, JTextField name){
		this.table=table;
		this.name=name;
	}

	public void actionPerformed(ActionEvent e) {
		//mysql DB connect
		String driverName="com.mysql.cj.jdbc.Driver";
		String DBURL = "jdbc:mysql://localhost:3306/team14?serverTimezone=Asia/Seoul&useSSL=false";
		String user = "root"; 
		String password = "1615046"; 
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName(driverName); 
			conn = DriverManager.getConnection(DBURL, user, password); 

			if (conn != null) { 
				System.out.println("Connected to the database"); 
			}

			DefaultTableModel model = (DefaultTableModel)table.getModel();
			int row=table.getSelectedRow();
			System.out.println(row);
			
			//if ro2 != -1, delete data from table
			if(row==-1)
				return;
			
			stmt = conn.createStatement();
			
			String R_name=(String) table.getValueAt(row,0); //restaurant_name
			String Date=(String) table.getValueAt(row,1); //reservation date
			String R_num=(String) table.getValueAt(row,2); //room number
			
			//sql query. delete reservation data having input name from DB
			String sql7b="delete from DBCOURSE_RESERVATION "
					+ "where name='"+name.getText()+"' and Date='"+Date+"' and room_num='"+R_num+"' "
					+ "and business_id=(select business_id from dbcourse_main where name='"+R_name+"');";
			stmt.executeUpdate(sql7b);
			
			model.removeRow(row);
			
		} catch (ClassNotFoundException ex1) { 
			System.out.println("Could not find database driver class");
			ex1.printStackTrace(); ex1.printStackTrace();
		} catch (SQLException ex) {
			System.out.println("An error occurred. Maybe user/password is invalid"); 
			ex.printStackTrace(); ex.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close(); 
					stmt.close();
				} catch (SQLException ex) { 
					ex.printStackTrace(); 
				}
			}
		}
		
	}
	
}
