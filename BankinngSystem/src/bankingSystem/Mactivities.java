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

import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

public class Mactivities extends JFrame implements ActionListener{
	
	
	JLabel text, text1;
	JTable t1;
	JButton exit,calculate;
	JDateChooser startDateChooser;
	JDateChooser endDateChooser;
	JTextField accountNumberField;
	
	public Mactivities(String string) {
		setLayout(null);
		
		
		text = new JLabel("Client Sum of deposit");
		text.setFont(new Font("Raleway", Font.BOLD, 26));
		text.setBounds(100,100,400,40);
		add(text);
		
		text = new JLabel("Enter the AccountNumber");
		text.setFont(new Font("Raleway", Font.BOLD, 26));
		text.setBounds(10,200,400,40);
		add(text);
		
		 accountNumberField = new JTextField();
	        accountNumberField.setBounds(350, 200, 150, 40);
	        add(accountNumberField);

		
		
		 startDateChooser = new JDateChooser();
	      startDateChooser.setBounds(150, 250, 150, 30);
	      add(startDateChooser);

	        endDateChooser = new JDateChooser();
	        endDateChooser.setBounds(400, 250, 150, 30);
	        add(endDateChooser);
		
		
		exit = new JButton("Exit");
		exit.setBounds(250,600,300,30);
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.WHITE);
		exit.addActionListener(this);
		add(exit);
		
		calculate = new JButton("Submit");
        calculate.setBounds(250, 500, 300, 30);
        calculate.setBackground(Color.BLACK);
        calculate.setForeground(Color.WHITE);
        calculate.addActionListener(this);
        add(calculate);
		
		
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
			new Mactivities("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == exit) {
		setVisible(false);
		new Manager(toString()).setVisible(true);
		}else  if (e.getSource() == calculate) {
            
			String accountNumber = accountNumberField.getText();
            Date startDate = startDateChooser.getDate();
            Date endDate = endDateChooser.getDate();

           
            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
            
            try {
    			Conn c = new Conn();
    			
    			ResultSet rs = c.s.executeQuery("SELECT pin as Accountnumber ,type, date, amount FROM bank WHERE pin ='"+accountNumber+"' AND type = 'deposit' AND date >= '"+startDate+"' AND date <= '"+endDate+"'");
    			t1.setModel(DbUtils.resultSetToTableModel(rs));
    			while(rs.next()) {
    				
    			}
    			
    		}catch (Exception e1) {
    			System.out.println(e1);
    		}

		}
	}
}
