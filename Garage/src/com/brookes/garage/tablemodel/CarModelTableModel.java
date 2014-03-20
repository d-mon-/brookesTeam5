package com.brookes.garage.tablemodel;

import javax.swing.table.AbstractTableModel;

public class CarModelTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final Object[][] donnees;
	 
    private final String[] entetes = {"Model Name"};
 
    public CarModelTableModel() {
        super();
 
        donnees = new Object[][]{
                {"Nicolas"},
                {"Damien"},
                {"Corinne"},
                {"Emilie"},
                {"Delphine"},
                {"Eric"},
        };
    }
 
    public int getRowCount() {
        return donnees.length;
    }
 
    public int getColumnCount() {
        return entetes.length;
    }
 
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
        return donnees[rowIndex][columnIndex];
    }


}
