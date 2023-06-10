package bankingSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Transaction extends JFrame implements ActionListener{
	
	
	JButton deposit, widthdraw, ministatement, pinchange, BalanceEnquiry, Exit,External;
	String Password;
	
	Transaction(String Password){
		this.Password = Password;
		setLayout(null);
		
		JLabel text = new JLabel("Transaction");
		text.setFont(new Font("Raleway", Font.BOLD, 38));
		text.setBounds(250,100,200,100);
		add(text);
		
		
		deposit = new JButton("Deposit");
		deposit.setBounds(170,250,150,30);
		deposit.setBackground(Color.BLACK);
		deposit.setForeground(Color.WHITE);
		deposit.addActionListener(this);
		add(deposit);
		
		widthdraw = new JButton("widthdraw");
		widthdraw.setBounds(170,300,150,30);
		widthdraw.setBackground(Color.BLACK);
		widthdraw.setForeground(Color.WHITE);
		widthdraw.addActionListener(this);
		add(widthdraw);
		
		ministatement = new JButton("Ministatement");
		ministatement.setBounds(170,450,150,30);
		ministatement.setBackground(Color.BLACK);
		ministatement.setForeground(Color.WHITE);
		ministatement.addActionListener(this);
		add(ministatement);
		
		pinchange = new JButton("Password Change");
		pinchange.setBounds(170,550,150,30);
		pinchange.setBackground(Color.BLACK);
		pinchange.setForeground(Color.WHITE);
		pinchange.addActionListener(this);
		add(pinchange);
		
		BalanceEnquiry = new JButton("BalanceEnquiry");
		BalanceEnquiry.setBounds(170,580,150,30);
		BalanceEnquiry.setBackground(Color.BLACK);
		BalanceEnquiry.setForeground(Color.WHITE);
		BalanceEnquiry.addActionListener(this);
		add(BalanceEnquiry);
		
		External = new JButton("External Transaction");
		External.setBounds(170,620,150,30);
		External.setBackground(Color.BLACK);
		External.setForeground(Color.WHITE);
		External.addActionListener(this);
		add(External);
		
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
			System.exit(0);
		}else if(ae.getSource() == deposit) {
			setVisible(false);
			new Deposit(Password).setVisible(true);
			}else if (ae.getSource()== pinchange) {
				setVisible(false);
				new Passwordchange(Password).setVisible(true);
				
			}else if (ae.getSource() == widthdraw ) {
				setVisible(false);
				new Widthdraw(Password).setVisible(true);
			}else if(ae.getSource() == External) {
				setVisible(false);
				new Externaltransaction(Password).setVisible(true);
			}else if(ae.getSource()== BalanceEnquiry) {
				setVisible(false);
				new Balance(Password).setVisible(true); 
			}else if(ae.getSource()==ministatement) {
				setVisible(false);
				new Ministatement(Password).setVisible(true);
			}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Transaction("");
	}

	

}
