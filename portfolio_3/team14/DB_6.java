package team14;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class DB_6 extends JDialog{
	public DB_6() {
		//1615046 Gibbeum Lee
		JDialog DB_6Dialog = new JDialog();  //"Checking Allergy"
		DB_6Dialog.setPreferredSize(new Dimension(650,500));
		Container DB_6contentPane = DB_6Dialog.getContentPane();
		JPanel DB_6panel = new JPanel();
		DB_6panel.setPreferredSize(new Dimension(550,300));
		
		String DB_6colNames[]= {"Menu","Price","origin", "Menu_grade"};
		DefaultTableModel DB_6model = new DefaultTableModel (DB_6colNames,0);
		JTable table = new JTable(DB_6model); //table made by data from DB
		
		DB_6panel.setLayout(new GridLayout(1,3));
		
		//search input text
		JTextField DB_6name = new JTextField("Enter the Rastaurant name");
		JTextField DB_6allergy = new JTextField("Enter the allergy name");
		JScrollPane DB_6scrollPane = new JScrollPane(table);
		DB_6scrollPane.setSize(550,400);
		
		//find the menus having input allergy in 'name'restaurant, show the table except that
		JButton search = new JButton("Search");
		search.addActionListener(new DB_6_SearchActionListener(table, DB_6name, DB_6allergy));
		
		DB_6panel.add(DB_6name);
		DB_6panel.add(DB_6allergy);
		DB_6panel.add(search);
		
		DB_6contentPane.add(DB_6panel,BorderLayout.CENTER);
		DB_6contentPane.add(DB_6scrollPane, BorderLayout.SOUTH);
		
		DB_6Dialog.pack();
		DB_6Dialog.setVisible(true);
	}

	public static void main(String[] args) {
		
		
	}

}
