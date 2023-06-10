package bankingSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.*;
public class Balance extends JFrame implements ActionListener{
	
	JButton Bexit;
	String Password;
	int balance = 0;

	Balance(String Password){
		this.Password = Password;
		setLayout(null);
		
		Bexit = new JButton("Exit");
		Bexit.setBounds(355,550,150,30);
		Bexit.setBackground(Color.BLACK);
		Bexit.setForeground(Color.WHITE);
		Bexit.addActionListener(this);
		add(Bexit);
		
		Conn c = new Conn();
		try {
			ResultSet rs = c.s.executeQuery("Select*from bank where pin = '"+Password+"'");
			//int balance = 0;
			while(rs.next()){
				
			
			if (rs.getString("type").equals("deposit")) {
				balance += Integer.parseInt(rs.getString("amount"));
			}else {
				balance -= Integer.parseInt(rs.getString("amount"));
			}
			
			}
		} catch(Exception e) {
			System.out.println(e);
		}
			
		JLabel text = new JLabel("Account Balance:" + balance);
		text.setFont(new Font("Raleway", Font.BOLD, 25));
		text.setBounds(140, 20, 600, 40);
		add(text);
		
		
		
		getContentPane().setBackground(Color.WHITE);
		setSize(800,800);
		setLocation(300, 0);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		setVisible(false);
		new Transaction(Password).setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Balance("");
	}
	

}
