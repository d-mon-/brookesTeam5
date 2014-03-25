package com.brookes.garage.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.Customers_car;

public class RepairFormFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	public JComboBox<Customer> customerComboBox;
	public JComboBox<Customers_car> carComboBox;
	public JTextField descriptionField;
	
	public JLabel noEmptyLabel;
	
	public JButton cancelButton;
	public JButton saveButton;
	

	/**
	 * Create the frame.
	 */
	public RepairFormFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 329, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblLastname = new JLabel("Customer");
		lblLastname.setBounds(33, 10, 62, 16);
		lblLastname.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(6, 76, 89, 16);
		lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
		
		descriptionField = new JTextField();
		descriptionField.setBounds(100, 70, 222, 72);
		descriptionField.setColumns(13);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(6, 148, 86, 29);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		saveButton = new JButton("Save");
		saveButton.setBounds(98, 148, 75, 29);
		saveButton.setHorizontalAlignment(SwingConstants.LEFT);
		
		noEmptyLabel = new JLabel("No field can be empty");
		noEmptyLabel.setBounds(179, 153, 138, 16);
		noEmptyLabel.setVisible(false);
		
		contentPane.setLayout(null);
		contentPane.add(lblLastname);
		contentPane.add(lblDescription);
		contentPane.add(descriptionField);
		contentPane.add(cancelButton);
		contentPane.add(saveButton);
		contentPane.add(noEmptyLabel);
		
		List<Customer> customers = DaoFactory.getCustomerDao().getAllCustomers();
		Customer[] customersArray = customers.toArray(new Customer[customers.size()]);
		
		customerComboBox = new JComboBox<Customer>(customersArray);
		customerComboBox.setBounds(100, 6, 222, 27);
		contentPane.add(customerComboBox);
		
		JLabel lblCar = new JLabel("Car");
		lblCar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCar.setBounds(33, 42, 62, 16);
		contentPane.add(lblCar);
		
		
		Customer customer = (Customer) customerComboBox.getSelectedItem();
		List<Customers_car> cars = DaoFactory.getCarDao().getCarsByCustomer(customer);
		Customers_car[] carsArray = cars.toArray(new Customers_car[cars.size()]);
		
		carComboBox = new JComboBox<Customers_car>(carsArray);
		carComboBox.setBounds(100, 38, 222, 27);
		contentPane.add(carComboBox);
	}
	
}
