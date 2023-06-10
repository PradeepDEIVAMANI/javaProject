package bankingSystem;
import javax.swing.*;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;

public class Login extends JFrame implements ActionListener {
	
	JButton login, signup, clear;
	TextField t1;
	JPasswordField t2;
	
	Login(){
		setTitle("HDFC Bank");
		
		setLayout(null);
		
		JLabel text = new JLabel("Welcome to HDFS Bank");
		text.setFont(new Font("Osward",Font.BOLD,38));
		text.setBounds(200, 40, 600, 40);
		add(text);
		
		JLabel AccountNum = new JLabel("Ac Num:");
		AccountNum.setFont(new Font("Raleway",Font.BOLD,28));
		AccountNum.setBounds(120, 150, 130, 40);
		add(AccountNum);
		
		t1 = new TextField();
		t1.setBounds(280, 150, 250, 40);
		t1.setFont(new Font("Arial", Font.BOLD, 14));
		add(t1);
		
		JLabel password = new JLabel("Pass:");
		password.setFont(new Font("Raleway",Font.BOLD,28));
		password.setBounds(120, 220, 130, 40);
		add(password);
		
		t2 = new JPasswordField();
		t2.setBounds(280, 220, 250, 40);
		password.setFont(new Font("Raleway",Font.BOLD,28));
		add(t2);
		
		login = new JButton("Login");
		login.setBounds(300, 300, 100, 30);
		login.setBackground(Color.BLACK);
		login.setForeground(Color.WHITE);
		login.addActionListener(this);
		add(login);
		
		clear = new JButton("Clear");
		clear.setBounds(430, 300, 100, 30);
		clear.setBackground(Color.BLACK);
		clear.setForeground(Color.WHITE);
		clear.addActionListener(this);
		add(clear);
		
		signup  = new JButton("Create Account");
		signup.setBounds(300, 350, 230, 30);
		signup.setBackground(Color.BLACK);
		signup.setForeground(Color.WHITE);
		signup.addActionListener(this);
		add(signup);
		
		
		getContentPane().setBackground(Color.WHITE);
		
		
		setSize(800,480);
		setVisible(true);
		setLocation(350, 200);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == clear) {
			t1.setText("");
			t2.setText("");
		} else if (ae.getSource()== login) {
			Conn conn = new Conn();
			
			String Accountnumber = t1.getText();
			String Password = t2.getText();
			String query ="select *from login where Accountnumber ='"+Accountnumber+"' and  pin = '"+Password+"'";
			String query1 ="select *from login2 where accno ='"+Accountnumber+"' and  pass = '"+Password+"'";
			String query2 ="select *from login3 where maccno ='"+Accountnumber+"' and  mpass = '"+Password+"'";
			
			try {
				ResultSet rs = conn.s.executeQuery(query);
				if (rs.next()) {
					setVisible(false);
					new Transaction(Password).setVisible(true);
				}else  {
					//JOptionPane.showMessageDialog(null, "Incorrect AccountNumber and Password");
					ResultSet rs1 = conn.s.executeQuery(query1);
					if (rs1.next()) {
						setVisible(false);
						new Forntoff(toString()).setVisible(true);
				}else {
					ResultSet rs2 = conn.s.executeQuery(query2);
					if (rs2.next()) {
						setVisible(false);
						new Manager(toString()).setVisible(true);
					
				}
					else {
						JOptionPane.showMessageDialog(null, "Incorrect AccountNumber and Password");
					}
				}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}else if(ae.getSource() == signup) {
			setVisible(false);
			new Signup().setVisible(true);
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login();
	}

}
