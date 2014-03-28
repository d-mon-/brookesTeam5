package com.brookes.garage.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.CustomersCar;

public class CustomerCarTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	// ArrayList of Car to populate the table
	public List<CustomersCar> data = new ArrayList<CustomersCar>();
	// The columns titles
	private final String[] titles = { "Brand", "Model", "Plate Number" };

	/**
	 * The constructor method
	 */
	public CustomerCarTableModel() {
		super();
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
			return data.get(rowIndex).getModel().getBrand().getName();
		case 1:
			return data.get(rowIndex).getModel().getName();
		case 2:
			return data.get(rowIndex).getNumber_plate();
		default:
			return null; // Will never occur
		}
	}

	/**
	 * Add a Car to the list and add the appropriate row
	 */
	public void addCar(CustomersCar car) {
		data.add(car);
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}

	/**
	 * Refresh the table with the customer's data
	 */
	public void refreshTable(Customer customer) {
		data = customer.getCars();
		fireTableDataChanged();
	}
}
