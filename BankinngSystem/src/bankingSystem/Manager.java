package bankingSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Manager extends JFrame implements ActionListener{
	
	JLabel text;
	JButton list,pinfo,exit;
	
	public Manager(String string) {
		setLayout(null);
		
		
		text = new JLabel("Manager Access Area");
		text.setFont(new Font("Raleway", Font.BOLD, 26));
		text.setBounds(170,200,400,40);
		add(text);
		
		
		list = new JButton("Sum of Deposite");
		list.setBounds(170,250,300,30);
		list.setBackground(Color.BLACK);
		list.setForeground(Color.WHITE);
		list.addActionListener(this);
		add(list);
		
		
		pinfo = new JButton("Customer Activities");
		pinfo.setBounds(170,350,300,30);
		pinfo.setBackground(Color.BLACK);
		pinfo.setForeground(Color.WHITE);
		pinfo.addActionListener(this);
		add(pinfo);
		
		exit = new JButton("Exit");
		exit.setBounds(170,600,300,30);
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.WHITE);
		exit.addActionListener(this);
		add(exit);
		
		
		
		getContentPane().setBackground(Color.WHITE);
		setSize(850, 800);
		setLocation(350, 0);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == exit) {
			setVisible(false);
			new Login().setVisible(true);
		}else if(e.getSource() == list) {
			setVisible(false);
			new Mdeposit(toString()).setVisible(true);
			}else if (e.getSource()== pinfo) {
				setVisible(false);
				new Mactivities(toString()).setVisible(true);
			}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Manager("");
	}

	

}
