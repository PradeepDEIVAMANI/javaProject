package bankingSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

public class Personalinfo extends JFrame implements ActionListener{
		JLabel text;
		JTable t1;
		JButton exit;
	public Personalinfo(String string){
		setLayout(null);
		
		text = new JLabel("Clients Personal Information");
		text.setFont(new Font("Raleway", Font.BOLD, 36));
		text.setBounds(170,100,500,40);
		add(text);
		
		exit = new JButton("Exit");
		exit.setBounds(355,650,150,30);
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.WHITE);
		exit.addActionListener(this);
		add(exit);
		
		
		t1 = new JTable();
		t1.setBounds(20, 300, 700, 200);
		add(t1);
		
		try {
			Conn c = new Conn();
			
			ResultSet rs = c.s.executeQuery("select * from Singup ");
			t1.setModel(DbUtils.resultSetToTableModel(rs));
			while(rs.next()) {
				
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}
		JScrollPane js = new JScrollPane(t1);
		js.setBounds(50,300,700,200);
		add(js);
		
		
		
		
		
		getContentPane().setBackground(Color.WHITE);
		setSize(850, 800);
		setLocation(350, 0);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		setVisible(false);
		new Forntoff(toString()).setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				new Personalinfo("");
	}

	

}
