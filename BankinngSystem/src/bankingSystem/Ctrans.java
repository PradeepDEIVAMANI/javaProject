package bankingSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

public class Ctrans extends JFrame implements ActionListener{
	JLabel text;
	JTable t1;
	JButton exit, Submit;
	JTextField accountNumberField;
	public Ctrans(String string) {
		
		setLayout(null);
		
		text = new JLabel("Client Transaction");
		text.setFont(new Font("Raleway", Font.BOLD, 36));
		text.setBounds(170,100,400,40);
		add(text);
		text = new JLabel("Enter the AccountNumber");
		text.setFont(new Font("Raleway", Font.BOLD, 26));
		text.setBounds(10,200,400,40);
		add(text);
		
		 accountNumberField = new JTextField();
	        accountNumberField.setBounds(350, 200, 150, 40);
	        add(accountNumberField);
		
		exit = new JButton("Exit");
		exit.setBounds(355,600,150,30);
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.WHITE);
		exit.addActionListener(this);
		add(exit);
		
		Submit = new JButton("Submit");
		Submit.setBounds(355,500,150,30);
		Submit.setBackground(Color.BLACK);
		Submit.setForeground(Color.WHITE);
		Submit.addActionListener(this);
		add(Submit);
		
		
		t1 = new JTable();
		t1.setBounds(20, 300, 700, 200);
		add(t1);
		
		
		JScrollPane js = new JScrollPane(t1);
		js.setBounds(50,300,700,200);
		add(js);
		
		
		
		getContentPane().setBackground(Color.WHITE);
		setSize(850, 800);
		setLocation(350, 0);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Ctrans("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == exit) {
			setVisible(false);
			new Manager(toString()).setVisible(true);
			}else  if (e.getSource() == Submit) {
	            
				String accountNumber = accountNumberField.getText();
	          
	            
	            try {
	    			Conn c = new Conn();
	    			
	    			ResultSet rs = c.s.executeQuery("SELECT pin as Accountnumber ,type,date, amount FROM bank WHERE pin ='"+accountNumber+"' ");
	    			t1.setModel(DbUtils.resultSetToTableModel(rs));
	    			while(rs.next()) {
	    				
	    			}
	    			
	    		}catch (Exception e1) {
	    			System.out.println(e1);
	    		}
			}
		
	}

}
