package com.brookes.garage.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.brookes.garage.dao.BrandDao;
import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Model;

public class BrandTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	// Related Brand entity DAO
	private BrandDao brandDao;
	// ArrayList of Brand to populate the table
	public List<Brand> data = new ArrayList<Brand>();
	// The columns titles
	private final String[] titles = { "Brand Name" };

	/**
	 * The constructor method
	 */
	public BrandTableModel() {
		super();
		data = null;
		brandDao = DaoFactory.getBrandDao();
		data = brandDao.getAllBrands();
		/*for(int i = 0, l = data.size();i<l;i++){
			Brand br = data.get(i);
			System.out.println("__"+br.getName()+" deleted: "+br.isDelete_flag() );
			for(int j = 0, ll = br.getModels().size();j<ll;j++){
				Model mod = br.getModels().get(j);
				System.out.println("_______"+mod.getName()+" deleted: "+mod.isDelete_flag());
			}
		}*/
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
