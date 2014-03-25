package com.brookes.garage.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Model;
import com.brookes.garage.entity.Part;

public class CarModelTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	// ArrayList of Model to populate the table
	public List<Model> data = new ArrayList<Model>();
	// The columns titles
	private final String[] titles = { "Model Name" };

	/**
	 * The constructor method
	 */
	public CarModelTableModel() {
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
			return data.get(rowIndex).getName();
		default:
			return null; // Will never occur
		}
	}

	/**
	 * Add a Model to the list and add the appropriate row
	 */
	public void addModel(Model model) {
		data.add(model);
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}

	/**
	 * Remove a Model from the list and remove the appropriate row
	 */
	public void removeModel(int rowIndex) {
		data.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	/**
	 * Update a Model in the list and update the appropriate row
	 */
	public void updateModel(Model model) {
		int firstRow = data.indexOf(model);
		fireTableRowsUpdated(firstRow, firstRow);
	}

	public void updateModelContent(Brand brand) {
		data = brand.getModels();
		fireTableDataChanged();
	}
	public void clearModelContent() {
		data = new ArrayList<Model>();
		fireTableDataChanged();
	}
	
}
