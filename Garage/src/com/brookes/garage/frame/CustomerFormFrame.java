package com.brookes.garage.frame;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.brookes.garage.entity.Customer;

import java.awt.Color;


public class CustomerFormFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	public JTextField firstnameField;
	public JTextField lastnameField;
	public JTextField phoneField;
	public JTextArea addressField;
	
	public JLabel noEmptyLabel;
	
	public JButton cancelButton;
	public JButton saveButton;
	
	private Customer customer;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CustomerFormFrame frame = new CustomerFormFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CustomerFormFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 329, 239);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(35, 11, 62, 16);
		lblFirstname.setHorizontalAlignment(SwingConstants.RIGHT);
		
		firstnameField = new JTextField();
		firstnameField.setBounds(102, 5, 222, 28);
		lblFirstname.setLabelFor(firstnameField);
		firstnameField.setColumns(10);
		
		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(37, 44, 60, 16);
		lblLastname.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lastnameField = new JTextField();
		lastnameField.setBounds(102, 38, 222, 28);
		lastnameField.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(5, 77, 92, 16);
		lblPhoneNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		
		phoneField = new JTextField();
		phoneField.setBounds(102, 71, 222, 28);
		phoneField.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(46, 110, 51, 16);
		lblAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		
		addressField = new JTextArea(4, 5);
		
		addressField.setBounds(102, 104, 222, 72);
		addressField.setColumns(13);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(8, 182, 86, 29);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		saveButton = new JButton("Save");
		saveButton.setBounds(100, 182, 75, 29);
		saveButton.setHorizontalAlignment(SwingConstants.LEFT);
		
		noEmptyLabel = new JLabel("No field can be empty");
		noEmptyLabel.setForeground(Color.RED);
		noEmptyLabel.setBounds(181, 187, 138, 16);
		noEmptyLabel.setVisible(false);
		
		contentPane.setLayout(null);
		contentPane.add(lblFirstname);
		contentPane.add(firstnameField);
		contentPane.add(lblLastname);
		contentPane.add(lastnameField);
		contentPane.add(lblPhoneNumber);
		contentPane.add(phoneField);
		contentPane.add(lblAddress);
		contentPane.add(addressField);
		contentPane.add(cancelButton);
		contentPane.add(saveButton);
		contentPane.add(noEmptyLabel);
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
