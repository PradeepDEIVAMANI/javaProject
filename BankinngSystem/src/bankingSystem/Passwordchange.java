package bankingSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Passwordchange extends JFrame implements ActionListener {
	
	JLabel Passtext, newpass,repass;
	JPasswordField tpass, trepass;
	JButton change, exit;
	String Password;
	
	Passwordchange(String Password){
		this.Password = Password;
	
	setLayout(null);
	
	Passtext = new JLabel("EDIT PROFILE");
	Passtext.setFont(new Font("Raleway", Font.BOLD, 26));
	Passtext.setBounds(170,200,400,40);
	add(Passtext);
	
	
	newpass = new JLabel("ENTER NEW PASSWORD");
	newpass.setFont(new Font("Raleway", Font.BOLD, 20));
	newpass.setBounds(60,320,400,40);
	add(newpass);
	
	
	tpass = new JPasswordField();
	tpass.setFont(new Font("Raleway", Font.BOLD, 26));
	tpass.setBounds(330,320,400,40);
	add(tpass);
	
	repass = new JLabel("RE-ENTER NEW PASSWORD");
	repass.setFont(new Font("Raleway", Font.BOLD, 20));
	repass.setBounds(60,400,400,40);
	add(repass);
	
	trepass = new JPasswordField();
	trepass.setFont(new Font("Raleway", Font.BOLD, 26));
	trepass.setBounds(330,400,400,40);
	add(trepass);
	
	change = new JButton("Change");
	change.setBounds(355,485,150,30);
	change.setBackground(Color.BLACK);
	change.setForeground(Color.WHITE);
	change.addActionListener(this);
	add(change);
	
	exit = new JButton("Exit");
	exit.setBounds(355,550,150,30);
	exit.setBackground(Color.BLACK);
	exit.setForeground(Color.WHITE);
	exit.addActionListener(this);
	add(exit);
	
	
	
	
	getContentPane().setBackground(Color.WHITE);
	setSize(800,800);
	setLocation(300,0);
	setVisible(true);
	
	
	
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource()== change) {
			
		
		try {
			String newpass = tpass.getText();
			String repass = tpass.getText();
			
			if(!newpass.equals(repass)) {
				JOptionPane.showMessageDialog(null, "Entered PIN Password not match");
				return;
			}
			
			if (newpass.equals("")) {
				JOptionPane.showMessageDialog(null, "Please Enter the new Password");
				return;
			}
		
		
		if (repass.equals("")) {
			JOptionPane.showMessageDialog(null, "Please Re - Enter the new Password");
			return;
		}
		
		Conn conn = new Conn();
		String query1 ="Update bank set pin = '"+repass+"'where pin='"+Password+"'";
		String query2 ="Update login set pin = '"+repass+"'where pin='"+Password+"'";	
		String query3 ="Update SignupTwo set passwordf = '"+repass+"'where passwordf='"+Password+"'";
		
		conn.s.executeUpdate(query1);
		conn.s.executeUpdate(query2);
		conn.s.executeUpdate(query3);
		
		JOptionPane.showMessageDialog(null, "Password Changed Successfully");
		
		setVisible(false);
		new Transaction(repass).setVisible(true);
		
		}catch (Exception e) {
		System.out.println(e);
		}
		}
		else {
			
			setVisible(false);
			new Transaction(Password).setVisible(true);
		}
		}
		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Passwordchange("").setVisible(true);
	}
	

}
