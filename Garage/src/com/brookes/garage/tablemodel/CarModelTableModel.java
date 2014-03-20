package com.brookes.garage.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.dao.ModelDao;
import com.brookes.garage.entity.Model;

public class CarModelTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	// Related Model entity DAO
	private ModelDao modelDao;
	// ArrayList of Customer to populate the table
	public List<Model> data = new ArrayList<Model>();
	// The columns titles
	private final String[] titles = { "Model Name" };

	/**
	 * The constructor method
	 */
	public CarModelTableModel() {
		super();

		modelDao = DaoFactory.getModelDao();
		data = modelDao.getAllModels();
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

}
