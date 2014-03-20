package com.brookes.garage.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.dao.RepairDao;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.Customers_car;
import com.brookes.garage.entity.Repair;

public class RepairTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	// Related Repair entity DAO
	private RepairDao repairDao;
	// ArrayList of Customer to populate the table
	public List<Repair> data = new ArrayList<Repair>();
	// The columns titles
	private final String[] titles = { "Reference", "Client", "Car", "Status", "Creation Date" };

	/**
	 * The constructor method
	 */
	public RepairTableModel() {
		super();

		repairDao = DaoFactory.getRepairDao();
		data = repairDao.getAllRepairs();
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
			return data.get(rowIndex).getIdentifier();
		case 1: {
			Customer customer = data.get(rowIndex).getCar().getCustomer();
			return customer.toString();
		}
		case 2: {
			Customers_car car = data.get(rowIndex).getCar();
			return car.getModel().toString();
		}
		case 3:
			return data.get(rowIndex).getStatus();
		case 4:
			return data.get(rowIndex).getCreation_date().toString();
		default:
			return null; // Will never occur
		}
	}

	/**
	 * Add a Repair to the list and add the appropriate row
	 */
	public void addRepair(Repair repair) {
		data.add(repair);
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}

	/**
	 * Update a Repair in the list and update the appropriate row
	 */
	public void updateRepair(Repair repair) {
		int firstRow = data.indexOf(repair);
		fireTableRowsUpdated(firstRow, firstRow);
	}
}
