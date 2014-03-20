package com.brookes.garage.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.brookes.garage.dao.CustomerDao;
import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.entity.Customer;

public class CustomerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	// Related Customer entity DAO
	private CustomerDao customerDao;
	// ArrayList of Customer to populate the table
	public List<Customer> data = new ArrayList<Customer>();
	// The columns titles
	private final String[] titles = { "Firstname", "Lastname", "Phone Number" };

	/**
	 * The constructor method
	 */
	public CustomerTableModel() {
		super();

		customerDao = DaoFactory.getCustomerDao();
		data = customerDao.getAllCustomers();
	}

	/**
	 * Return the number of rows in the table
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}

	/**
	 * Return the number of columns in the table
	 */
	@Override
	public int getColumnCount() {
		return titles.length;
	}

	/**
	 * Return the name of the column at the specified index
	 */
	@Override
	public String getColumnName(int columnIndex) {
		return titles[columnIndex];
	}

	/**
	 * Return the value to display for the specified row and column index
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return data.get(rowIndex).getFirstname();
		case 1:
			return data.get(rowIndex).getLastname();
		case 2:
			return data.get(rowIndex).getPhone_number();
		default:
			return null; // Will never occur
		}
	}

	/**
	 * Add a Customer to the list and add the appropriate row
	 */
	public void addCustomer(Customer customer) {
		data.add(customer);
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}

	/**
	 * Remove a Customer from the list and remove the appropriate row
	 */
	public void removeCustomer(int rowIndex) {
		data.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	/**
	 * Update a Customer in the list and update the appropriate row
	 */
	public void updateCustomer(Customer customer) {
		int firstRow = data.indexOf(customer);
		fireTableRowsUpdated(firstRow, firstRow);
	}
}
