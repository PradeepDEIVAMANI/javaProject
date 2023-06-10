package bankingSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;


public class SignupTwo extends JFrame implements ActionListener{
	
	JRadioButton r1,r2,r3,r4;
	JButton submit, cancel;
	String formno;
	SignupTwo(String formno){
		this.formno =formno;
		
		
		setLayout(null);
		
		
		JLabel l1 = new JLabel("Account Details");
		l1.setFont(new Font ("Raleway", Font.BOLD, 22));
		l1.setBounds(280,40,400,40);
		add(l1);
		
		JLabel type = new JLabel("Account Type");
		type.setFont(new Font ("Raleway", Font.BOLD, 22));
		type.setBounds(100,140,280,40);
		add(type);
		
		r1 = new JRadioButton("Savings Account");
		r1.setFont(new Font ("Raleway", Font.BOLD, 16));
		r1.setBackground(Color.WHITE);
		r1.setBounds(100, 180, 200, 40);
		add(r1);
		
		r2 = new JRadioButton("Current Account");
		r2.setFont(new Font ("Raleway", Font.BOLD, 16));
		r2.setBackground(Color.WHITE);
		r2.setBounds(350, 180, 200, 40);
		add(r2);
		
		r3 = new JRadioButton("Deposite Account");
		r3.setFont(new Font ("Raleway", Font.BOLD, 16));
		r3.setBackground(Color.WHITE);
		r3.setBounds(100, 220, 200, 40);
		add(r3);
		
		r4 = new JRadioButton("Student Account");
		r4.setFont(new Font ("Raleway", Font.BOLD, 16));
		r4.setBackground(Color.WHITE);
		r4.setBounds(350, 220, 200, 40);
		add(r4);
		
		ButtonGroup groupaccount = new ButtonGroup();
		groupaccount.add(r1);
		groupaccount.add(r2);
		groupaccount.add(r3);
		groupaccount.add(r4);
		
		
		submit = new JButton("Submit");
		submit.setBounds(310,300,200,40);
		submit.setBackground(Color.BLACK);
		submit.setForeground(Color.WHITE);
		submit.addActionListener(this);
		add(submit);
		
		cancel = new JButton("Cancel");
		cancel.setBounds(520,720,200,40);
		cancel.setBackground(Color.BLACK);
		cancel.setForeground(Color.WHITE);
		cancel.addActionListener(this);
		add(cancel);
		
		
		
		
		
		
		getContentPane().setBackground(Color.WHITE);
		setSize(850, 700);
		setLocation(350, 0);
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() == submit ){
			String accountType = null;
			if(r1.isSelected()) {
				accountType = "Savings Account";
			}else if(r2.isSelected()) {
				accountType = "Curret Account";
			}else if(r3.isSelected()) {
				accountType = "Deposite Account";
			}else if(r4.isSelected()) {
				accountType = "Student Account";
			}
			
			Random random = new Random();
			String Accountnumber ="" + Math.abs((random.nextLong()% 90000L))+ 50500L;
			
			String Password  ="" + Math.abs((random.nextLong()% 9000L))+ 100L;
			
			try {
				if(accountType.equals("")) {
					JOptionPane.showMessageDialog(null,"AccounType is Required");
				}else {
					Conn conn = new Conn();
					String query1 ="insert into signupTwo values ('"+formno+"','"+accountType+"','"+Accountnumber+"','"+Password+"')";
					String query2 ="insert into login values ('"+formno+"','"+Accountnumber+"','"+Password+"')";
					
					conn.s.executeUpdate(query1);
					conn.s.executeUpdate(query2);
					
					JOptionPane.showMessageDialog(null,"Account Number: "+Accountnumber+ "/n Pin: "+Password);
				}
				setVisible(false);
				new Transaction(Password).setVisible(true);
			}catch(Exception e) {
				System.out.println(e);
			}
			
			}else if(ae.getSource() == cancel ) {
			setVisible(false);
			new Login().setVisible(true);
		}
	}
	 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SignupTwo("");
	}

}