package team14;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class DB_7 extends JDialog {
	public DB_7() {
		JDialog DB7_Dialog = new JDialog(); //"Checking Reservation"
		DB7_Dialog.setPreferredSize(new Dimension(500,500));
		DB7_Dialog.setLocation(450,150);
		Container contentPane = DB7_Dialog.getContentPane();
		JPanel DB7_panel = new JPanel();
		JPanel DB7_panel2 = new JPanel(); 
		
		String DB7_colNames[]= {"Name","Date","Room_num", "Person_number"};
		DefaultTableModel DB7_model = new DefaultTableModel (DB7_colNames,0);
		JTable DB7_table = new JTable(DB7_model); //table made by data from DB
		
		DB7_panel.setLayout(new GridLayout(1,3));
		DB7_panel2.setLayout(new GridLayout(1,4));
		
		//search reservation date field
		JTextField DB7_name = new JTextField("Enter the reservation name");
		JScrollPane DB7_scrollPane = new JScrollPane(DB7_table);
		DB7_scrollPane.setSize(400,300);
		
		JButton DB7_search = new JButton("Search");
		//find the rows having that name and allergy, show the table except that	
		DB7_search.addActionListener(new DB_7_SearchReservationActionListener(DB7_table,DB7_name));
		JButton DB7_Delete = new JButton("Delete");
		DB7_Delete.addActionListener(new DB_7_DeleteActionListener(DB7_table,DB7_name)); //go to back
		
		//Update reservation date field
		JTextField DB7_Rt_name = new JTextField(20);
		JTextField DB7_Rv_date = new JTextField(20);
		JTextField DB7_Rv_roomNum = new JTextField(5);
		JTextField DB7_Rv_persomNum = new JTextField(5);
		
		//show the selected cell data. save that date to DB7_Selected.
		String DB7_Selected[]=new String[4];
		
		DB7_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent event) {
				
				if(DB7_table.getSelectedRow()>=0) {
					DB7_Selected[0]=DB7_table.getValueAt(DB7_table.getSelectedRow(), 0).toString();
					DB7_Rt_name.setText(DB7_Selected[0]);
					DB7_Selected[1]=DB7_table.getValueAt(DB7_table.getSelectedRow(), 1).toString();
					DB7_Rv_date.setText(DB7_Selected[1]);
					DB7_Selected[2]=DB7_table.getValueAt(DB7_table.getSelectedRow(), 2).toString();
					DB7_Rv_roomNum.setText(DB7_Selected[2]);
					DB7_Selected[3]=DB7_table.getValueAt(DB7_table.getSelectedRow(), 3).toString();
					DB7_Rv_persomNum.setText(DB7_Selected[3]);
				}
			}
		});
		
		JButton DB7_update = new JButton("Update");
		DB7_update.addActionListener(new DB_7_UpdateActionListener(DB7_Selected, DB7_table, DB7_name, 
				DB7_Rt_name, DB7_Rv_date, DB7_Rv_roomNum, DB7_Rv_persomNum)); //Update reservation data
		//input field and Search, Delete button struct
		DB7_panel.add(DB7_name);
		DB7_panel.add(DB7_search);
		DB7_panel.add(DB7_Delete);
		
		//DB7_panel2.add(Rt_name);
		DB7_panel2.add(DB7_Rv_date);
		DB7_panel2.add(DB7_Rv_roomNum);
		DB7_panel2.add(DB7_Rv_persomNum);
		DB7_panel2.add(DB7_update);
		
		contentPane.add(DB7_panel,BorderLayout.NORTH);
		contentPane.add(DB7_scrollPane, BorderLayout.CENTER);
		contentPane.add(DB7_panel2, BorderLayout.SOUTH);
		
		DB7_Dialog.pack();
		DB7_Dialog.setVisible(true);
	}

	public static void main(String[] args) {

	}

}
