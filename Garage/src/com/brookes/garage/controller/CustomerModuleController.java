package com.brookes.garage.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.brookes.garage.dao.CustomerDao;
import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.frame.CustomerFormFrame;
import com.brookes.garage.frame.CustomerListFrame;
import com.brookes.garage.tablemodel.CustomerTableModel;

public class CustomerModuleController implements ActionListener, ListSelectionListener {

	// Identifiers used to switch between the list and the details
    final static String LISTFRAME = "list";
    final static String DETAILSFRAME = "details";
    
    // The panel managed by the controller
	public JPanel mainPanel;

	// Different frames and controllers displayed by this module
	private CustomerListFrame customerListFrame;
	private CustomerDetailsController customerDetailsController;
	private CustomerFormFrame customerForm;

	// Data source related objects
	private CustomerDao customerDao = DaoFactory.getCustomerDao();
	private CustomerTableModel tableModel;
	private TableRowSorter<TableModel>  sorter;

	
	/**
	 * The constructor method
	 */
	public CustomerModuleController() {
		super();

		this.createListPage();
		this.createDetailsPage();
        
		mainPanel = new JPanel(new CardLayout());
		mainPanel.setOpaque(false);
		mainPanel.add(customerListFrame.contentPane, LISTFRAME);
		mainPanel.add(customerDetailsController.mainFrame.contentPane, DETAILSFRAME);
	
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
			sorter = new TableRowSorter<TableModel>(
					customerListFrame.table.getModel());
			customerListFrame.table.setRowSorter(sorter);
			sorter.setSortable(2, false);
			sorter.setSortsOnUpdates(true);

			// Add itself as action listener to create, edit and delete a customer
			customerListFrame.createButton.addActionListener(this);
			customerListFrame.editButton.addActionListener(this);
			customerListFrame.deleteButton.addActionListener(this);
			customerListFrame.viewButton.addActionListener(this);
			
			// We setup the filter used by the search field
			customerListFrame.filterTextField.getDocument().addDocumentListener(
	                new DocumentListener() {
	                    public void changedUpdate(DocumentEvent e) {
	                        newFilter();
	                    }
	                    public void insertUpdate(DocumentEvent e) {
	                        newFilter();
	                    }
	                    public void removeUpdate(DocumentEvent e) {
	                        newFilter();
	                    }
	                }); 
			
		}
	}

	/**
	 * Create the details frame
	 */
	private void createDetailsPage() {
		if (customerDetailsController == null) {
			customerDetailsController = new CustomerDetailsController();
			// We listen to the back button so that we know when to go back to the customer list
			customerDetailsController.mainFrame.backButton.addActionListener(this);
		}
	}
	
	/**
	 * Method used to parse the customer table
	 */
	private void newFilter() {
        RowFilter<TableModel, Object> rf = null;
        
        try {
        	// Regex used to filter the firstname, lastname and phone number of the table
        	// This regex is case insensitive
        	String filterText = customerListFrame.filterTextField.getText();
            rf = RowFilter.regexFilter("(?i)"+filterText, 0,1,2);
        } catch (java.util.regex.PatternSyntaxException e) {
            //If current expression can't be parsed, don't update.
            return;
        }
        sorter.setRowFilter(rf);
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
			this.showCustomerDetails();
		} else if (customerForm != null && e.getSource() == customerForm.okButton) {
			this.saveCustomer();
		} else if (customerDetailsController != null && e.getSource() == customerDetailsController.mainFrame.backButton) {
			this.goBackToCustomerList();
		}
	}

	/**
	 * Displays the customer creation form in a new window 
	 * Listen to the action triggered by the save button
	 */
	public void showCustomerCreationForm() {
		customerForm = new CustomerFormFrame();
		customerForm.okButton.addActionListener(this);

		customerForm.setVisible(true);
	}

	/**
	 * Displays the customer edition form in a new window
	 * It consists of the same frame as for creation but already filled-in
	 */
	public void showCustomerEditionForm() {
		customerForm = new CustomerFormFrame();
		customerForm.okButton.addActionListener(this);

		// We get the index of the selected row 
		// and retrieve the corresponding Customer entity
		int rowIndex = customerListFrame.table.getSelectedRow();
        rowIndex = customerListFrame.table.getRowSorter().convertRowIndexToModel(rowIndex);

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
	 * Triggered when the user wish to save a customer 
	 * in either the creation or edition form
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

				// We add it to the database and to our table model
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
        rowIndex = customerListFrame.table.getRowSorter().convertRowIndexToModel(rowIndex);
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
			// Since a row is now selected, we enable the edit, view and delete button
			customerListFrame.editButton.setEnabled(true);
			customerListFrame.viewButton.setEnabled(true);
			customerListFrame.deleteButton.setEnabled(true);
		}
	}
	
	/**
	 * Fills the appropriate data into the details page and displays it
	 */
	public void showCustomerDetails() {
		int rowIndex = customerListFrame.table.getSelectedRow();
        rowIndex = customerListFrame.table.getRowSorter().convertRowIndexToModel(rowIndex);

		Customer customer = tableModel.data.get(rowIndex);
		
		customerDetailsController.setCustomer(customer);
		
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
