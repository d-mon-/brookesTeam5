package com.brookes.garage.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.brookes.garage.dao.CustomerDao;
import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.frame.CustomerDetailsFrame;
import com.brookes.garage.frame.CustomerFormFrame;
import com.brookes.garage.frame.CustomerListFrame;
import com.brookes.garage.tablemodel.CustomerTableModel;

public class CustomerModuleController implements ActionListener,
		ListSelectionListener {

    final static String LISTFRAME = "list";
    final static String DETAILSFRAME = "details";
    
	public JPanel mainPanel;

	private CustomerListFrame customerListFrame;
	private CustomerDetailsFrame customerDetailsFrame;
	private CustomerFormFrame customerForm;

	private CustomerDao customerDao = DaoFactory.getCustomerDao();
	private CustomerTableModel tableModel;

	public CustomerModuleController() {
		super();

		this.createListPage();
		this.createDetailsPage();
        
		mainPanel = new JPanel(new CardLayout());
		mainPanel.setOpaque(false);
		mainPanel.add(customerListFrame.contentPane, LISTFRAME);
		mainPanel.add(customerDetailsFrame.contentPane, DETAILSFRAME);
	
	}

	/**
	 * Create and configure the list frame
	 */
	private void createListPage() {
		if (customerListFrame == null) {
			customerListFrame = new CustomerListFrame();

			// Set the table model and add itself as selection listener for the
			// table
			tableModel = new CustomerTableModel();
			customerListFrame.table.setModel(tableModel);
			customerListFrame.table.getSelectionModel().addListSelectionListener(
					this);

			// Create and configure a table row sorter
			// so that the user is able to sort the table according to columns
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
					customerListFrame.table.getModel());
			customerListFrame.table.setRowSorter(sorter);
			sorter.setSortable(2, false);
			sorter.setSortsOnUpdates(true);

			// Add itself as action listener to create, edit and delete a customer
			customerListFrame.createButton.addActionListener(this);
			customerListFrame.editButton.addActionListener(this);
			customerListFrame.deleteButton.addActionListener(this);
			customerListFrame.viewButton.addActionListener(this);
		}
	}

	/**
	 * Create the details frame
	 */
	private void createDetailsPage() {
		if (customerDetailsFrame == null) {
			customerDetailsFrame = new CustomerDetailsFrame();
			customerDetailsFrame.backButton.addActionListener(this);
		}
	}
	
	/**
	 * An action should be performed based on the button triggering it
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == customerListFrame.createButton) {
			this.showCustomerCreationForm();
		} else if (e.getSource() == customerListFrame.editButton) {
			this.showCustomerEditionForm();
		} else if (e.getSource() == customerListFrame.deleteButton) {
			this.deleteCustomer();
		} else if (e.getSource() == customerListFrame.viewButton) {
			this.viewCustomerDetails();
		} else if (customerForm != null && e.getSource() == customerForm.saveButton) {
			this.saveCustomer();
		} else if (customerDetailsFrame != null && e.getSource() == customerDetailsFrame.backButton) {
			this.goBackToCustomerList();
		}
	}

	/**
	 * Display the customer creation form in a new window Listen to the action
	 * triggered by the save button
	 */
	public void showCustomerCreationForm() {
		customerForm = new CustomerFormFrame();
		customerForm.saveButton.addActionListener(this);

		customerForm.setVisible(true);
	}

	/**
	 * Displays the customer edition form in a new window It consists of the
	 * same frame as for creation but already filled-in
	 */
	public void showCustomerEditionForm() {
		customerForm = new CustomerFormFrame();
		customerForm.saveButton.addActionListener(this);

		// We get the index of the selected row and retrieve the corresponding
		// Customer entity
		int rowIndex = customerListFrame.table.getSelectedRow();
		Customer customer = tableModel.data.get(rowIndex);
		customerForm.setCustomer(customer);

		// We fill-in the form
		customerForm.firstnameField.setText(customer.getFirstname());
		customerForm.lastnameField.setText(customer.getLastname());
		customerForm.phoneField.setText(customer.getPhone_number());
		customerForm.addressField.setText(customer.getAddress());

		customerForm.setVisible(true);
	}

	/**
	 * Triggered when the user wish to save a customer in either the creation or
	 * edition form
	 */
	public void saveCustomer() {
		String firstname = customerForm.firstnameField.getText();
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
		}
	}

	/**
	 * Triggered when the user have selected a row and clicked the delete button
	 */
	public void deleteCustomer() {
		// We get the currently selected row index, get the customer
		// and remove it from the table model and the database
		int rowIndex = customerListFrame.table.getSelectedRow();
		Customer customer = tableModel.data.get(rowIndex);
		tableModel.removeCustomer(rowIndex);
		customerDao.removeCustomer(customer);
	}

	/**
	 * The row selected by the user has changed
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == customerListFrame.table.getSelectionModel()) {
			// Since a row is now selected, we enable the edit, view and delete
			// button
			customerListFrame.editButton.setEnabled(true);
			customerListFrame.viewButton.setEnabled(true);
			customerListFrame.deleteButton.setEnabled(true);
		}
	}
	
	/**
	 * Fills the appropriate data into the details page and displays it
	 */
	public void viewCustomerDetails() {
		int rowIndex = customerListFrame.table.getSelectedRow();
		Customer customer = tableModel.data.get(rowIndex);
		
		customerDetailsFrame.nameLabel.setText(customer.getFirstname() + " " + customer.getLastname());
		customerDetailsFrame.addressLabel.setText(customer.getAddress());
		customerDetailsFrame.phoneLabel.setText(customer.getPhone_number());
		
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, DETAILSFRAME);
	}
	
	/**
	 * Go back to the customer list (from the details page)
	 */
	public void goBackToCustomerList() {
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, LISTFRAME);
	}
}