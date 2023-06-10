package bankingSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.mysql.cj.jdbc.ClientPreparedStatement;
import com.mysql.cj.protocol.Resultset;

import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.io.*;



public class Ministatement extends JFrame implements ActionListener{
	
	String Password;
	JTable t1;
	JButton exit, Download;
	Ministatement(String Password){
		this.Password = Password;
		
		setTitle("Mini-Statement");
		setLayout(null);
		
		JLabel text1 = new JLabel();
		add(text1);
		
		JLabel text2 = new JLabel("HDFS Bank");
		text2.setFont(new Font("Raleway", Font.BOLD, 38));
		text2.setBounds(150,20,500,40);
		add(text2);
		
		JLabel Account = new JLabel();
		Account.setFont(new Font("Raleway", Font.BOLD, 22));
		Account.setBounds(20,80,500,100);
		add(Account);
		
		JLabel balance = new JLabel();
		balance.setFont(new Font("Raleway", Font.BOLD, 22));
		balance.setBounds(20,600,750,100);
		add(balance);
		
		exit = new JButton("Exit");
		exit.setBounds(355,700,150,30);
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.WHITE);
		exit.addActionListener(this);
		add(exit);
		
		Download = new JButton("Download");
		Download.setBounds(150,700,150,30);
		Download.setBackground(Color.BLACK);
		Download.setForeground(Color.WHITE);
		Download.addActionListener(this);
		add(Download);
		
		
		t1 = new JTable();
		t1.setBounds(20, 300, 700, 200);
		add(t1);
		
		try {
			Conn c = new Conn();
			ResultSet rs = c.s.executeQuery("select * from login where pin ='"+Password+"'");
			
			while(rs.next()) {
				Account.setText("Account Number:" +rs.getString("Accountnumber"));
			}
		}
		
		catch (Exception e) 
		
		{
			System.out.println(e);
		}
		
		try {
			Conn c = new Conn();
			int bal=0;
			ResultSet rs = c.s.executeQuery("select * from bank where pin ='"+Password+"'");
			t1.setModel(DbUtils.resultSetToTableModel(rs));
			while(rs.next()) {
				//Account.setText("Account Number:" +rs.getString("Accountnumber"));
				if (rs.getString("type").equals("deposit")) {
					bal += Integer.parseInt(rs.getString("amount"));
				}else {
					bal -= Integer.parseInt(rs.getString("amount"));
				}
			}
			balance.setText("Your Balance is: "+bal);
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
		if(e.getSource() == exit) {
			setVisible(false);
			new Transaction(Password).setVisible(true);
		}else if(e.getSource() == Download) {
			//setVisible(false);
			Conn c = new Conn();
			String filename ="C:\\Users\\Aravind\\OneDrive\\Desktop\\Data\\data.csv";
			String query =("select * from bank where pin ='"+Password+"'");
		try {
			
			 PrintWriter pw= new PrintWriter(new File(filename));
			    StringBuilder sb=new StringBuilder();
			 

			    PreparedStatement ps = c.prepareStatement(query);
			    ResultSet rs= ps.executeQuery(query);
			 
			    while(rs.next()){
			     sb.append(rs.getString("pin"));
			     sb.append(","); 
			     sb.append(rs.getString("date"));
			     sb.append(",");
			     sb.append(rs.getString("type"));
			     sb.append(",");
			     sb.append(rs.getString("amount"));
			     sb.append(",");
			     
			     sb.append("\r\n");
			    }
			    JOptionPane.showMessageDialog(null, "Data Exported SuccessFully");
			    pw.write(sb.toString());
			    pw.close();
			   
			
		}catch (Exception e1) {
			System.out.println(e1);
		}
	}

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Ministatement("");
	}

	
}
