package bankingSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Forntoff  extends JFrame implements ActionListener{
	JLabel text;
	JButton list, pinfo,transhistory, exit;
	public Forntoff(String String){
		setLayout(null);
		
		
		text = new JLabel("ForntOfficer Access Area");
		text.setFont(new Font("Raleway", Font.BOLD, 26));
		text.setBounds(170,200,400,40);
		add(text);
		
		
		list = new JButton("Clients List");
		list.setBounds(170,250,300,30);
		list.setBackground(Color.BLACK);
		list.setForeground(Color.WHITE);
		list.addActionListener(this);
		add(list);
		
		
		pinfo = new JButton("Clients Personal Information");
		pinfo.setBounds(170,350,300,30);
		pinfo.setBackground(Color.BLACK);
		pinfo.setForeground(Color.WHITE);
		pinfo.addActionListener(this);
		add(pinfo);
		
		transhistory = new JButton("Clients Transaction History");
		transhistory.setBounds(170,450,300,30);
		transhistory.setBackground(Color.BLACK);
		transhistory.setForeground(Color.WHITE);
		transhistory.addActionListener(this);
		add(transhistory);
		
		
		exit = new JButton("Exit");
		exit.setBounds(170,600,300,30);
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.WHITE);
		exit.addActionListener(this);
		add(exit);
		
		
		
		        getContentPane().setBackground(Color.WHITE);
				setSize(700, 700);
				setLocation(350, 0);
				setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if(ae.getSource() == exit) {
			setVisible(false);
			
			new Login().setVisible(true);
		}else if(ae.getSource() == list) {
			setVisible(false);
			new ClientsList(toString()).setVisible(true);
			}else if (ae.getSource()== pinfo) {
				setVisible(false);
				new Personalinfo(toString()).setVisible(true);	
				
			}else if (ae.getSource() == transhistory ) {
				setVisible(false);
				
				new Ctrans(toString()).setVisible(true);
			}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Forntoff("");
	}

	

}
