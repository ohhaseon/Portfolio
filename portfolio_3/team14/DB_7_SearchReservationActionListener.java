package team14;

import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class DB_7_SearchReservationActionListener implements ActionListener{
	JTable table;
	JTextField name;
	
	DB_7_SearchReservationActionListener(JTable table, JTextField name){
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

			/*if (conn != null) { 
				System.out.println("Connected to the database"); 
			}*/
			stmt = conn.createStatement(); 
			
			//sql query. get information name, date, room_num, person_number about reservation
			String sql7a="select dbcourse_main.name, DBCOURSE_RESERVATION.date, DBCOURSE_RESERVATION.room_num, DBCOURSE_RESERVATION.person_number "
					+ "from DBCOURSE_RESERVATION, dbcourse_main "
					+ "where dbcourse_main.business_id=DBCOURSE_RESERVATION.business_id and DBCOURSE_RESERVATION.name='"+name.getText()+"';";
			ResultSet rs=stmt.executeQuery(sql7a);

			//table initialize
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.setNumRows(0);
			
			while(rs.next()) { 
				//get text from sql and print to table

				
				String arr[]=new String[4];
				arr[0]=rs.getString(1);
				arr[1]=String.valueOf(rs.getTimestamp(2)); //시간이 이상하게 나와
				arr[2]=String.valueOf(rs.getInt(3));
				arr[3]=String.valueOf(rs.getInt(4));
				
				model.addRow(arr);
			}
			
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
