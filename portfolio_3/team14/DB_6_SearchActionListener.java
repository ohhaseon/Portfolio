package team14;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class DB_6_SearchActionListener implements ActionListener{

	//1615046 Gibbeum Lee
	
	JTable table;
	JTextField name, allergy;
	
	DB_6_SearchActionListener(JTable table, JTextField name, JTextField allergy){
		this.table=table;
		this.name=name;
		this.allergy=allergy;
	}
	
	public void actionPerformed(ActionEvent e) {

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
			
			//find the data having not allery searched in restaurant
			String sql6="select menu, price, origin, menu_grade "
					+ "from dbcourse_InfoMenu "
					+ "where (allergy is null or allergy !='"+allergy.getText()+"' ) and business_id in (select business_id from DBCOURSE_MAIN where name='"+name.getText()+"');";
			
			ResultSet rs=stmt.executeQuery(sql6);

			//table initialize
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.setNumRows(0);
			
			while(rs.next()) {
				
				String arr[]=new String[4];
				arr[0]=rs.getString(1);
				arr[1]=String.valueOf(rs.getInt(2));
				arr[2]=rs.getString(3);
				arr[3]=String.valueOf(rs.getFloat(4));
				
				//print out the search result on the table
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
