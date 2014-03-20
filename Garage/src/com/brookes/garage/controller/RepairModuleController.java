package com.brookes.garage.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.brookes.garage.frame.RepairListFrame;
import com.brookes.garage.tablemodel.RepairTableModel;

public class RepairModuleController implements ActionListener, ListSelectionListener {

	final static String LISTFRAME = "list";
    final static String DETAILSFRAME = "details";
    
	public JPanel mainPanel;

	private RepairListFrame repairListFrame;
	//private CustomerDetailsFrame customerDetailsFrame;
	//private CustomerFormFrame customerForm;

	//private RepairDao repairDao = DaoFactory.getRepairDao();
	private RepairTableModel tableModel;

	public RepairModuleController() {
		super();

		this.createListPage();
		//this.createDetailsPage();
        
		mainPanel = new JPanel(new CardLayout());
		mainPanel.setOpaque(false);
		mainPanel.add(repairListFrame.contentPane, LISTFRAME);
		//mainPanel.add(customerDetailsFrame.contentPane, DETAILSFRAME);
	}
	
	/**
	 * Create and configure the list frame
	 */
	private void createListPage() {
		if (repairListFrame == null) {
			repairListFrame = new RepairListFrame();
			
			// Set the table model and add itself as selection listener for the
			// table
			tableModel = new RepairTableModel();
			repairListFrame.table.setModel(tableModel);
			repairListFrame.table.getSelectionModel().addListSelectionListener(this);

			// Create and configure a table row sorter
			// so that the user is able to sort the table according to columns
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
					repairListFrame.table.getModel());
			repairListFrame.table.setRowSorter(sorter);
			sorter.setSortable(2, false);
			sorter.setSortsOnUpdates(true);

			// Add itself as action listener to create, edit and delete a customer
			repairListFrame.createButton.addActionListener(this);
			repairListFrame.viewButton.addActionListener(this);
			
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
