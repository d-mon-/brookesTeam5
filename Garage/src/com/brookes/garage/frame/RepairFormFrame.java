package com.brookes.garage.frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.Customers_car;
import java.awt.Color;

public class RepairFormFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	public JComboBox<Customer> customerComboBox;
	public JComboBox<Customers_car> carComboBox;
	public JTextArea descriptionField;
	
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
	public RepairFormFrame() {
		setResizable(false);
		setBounds(100, 100, 337, 211);
		getContentPane().setLayout(null);
		
		
		JLabel lblLastname = new JLabel("Customer");
		lblLastname.setBounds(33, 10, 62, 16);
		lblLastname.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(6, 76, 89, 16);
		lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
		
		descriptionField = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(descriptionField);
		scrollPane.setBounds(100, 70, 222, 72);
		descriptionField.setLineWrap(true);
		descriptionField.setWrapStyleWord(true);
		
		noEmptyLabel = new JLabel("No field can be empty");
		noEmptyLabel.setForeground(Color.RED);
		noEmptyLabel.setBounds(183, 157, 138, 16);
		noEmptyLabel.setVisible(false);
		
		
		getContentPane().add(lblLastname);
		getContentPane().add(lblDescription);
		getContentPane().add(scrollPane);
		getContentPane().add(noEmptyLabel);
		
		List<Customer> customers = DaoFactory.getCustomerDao().getAllCustomers();
		Customer[] customersArray = customers.toArray(new Customer[customers.size()]);
		
		customerComboBox = new JComboBox<Customer>(customersArray);
		customerComboBox.setBounds(100, 6, 222, 27);
		getContentPane().add(customerComboBox);
		
		JLabel lblCar = new JLabel("Car");
		lblCar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCar.setBounds(33, 42, 62, 16);
		getContentPane().add(lblCar);
		
		
		Customer customer = (Customer) customerComboBox.getSelectedItem();
		List<Customers_car> cars = DaoFactory.getCarDao().getCarsByCustomer(customer);
		Customers_car[] carsArray = cars.toArray(new Customers_car[cars.size()]);
		
		carComboBox = new JComboBox<Customers_car>(carsArray);
		carComboBox.setBounds(100, 38, 222, 27);
		getContentPane().add(carComboBox);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 148, 369, 33);
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
	
}
