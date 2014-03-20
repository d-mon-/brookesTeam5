package com.brookes.garage.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.brookes.garage.dao.BrandDao;
import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.entity.Brand;

public class BrandTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	// Related Customer entity DAO
	private BrandDao brandDao;
	// ArrayList of Customer to populate the table
	public List<Brand> data = new ArrayList<Brand>();
	// The columns titles
	private final String[] titles = { "Brand Name" };

	/**
	 * The constructor method
	 */
	public BrandTableModel() {
		super();

		brandDao = DaoFactory.getBrandDao();
		data = brandDao.getAllBrands();
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
	 * Add a Brand to the list and add the appropriate row
	 */
	public void addBrand(Brand brand) {
		data.add(brand);
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}

	/**
	 * Remove a Brand from the list and remove the appropriate row
	 */
	public void removeBrand(int rowIndex) {
		data.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	/**
	 * Update a Brand in the list and update the appropriate row
	 */
	public void updateBrand(Brand brand) {
		int firstRow = data.indexOf(brand);
		fireTableRowsUpdated(firstRow, firstRow);
	}

}
