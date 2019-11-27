package team14;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;

public class DB_Restaurant extends JFrame implements ActionListener {
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	JButton b5;
	public DB_Restaurant(){
		setTitle("Home UI"); 
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b1=new JButton("Search Name");
		b2=new JButton("Search Address");
		b3=new JButton("Search Type");
		b4=new JButton("Check Allergy");
		b5=new JButton("Check Reservation");
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		JPanel p=new JPanel();
		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.add(b4);
		p.add(b5);
		p.setLayout(new GridLayout(0,1));
		add(p);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==b1) {
			DB_8 db8=new DB_8();
		}
		if(ae.getSource()==b2) {
			DB_9 db9=new DB_9();
		}
		if(ae.getSource()==b3) {
			DB_10 db10=new DB_10();
		}
		if(ae.getSource()==b4) {
			DB_6 db6=new DB_6();
		}
		if(ae.getSource()==b5) {
			DB_7 db7=new DB_7();
			//db7.setSize(500,530);
			//db7.setVisible(true);
		}
	}
	public static void main(String[] args) {
		DB_Restaurant dbr=new DB_Restaurant();
	}
}
