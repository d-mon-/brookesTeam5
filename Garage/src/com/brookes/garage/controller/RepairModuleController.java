package com.brookes.garage.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.dao.RepairDao;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.Customers_car;
import com.brookes.garage.entity.Repair;
import com.brookes.garage.frame.RepairFormFrame;
import com.brookes.garage.frame.RepairListFrame;
import com.brookes.garage.tablemodel.RepairTableModel;

public class RepairModuleController implements ActionListener, ListSelectionListener {

	final static String LISTFRAME = "list";
    final static String DETAILSFRAME = "details";
    
	public JPanel mainPanel;

	private RepairListFrame repairListFrame;
	//private CustomerDetailsFrame customerDetailsFrame;
	private RepairFormFrame repairForm;

	private RepairDao repairDao = DaoFactory.getRepairDao();
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
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == repairListFrame.createButton) {
			this.showRepairCreationForm();
		} else if (e.getSource() == repairListFrame.viewButton) {
			//this.showRepairDetails();
		} else if (e.getSource() == repairForm.saveButton) {
			this.saveRepair();
		} else if (e.getSource() == repairForm.customerComboBox) {
			Customer customer = (Customer) repairForm.customerComboBox.getSelectedItem();
			List<Customers_car> cars = customer.getCars();
			Customers_car[] array = cars.toArray(new Customers_car[cars.size()]);
			DefaultComboBoxModel<Customers_car> model = new DefaultComboBoxModel<Customers_car>(array);
			repairForm.carComboBox.setModel( model );
			repairForm.carComboBox.setEnabled(true);
		}
	}

	/**
	 * Display the repair creation form in a new window
	 * Listen to the action triggered by the save button
	 */
	private void showRepairCreationForm() {
		repairForm = new RepairFormFrame();
		repairForm.customerComboBox.addActionListener(this);
		repairForm.saveButton.addActionListener(this);

		
		repairForm.setVisible(true);
	}
	
	/**
	 * Triggered when the user wish to save a repair in the creation form
	 */
	public void saveRepair() {
		String reference = repairForm.identifierField.getText();
		Customers_car car = (Customers_car)repairForm.carComboBox.getSelectedItem();
		String description = repairForm.descriptionField.getText();

		if (reference.length() > 0 /*&& car != null*/ && description.length() > 0) {
			// Every fields contains a value

				// The form was a creation form
				// We create a new repair with the values and save it
				Repair repair = new Repair();
				repair.setIdentifier(reference);
				repair.setCar(car);
				repair.setDescription(description);

				// We add it to the database and our table model
				repairDao.addRepair(repair);
				tableModel.addRepair(repair);

			// We close the windows
			repairForm.dispose();
		} else {
			// Not every fields contain a value so we display a message
			repairForm.noEmptyLabel.setVisible(true);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {

	}
	
	
}
