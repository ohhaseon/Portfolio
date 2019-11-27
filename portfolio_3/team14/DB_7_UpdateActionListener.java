package team14;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class DB_7_UpdateActionListener implements ActionListener{
	
	//DB7_Selected = data before modified / etc. = data after modified
	JTextField DB7_name,Rt_name, Rv_date, Rv_roomNum, Rv_persomNum;
	JTable table;
	String[] DB7_Selected;
	
	DB_7_UpdateActionListener(String[] DB7_Selected, JTable table,
			JTextField DB7_name, JTextField Rt_name, JTextField Rv_date, JTextField Rv_roomNum, JTextField Rv_persomNum){
		this.DB7_Selected=DB7_Selected;
		this.table=table;
		this.DB7_name=DB7_name;
		this.Rt_name=Rt_name;
		this.Rv_date=Rv_date;
		this.Rv_roomNum=Rv_roomNum;
		this.Rv_persomNum=Rv_persomNum;
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
					stmt = conn.createStatement(); 
				
					JFrame frame1 = new JFrame(" !Caution! ");
					JLabel label1 =new JLabel(" !Already reservated. Please input different data!");
					frame1.getContentPane().add(label1);
					frame1.setPreferredSize(new Dimension(300,100));
					frame1.setLocation(450,150);
					frame1.pack();
					
					
					JFrame frame2 = new JFrame("!Caution!");
					JLabel label2 =new JLabel(" !This data doesn't match condition!");
					frame2.getContentPane().add(label2);
					frame2.setPreferredSize(new Dimension(300,100));
					frame2.setLocation(550,300);
					frame2.pack();
					
					//////sql query. get information name, date, room_num, person_number about reservation////
					//search the busniess_id from selected table row
					String sql7a="select business_id, name from DBCOURSE_RESERVATION "
							+ "where name='"+DB7_name.getText()+"' and Date='"+DB7_Selected[1]+"' and room_num='"+DB7_Selected[2]+"' "
							+ "and business_id=(select business_id from dbcourse_main where name='"+DB7_Selected[0]+"');";
					ResultSet rs=stmt.executeQuery(sql7a);
					int DB7_BId=0;
					if(rs.next()) DB7_BId=rs.getInt(1);
					
					//////////if updating date is not proper, don't update///////////
					//FK checking
					String sql7b="select business_id, Date, name from DBCOURSE_RESERVATION ";
					rs=stmt.executeQuery(sql7b);
					while(rs.next()) { 
						if(DB7_BId==rs.getInt(1) && Rv_date.getText()==String.valueOf(rs.getTimestamp(2)) && DB7_name.getText()==rs.getString(3)) {
							System.out.println("조건 부적합1");
							frame1.setVisible(true);
						}
						
					}
					////Date, Room_num, Person_num requirements checking
					//time compare
					String sql7c="select open_time, close_time, break_time from DBCOURSE_TIME where business_id='"+DB7_BId+"'";
					rs=stmt.executeQuery(sql7c);
					while(rs.next()) { 
						Timestamp Rv_time=Timestamp.valueOf(Rv_date.getText());
						//time must be between open time ~ close time
						if(rs.getTimestamp(1).getTime()%(24*60*60*1000L)>Rv_time.getTime()% (24*60*60*1000L) || 
								rs.getTimestamp(2).getTime()% (24*60*60*1000L)<Rv_time.getTime()% (24*60*60*1000L)){
							System.out.println("조건 부적합2"); 
							frame2.setVisible(true);
							return;
							}
						//time must not equals to break time
						if(rs.getTimestamp(3)!=null)
								if(rs.getTimestamp(3).getTime()% (24*60*60*1000L)==Rv_time.getTime()% (24*60*60*1000L)){
									System.out.println("조건 부적합2");
									frame2.setVisible(true);
									return;
									}
					}
					
					//Room_num is have to same with the Room_num in the table DBCOURSE_Room
					String sql7c_2="select room_num from DBCOURSE_Room where business_id='"+DB7_BId+"'";
					rs=stmt.executeQuery(sql7c_2);
					boolean IsInSameRoom=false;
					while(rs.next()) { 
						if(rs.getInt(1)==Integer.parseInt(Rv_roomNum.getText()))
							IsInSameRoom=true;
					}
					if(!IsInSameRoom){
						System.out.println("조건 부적합3");
						frame2.setVisible(true);
						return;
						}
					
					//Person_num
					String sql7c_3="select capacity from DBCOURSE_Room where business_id='"+DB7_BId+"' and Room_num='"+Rv_roomNum.getText()+"'";
					rs=stmt.executeQuery(sql7c_3);
					while(rs.next()) { 
						if(rs.getInt(1)<Integer.parseInt(Rv_persomNum.getText())){
							System.out.println("조건 부적합4");
							frame2.setVisible(true);
							return;
							}
					}
					
					///////////////update data (Date, capacity, Room number) using input date to the Reservation Table.///////////
					String sql7d="update DBCOURSE_RESERVATION "
							+ "set Date='"+Rv_date.getText()+"' "
							+ "where name='"+DB7_name.getText()+"' and Date='"+DB7_Selected[1]+"' and room_num='"+DB7_Selected[2]+"' "
							+ "and business_id="+DB7_BId+";";
					stmt.executeUpdate(sql7d);
					
					System.out.println(DB7_name.getText()+" "+Rv_date.getText()+" "+DB7_Selected[2]+" "+DB7_BId);
					
					String sql7e="update DBCOURSE_RESERVATION "
							+ "set person_number='"+Rv_persomNum.getText()+"' "
							+ "where name='"+DB7_name.getText()+"' and Date='"+Rv_date.getText()+"' and room_num='"+DB7_Selected[2]+"' "
							+ "and business_id="+DB7_BId+";";
					stmt.executeUpdate(sql7e);
					
					System.out.println(DB7_name.getText()+" "+Rv_date.getText()+" "+DB7_Selected[2]+" "+DB7_BId);
					
					String sql7f="update DBCOURSE_RESERVATION "
							+ "set room_num='"+Rv_roomNum.getText()+"' "
							+ "where name='"+DB7_name.getText()+"' and Date='"+Rv_date.getText()+"' and room_num='"+DB7_Selected[2]+"' "
							+ "and business_id="+DB7_BId+";";			
					stmt.executeUpdate(sql7f);
					
					System.out.println(DB7_name.getText()+" "+Rv_date.getText()+" "+DB7_Selected[2]+" "+DB7_BId);
					
					///////bring the data modified////////
					String sql7h="select DBCOURSE_RESERVATION.date, DBCOURSE_RESERVATION.room_num, DBCOURSE_RESERVATION.person_number "
							+ "from DBCOURSE_RESERVATION, dbcourse_main "
							+ "where dbcourse_main.business_id=DBCOURSE_RESERVATION.business_id and DBCOURSE_RESERVATION.name='"+DB7_name.getText()+"';";
					rs=stmt.executeQuery(sql7h);
					
					while(rs.next()) {
						//get text from sql and print to table

						//table initialize
						DefaultTableModel model = (DefaultTableModel)table.getModel();
						model.setNumRows(0);
						
						String arr[]=new String[4];
						arr[0]=DB7_Selected[0];
						arr[1]=String.valueOf(rs.getTimestamp(1));
						arr[2]=String.valueOf(rs.getInt(2));
						arr[3]=String.valueOf(rs.getInt(3));
						
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
