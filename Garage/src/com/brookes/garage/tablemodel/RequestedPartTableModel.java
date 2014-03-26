package com.brookes.garage.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.brookes.garage.entity.Estimate;
import com.brookes.garage.entity.Part;
import com.brookes.garage.entity.RequestedPart;

public class RequestedPartTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;


	// ArrayList of Part to populate the table
	public List<RequestedPart> data = new ArrayList<RequestedPart>();
	// The columns titles
	private final String[] titles = { "Reference", "Name", "Price (£)" };

	/**
	 * The constructor method
	 */
	public RequestedPartTableModel() {
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
			return data.get(rowIndex).getPart().getReference();
		case 1:
			return data.get(rowIndex).getPart().getName();
		case 2:
			return data.get(rowIndex).getPart().getPrice();
		default:
			return null; // Will never occur
		}
	}

	/**
	 * Add a Part to the list and add the appropriate row
	 */
	public void addPart(RequestedPart part) {
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

	public void updateContent(Estimate estimate) {
		data = estimate.getParts();
		fireTableDataChanged();
	}

	public void clearPartContent() {
		data = new ArrayList<RequestedPart>();
		fireTableDataChanged();
	}

}
