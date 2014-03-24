package com.brookes.garage.tablemodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.brookes.garage.entity.Estimate;
import com.brookes.garage.entity.Part;
import com.brookes.garage.entity.Repair;

public class EstimateTableModel extends AbstractTableModel {


	private static final long serialVersionUID = 1L;


	// ArrayList of Part to populate the table
	public List<Estimate> data = new ArrayList<Estimate>();
	// The columns titles
	private final String[] titles = { "Reference", "Created On", "Status", "Price (£)" };
	
	/**
	 * The constructor method
	 */
	public EstimateTableModel() {
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
			return data.get(rowIndex).getIdentifier();
		case 1:{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = data.get(rowIndex).getCreation_date();
			return dateFormat.format(date);
		}
		case 2:
			return data.get(rowIndex).isInvalidated()?"Invalidated":"Pending";
		case 3: {
			System.out.println(data.get(rowIndex).getParts().size());

			double totalPrice = 0;
			for (Part part : data.get(rowIndex).getParts())
				totalPrice += part.getPrice();
			return totalPrice;
		}
		default:
			return null; // Will never occur
		}
	}

	/**
	 * Add an Estimate to the list and add the appropriate row
	 */
	public void addEstimate(Estimate estimate) {
		data.add(0, estimate);
		fireTableRowsInserted(0, 0);
	}	

	/**
	 * Remove an Estimate from the list and remove the appropriate row
	 */
	public void removeEstimate(int rowIndex) {
		data.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	/**
	 * Update an Estimate in the list and update the appropriate row
	 */
	public void updateEstimate(Estimate estimate) {
		int firstRow = data.indexOf(estimate);
		fireTableRowsUpdated(firstRow, firstRow);
	}

	public void refreshContent(Repair repair) {
		data = repair.getEstimates();
		fireTableDataChanged();
	}
}
