package bankingSystem;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.*;

public class Widthdraw extends JFrame implements ActionListener {
	
	JLabel text;
	JTextField amount;
	JButton Widthdraw, Exit;
	String Password;
	Widthdraw(String Password){
		this.Password = Password;
		setLayout(null);
		
		text = new JLabel("Enter the Amount");
		text.setFont(new Font("Raleway", Font.BOLD, 26));
		text.setBounds(170,200,400,40);
		add(text);
		
		amount = new JTextField();
		amount.setFont(new Font("Raleway", Font.BOLD, 26));
		amount.setBounds(270,300,400,40);
		add(amount);
		
		Widthdraw = new JButton("Widthraw");
		Widthdraw.setBounds(170,400,150,30);
		Widthdraw.setBackground(Color.BLACK);
		Widthdraw.setForeground(Color.WHITE);
		Widthdraw.addActionListener(this);
		add(Widthdraw);
		
		Exit = new JButton("Cancel");
		Exit.setBounds(470,400,150,30);
		Exit.setBackground(Color.BLACK);
		Exit.setForeground(Color.WHITE);
		Exit.addActionListener(this);
		add(Exit);
		
		
		getContentPane().setBackground(Color.WHITE);
		setSize(800,800);
		setLocation(300,0);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == Widthdraw) {
			String number = amount.getText();
			Date date = new Date();
			if(number.equals("")) {
				JOptionPane.showMessageDialog(null, "Enter the Amount");
			}else {
				try {
				Conn conn = new Conn();
				String query ="insert into bank values('"+Password+"','"+date+"','Widthdraw','"+number+"')";
				conn.s.execute(query);
				JOptionPane.showMessageDialog(null, "EURO"+number+"Widthdraw Successfully");
				setVisible(false);
				new Transaction(Password).setVisible(true);
			}catch (Exception e){
				System.out.println(e);
			}
				
			}
		}else if (ae.getSource() == Exit ){
			setVisible(false);
			new Transaction(Password).setVisible(true);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Widthdraw("");
	}

	


}


