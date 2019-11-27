package team14;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class DB_14 extends JFrame{

	String customer_id; //variables for customer_id
	
	JPanel etpanel = new JPanel(); //declare panel
	JLabel etlabel1 = new JLabel("Enter your ID"); //declare label for "Enter your ID"
	JLabel etlabel2 = new JLabel(""); //declare label for customer_id
	JLabel etlabel3 = new JLabel(""); //declare label for result of transaction
	JLabel etlabel4 = new JLabel(""); //declare label for result of withdraw
	JLabel etlabel5 = new JLabel(""); //declare label for result of deposit
	JTextField ettext = new JTextField(10); //declare textfield for input
	JButton etbutton = new JButton("Enter"); //declare button for event 
	
	DB_14(int totalprice, int business_id){		
		etpanel.add(etlabel1); //add label to panel
		etpanel.add(ettext); //add textfield to panel
		etpanel.add(etbutton); //add button to panel
		etpanel.add(etlabel2); //add label to panel
		etpanel.add(etlabel3); //add label to panel
		etpanel.add(etlabel4); //add label to panel
		etpanel.add(etlabel5); //add label to panel
		
		etbutton.addActionListener(new ActionListener(){ //occur event when press the button
			public void actionPerformed(ActionEvent e){
				customer_id = ettext.getText(); //customer_id input from user
				etlabel2.setText(customer_id); 
				DBPAYMENT dbpayment = new DBPAYMENT(customer_id, totalprice, business_id); //declare dbpayment object
				etlabel3.setText(dbpayment.result);
				etlabel4.setText(dbpayment.wd_result);
				etlabel5.setText(dbpayment.dp_result);
			}
		});
		add(etpanel);
		setTitle("restaurnat"); //initial value for frame
		setSize(500,300); //initial value for frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	
	}
}

class DBPAYMENT{ //declare class DBPAYMENT
	
	int totalprice; //variables for totalprice
	int business_id; //variables for business_id
		
	String customer_id; //variables for customer_id
	String result; //variables for result of transcation
	String wd_result; //variables for result of withdraw
	String dp_result; //variables for result of deposit
	String url = "jdbc:mysql://localhost:3306/team14";
	String user = "team14";
	String pwd = "team14";
	Connection Conn = null;
	
	void withdraw() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection Conn = DriverManager.getConnection(url, user, pwd);
		Statement stmt = Conn.createStatement();
		StringBuilder sb = new StringBuilder(); //declare sb object for string concatenation
		sb.append("update dbcourse_cust_acc ") //string concatenation
		.append("set cust_account=cust_account-"+totalprice+" ") //string concatenation
		.append("where customer_id='"+customer_id+"' ") //string concatenation
		.append("and cust_account>="+totalprice); //string concatenation
		String sql = sb.toString(); //string concatenation
		
		int result = stmt.executeUpdate(sql); //execute sql query 
		if(result == 0) throw new Exception("Customer_id does not exist or money is not enough"); //when occur error, throw exception
		stmt.close();
	}
	
	void deposit() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection Conn = DriverManager.getConnection(url, user, pwd);
		Statement stmt = Conn.createStatement();
		StringBuilder sb = new StringBuilder(); //declare sb object for string concatenation
		sb.append("update dbcourse_rest_acc ") //string concatenation
		.append("set rest_account=rest_account+"+totalprice+" ") //string concatenation
		.append("where business_id="+business_id); //string concatenation
		String sql = sb.toString(); //string concatenation
		
		int result = stmt.executeUpdate(sql); //execute sql query 
		if(result == 0) throw new Exception(" Failed to deposit to restaurant_account"); //when error occur, throw exception
		stmt.close();
	}
	
	DBPAYMENT(String customer_id, int totalprice, int business_id){
		this.customer_id = customer_id;
		this.totalprice = totalprice;
		this.business_id = business_id;
		
		try {
			try{Class.forName("com.mysql.jdbc.Driver");}catch(Exception ex){}
			Connection Conn = DriverManager.getConnection(url, user, pwd);
			Conn.setAutoCommit(false); // setAutoCommit(false) for transcation
			try{
				withdraw(); //call method withdraw
				try{
					deposit(); //call method deposit
					result = "Success"; //when withdraw and deposit success, assign "success" to result
				}catch(Exception ex){
					dp_result = ex.getMessage(); //when deposit fail, getMessage
					}
			}catch(Exception ex){
				wd_result = ex.getMessage(); //when withdraw fail, getMessage
				}
			Conn.commit(); //change commit to true
		}catch(SQLException e1){ 
			e1.printStackTrace();
			try{Conn.rollback();}catch(Exception e2){} //when error occur, rollback
			result = "Rollback occured"; //assign "Rollback occured" to result
			}finally {
				try {Conn.setAutoCommit(true);}catch(Exception e){} //when transaction finished, setAutoCommit(true)
				}
		}
}

