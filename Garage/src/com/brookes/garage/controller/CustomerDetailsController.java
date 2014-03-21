package com.brookes.garage.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.brookes.garage.dao.CarDao;
import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.Customers_car;
import com.brookes.garage.entity.Model;
import com.brookes.garage.frame.CarFormFrame;
import com.brookes.garage.frame.CustomerDetailsFrame;
import com.brookes.garage.tablemodel.CustomerCarTableModel;

public class CustomerDetailsController implements ActionListener, ListSelectionListener {

	private Customer customer;
	
	public CustomerDetailsFrame mainFrame;
	private CarFormFrame carForm;

	private CarDao carDao = DaoFactory.getCarDao();
	private CustomerCarTableModel carTableModel;

	public CustomerDetailsController() {
		super();

		this.createPage();
	
	}

	/**
	 * Create and configure the list frame
	 */
	private void createPage() {
		if (mainFrame == null) {
			mainFrame = new CustomerDetailsFrame();

			// Set the table model and add itself
			carTableModel = new CustomerCarTableModel();
			mainFrame.carTable.setModel(carTableModel);


			// Add itself as action listener to create, edit and delete a car
			mainFrame.addCarButton.addActionListener(this);
		}
	}

	/**
	 * An action should be performed based on the button triggering it
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainFrame.addCarButton) {
			this.showCarCreationForm();
		} else if (e.getSource() == carForm.brandComboBox) {
			Brand brand = (Brand) carForm.brandComboBox.getSelectedItem();
			List<Model> models = brand.getModels();
			Model[] array = models.toArray(new Model[models.size()]);
			DefaultComboBoxModel<Model> model = new DefaultComboBoxModel<Model>(array);
			carForm.modelComboBox.setModel(model);
			carForm.modelComboBox.setEnabled(true);
		}  else if (e.getSource() == carForm.saveButton) {
			this.addCarToCustomer(carForm.identifierField.getText(), (Model) carForm.modelComboBox.getSelectedItem());
		}
	}
	
	/**
	 * Add a new car according to a customer and a (Brand->) model
	 * triggered by the save button
	 */
	public void addCarToCustomer(String plate, Model model) {
		if(plate.isEmpty()||model.equals(null)){
			//no plate or model defined before save -> pop up "error"
			JOptionPane.showMessageDialog(null, "You must specify a plate and a model.");
		}else{
			//must verify if the plate already exist in the DB
			Customers_car customer_car = new Customers_car();
			customer_car.setModel(model);
			customer_car.setNumber_plate(plate);
			customer_car.setCustomer(customer);
			carDao.addCar(customer_car);
			carTableModel.addCar(customer_car);
			carForm.dispose();
			
		}
		
	}

	/**
	 * Display the car creation form in a new window. Listen to the action
	 * triggered by the save button
	 */
	public void showCarCreationForm() {
		carForm = new CarFormFrame();
		carForm.saveButton.addActionListener(this);
		carForm.setVisible(true);
	}

	/**
	 * Triggered when the user wish to save a customer in either the creation or
	 * edition form
	 */
	public void saveCustomer() {
		/*String firstname = customerForm.firstnameField.getText();
		String lastname = customerForm.lastnameField.getText();
		String phone = customerForm.phoneField.getText();
		String address = customerForm.addressField.getText();

		if (firstname.length() > 0 && lastname.length() > 0
				&& phone.length() > 0 && address.length() > 0) {
			// Every fields contains a value

			if (customerForm.getCustomer() != null) {
				// The form contains an existing customer
				// This was an edition form
				// We update the values in the existing customer and update it
				Customer customer = customerForm.getCustomer();
				customer.setFirstname(firstname);
				customer.setLastname(lastname);
				customer.setPhone_number(phone);
				customer.setAddress(address);

				// We update the database and our table model
				customerDao.updateCustomer(customer);
				tableModel.updateCustomer(customer);
			} else {
				// The form was a creation form
				// We create a new customer with the values and save it
				Customer customer = new Customer();
				customer.setFirstname(firstname);
				customer.setLastname(lastname);
				customer.setPhone_number(phone);
				customer.setAddress(address);

				// We add it to the database and our table model
				customerDao.addCustomer(customer);
				tableModel.addCustomer(customer);
			}

			// We close the windows
			customerForm.dispose();
		} else {
			// Not every fields contain a value so we display a message
			customerForm.noEmptyLabel.setVisible(true);
		}*/
	}

	/**
	 * The row selected by the user has changed
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		/*if (e.getSource() == customerListFrame.table.getSelectionModel()) {
			// Since a row is now selected, we enable the edit, view and delete
			// button
			customerListFrame.editButton.setEnabled(true);
			customerListFrame.viewButton.setEnabled(true);
			customerListFrame.deleteButton.setEnabled(true);
		}*/
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		mainFrame.nameLabel.setText(customer.getFirstname() + " " + customer.getLastname());
		mainFrame.addressLabel.setText(customer.getAddress());
		mainFrame.phoneLabel.setText(customer.getPhone_number());
		carTableModel.refreshTable(customer);
	}
	
}