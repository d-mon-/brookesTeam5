package com.brookes.garage.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.dao.PartDao;
import com.brookes.garage.entity.Part;

public class PartTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	// Related Part entity DAO
	private PartDao partDao;
	// ArrayList of Part to populate the table
	public List<Part> data = new ArrayList<Part>();
	// The columns titles
	private final String[] titles = { "Reference", "Name", "Price" };
	
	/**
	 * The constructor method
	 */
	public PartTableModel() {
		super();

		partDao = DaoFactory.getPartDao();
		data = partDao.getAllParts();
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
			return data.get(rowIndex).getReference();
		case 1:
			return data.get(rowIndex).getName();
		case 2:
			return data.get(rowIndex).getPrice();
		default:
			return null; // Will never occur
		}
	}

	/**
	 * Add a Part to the list and add the appropriate row
	 */
	public void addPart(Part part) {
		data.add(part);
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}

	/**
	 * Remove a Part from the list and remove the appropriate row
	 */
	public void removePart(int rowIndex) {
		data.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	/**
	 * Update a Part in the list and update the appropriate row
	 */
	public void updatePart(Part part) {
		int firstRow = data.indexOf(part);
		fireTableRowsUpdated(firstRow, firstRow);
	}

}
