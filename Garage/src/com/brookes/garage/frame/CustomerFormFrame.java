package com.brookes.garage.frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.brookes.garage.entity.Customer;

public class CustomerFormFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	public JTextField firstnameField;
	public JTextField lastnameField;
	public JTextField phoneField;
	public JTextArea addressField;
	
	private Customer customer;
	
	public JLabel noEmptyLabel;
	
	public JButton okButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			StatusFormFrame dialog = new StatusFormFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CustomerFormFrame() {
		setResizable(false);
		setBounds(100, 100, 337, 247);
		getContentPane().setLayout(null);
		
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
		JScrollPane scrollPane = new JScrollPane(addressField);
		scrollPane.setBounds(102, 104, 222, 72);
		addressField.setLineWrap(true);
		
		noEmptyLabel = new JLabel("No field can be empty");
		noEmptyLabel.setForeground(Color.RED);
		noEmptyLabel.setBounds(171, 188, 138, 16);
		noEmptyLabel.setVisible(false);
		
		getContentPane().add(lblFirstname);
		getContentPane().add(firstnameField);
		getContentPane().add(lblLastname);
		getContentPane().add(lastnameField);
		getContentPane().add(lblPhoneNumber);
		getContentPane().add(phoneField);
		getContentPane().add(lblAddress);
		getContentPane().add(scrollPane);
		getContentPane().add(noEmptyLabel);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(3, 180, 369, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.LEFT));
			getContentPane().add(buttonPane);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
		}
		noEmptyLabel.setVisible(false);
		
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
