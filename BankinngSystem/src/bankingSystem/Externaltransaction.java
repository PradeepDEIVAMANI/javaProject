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
import javax.swing.JTextField;
import  java.sql.*;
import java.util.Date;

public class Externaltransaction extends JFrame implements ActionListener{

	JButton amount1, amount2, amount3, amount4, amount5, amount6,Exit;
	String Password;
	JTextField accountnumber ;
	
	Externaltransaction(String Password){
		this.Password = Password;
		setLayout(null);
		
		JLabel text = new JLabel("SELECT THE AMOUNT");
		text.setFont(new Font("Raleway", Font.BOLD, 38));
		text.setBounds(250,100,200,100);
		add(text);
		
		JLabel account = new JLabel("ENTER THE ACCOUNT NUMBER");
		account.setFont(new Font("Raleway", Font.BOLD, 38));
		account.setBounds(250,100,200,100);
		add(account);
		
		accountnumber = new JTextField();
		accountnumber .setFont(new Font("Raleway",Font.BOLD,14));
		accountnumber .setBounds(300, 200, 400, 30);
		add(accountnumber);
		
		
		amount1= new JButton("100");
		amount1.setBounds(170,250,150,30);
		amount1.setBackground(Color.BLACK);
		amount1.setForeground(Color.WHITE);
		amount1.addActionListener(this);
		add(amount1);
		
		amount2 = new JButton("200 EURO");
		amount2.setBounds(170,300,150,30);
		amount2.setBackground(Color.BLACK);
		amount2.setForeground(Color.WHITE);
		amount2.addActionListener(this);
		add(amount2);
		
		amount3 = new JButton("300 EURO");
		amount3.setBounds(170,450,150,30);
		amount3.setBackground(Color.BLACK);
		amount3.setForeground(Color.WHITE);
		amount3.addActionListener(this);
		add(amount3);
		
		amount4 = new JButton("500 EURO");
		amount4.setBounds(170,550,150,30);
		amount4.setBackground(Color.BLACK);
		amount4.setForeground(Color.WHITE);
		amount4.addActionListener(this);
		add(amount4);
		
		amount5 = new JButton("1000 EURO");
		amount5.setBounds(170,580,150,30);
		amount5.setBackground(Color.BLACK);
		amount5.setForeground(Color.WHITE);
		amount5.addActionListener(this);
		add(amount5);
		
		amount6 = new JButton("2000 EURO");
		amount6.setBounds(170,620,150,30);
		amount6.setBackground(Color.BLACK);
		amount6.setForeground(Color.WHITE);
		amount6.addActionListener(this);
		add(amount6);
		
		Exit = new JButton("Exit");
		Exit.setBounds(350,580,150,30);
		Exit.setBackground(Color.BLACK);
		Exit.setForeground(Color.WHITE);
		Exit.addActionListener(this);
		add(Exit);
		
		getContentPane().setBackground(Color.WHITE);
		setSize(850, 800);
		setLocation(350, 0);
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource() == Exit) {
			setVisible(false);
			new Transaction(Password).setVisible(true);
		}
		else {
		//tring accountnumbers = accountnumber.getText();
			String amount =((JButton)ae.getSource()).getText().substring(0);
			Conn conn = new Conn();
			try {
				ResultSet rs = conn.s.executeQuery("Select*from bank where pin = '"+Password+"'");
				int balance = 0;
				while(rs.next()){
					
				
				if (rs.getString("type").equals("deposit")) {
					balance += Integer.parseInt(rs.getString("amount"));
				}else {
					balance -= Integer.parseInt(rs.getString("amount"));
				}
				
				}
				if (ae.getSource() != Exit && balance < Integer.parseInt(amount));{
					JOptionPane.showMessageDialog(null, "Insufficient Balance");
				}
				
				Date date = new Date();
				String query = "insert into bank values('"+Password+"','"+date+"', 'withdraw', '"+amount+"')";
				conn.s.executeUpdate(query);
				JOptionPane.showMessageDialog(null,"Euro" +amount+ "Debited Successfully");
				
				setVisible(false);
				new Transaction(Password).setVisible(true);
				
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Externaltransaction("");
	}

}
