package bankingSystem;

import javax.swing.JFrame;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
import java.awt.*;


public class Signup extends JFrame implements ActionListener {
	
	long random;
	JTextField FnameTextField, LnameTextField, DobTextField, emailTextField, cityTextField;
	JLabel formno, Fname, Lname, Dob, Gender, email, City, PersonalDetails;
	JRadioButton male, female;
	JDateChooser dateChooser;
	JButton CreateAccount;
	Signup() {
		
		setLayout(null);
		
		Random ran = new Random();
		random = Math.abs((ran.nextLong()% 9000L) +1000L);
		
		formno = new JLabel("APPLICATION NUMBER." +random);
		formno.setFont(new Font("Raleway", Font.BOLD, 38));
		formno.setBounds(140, 20, 600, 40);
		add(formno);
		
		PersonalDetails = new JLabel("Personal Details");
		PersonalDetails.setFont(new Font("Raleway", Font.BOLD, 22));
		PersonalDetails.setBounds(290, 80, 200, 30);
		add(PersonalDetails);
		
		Fname = new JLabel("FirstName: ");
		Fname.setFont(new Font("Raleway", Font.BOLD, 20));
		Fname.setBounds(100, 140, 200, 30);
		add(Fname);
		
		FnameTextField = new JTextField();
		FnameTextField.setFont(new Font("Raleway",Font.BOLD,14));
		FnameTextField.setBounds(300, 140, 400, 30);
		add(FnameTextField);
		
		Lname = new JLabel("LastName: ");
		Lname.setFont(new Font("Raleway", Font.BOLD, 20));
		Lname.setBounds(100, 190, 200, 30);
		add(Lname);
		
		LnameTextField = new JTextField();
		LnameTextField.setFont(new Font("Raleway",Font.BOLD,14));
		LnameTextField.setBounds(300, 190, 400, 30);
		add(LnameTextField);
		
		Dob = new JLabel("DOB: ");
		Dob.setFont(new Font("Raleway", Font.BOLD, 20));
		Dob.setBounds(100, 240, 200, 30);
		add(Dob);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(300, 240, 400, 30);
		dateChooser.setForeground(new Color(105, 105, 105));
		add(dateChooser);
		
		
		DobTextField = new JTextField();
		DobTextField.setFont(new Font("Raleway",Font.BOLD,14));
		DobTextField.setBounds(300, 240, 400, 30);
		add(DobTextField);
		
		Gender = new JLabel("Gender: ");
		Gender.setFont(new Font("Raleway", Font.BOLD, 20));
		Gender.setBounds(100, 290, 200, 30);
		add(Gender);
		
		male = new JRadioButton("Male");
		male.setBounds(300, 290, 60, 30);
		male.setBackground(Color.WHITE);
		add(male);
		
		female = new JRadioButton("Female");
		female.setBounds(450, 290, 100, 30);
		female.setBackground(Color.WHITE);
		add(female);
		
		ButtonGroup gendergroup = new ButtonGroup();
		gendergroup.add(male);
		gendergroup.add(female);
		
		
		
		email = new JLabel("E-mail: ");
		email.setFont(new Font("Raleway", Font.BOLD, 20));
		email.setBounds(100, 340, 200, 30);
		add(email);
		
		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Raleway",Font.BOLD,14));
		emailTextField.setBounds(300, 340, 400, 30);
		add(emailTextField);
		
		City = new JLabel("City: ");
		City.setFont(new Font("Raleway", Font.BOLD, 20));
		City.setBounds(100, 390, 200, 30);
		add(City);
		
		cityTextField = new JTextField();
 		cityTextField.setFont(new Font("Raleway",Font.BOLD,14));
		cityTextField.setBounds(300, 390, 400, 30);
		add(cityTextField);
		
		
		CreateAccount = new JButton("Create Account");
		CreateAccount.setBounds(300,450,400,30);
		CreateAccount.setBackground(Color.BLACK);
		CreateAccount.setForeground(Color.WHITE);
		CreateAccount.addActionListener(this);
		add(CreateAccount);
		
		
		 
		
		
		getContentPane().setBackground(Color.WHITE);
		setSize(850, 800);
		setLocation(350, 10);
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		String formno = "" + random;
		String Fname = FnameTextField.getText();
		String Lname = LnameTextField.getText();
		String Dob = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
		String gender = null;
		if(male.isSelected()) {
			gender = "Male";
		}else if (female.isSelected()) {
			gender = "Female";
		}
		
		String email = emailTextField.getText();
		String city  = cityTextField.getText();
		
		try {
			if (Fname.equals("")) {
				JOptionPane.showMessageDialog(null, "Name is Required");
			}else {
				Conn c = new Conn();
				String query = "insert into singup values('"+formno+"','"+Fname+"','"+Lname+"','"+Dob+"','"+gender+ "','"+email+"','"+city+"')";
				c.s.executeUpdate(query);	
				
				//signup2object
				setVisible(false);
				
				new SignupTwo (formno).setVisible(true);
			}
		}catch (Exception e) {
			System.out.println(e);
		}
 		
	}
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Signup();

	}

}
