package com.brookes.garage.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.dao.RepairDao;
import com.brookes.garage.dao.StatusDao;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.CustomersCar;
import com.brookes.garage.entity.Repair;
import com.brookes.garage.frame.RepairFormFrame;
import com.brookes.garage.frame.RepairListFrame;
import com.brookes.garage.tablemodel.RepairTableModel;

public class RepairModuleController implements ActionListener, ListSelectionListener {

	// Identifiers used to switch between the list and the details
	final static String LISTFRAME = "list";
	final static String DETAILSFRAME = "details";

	// Views displayed by the controller
	public JPanel mainPanel;
	private RepairListFrame repairListFrame;
	private RepairDetailsController repairDetailsController;
	private RepairFormFrame repairForm;

	// Objects related to the data model
	private RepairDao repairDao = DaoFactory.getRepairDao();
	private RepairTableModel tableModel;


	/**
	 * The constructor method
	 */
	public RepairModuleController() {
		super();

		this.createListPage();
		this.createDetailsPage();

		mainPanel = new JPanel(new CardLayout());
		mainPanel.setOpaque(false);
		mainPanel.add(repairListFrame.contentPane, LISTFRAME);
		mainPanel.add(repairDetailsController.mainFrame.contentPane, DETAILSFRAME);
	}

	/**
	 * Create and configure the list frame
	 */
	private void createListPage() {
		if (repairListFrame == null) {
			repairListFrame = new RepairListFrame();

			// Set the table model and add itself as selection listener for the table
			tableModel = new RepairTableModel();
			repairListFrame.table.setModel(tableModel);
			repairListFrame.table.getSelectionModel().addListSelectionListener(this);

			// Create and configure a table row sorter
			// so that the user is able to sort the table according to columns
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(repairListFrame.table.getModel());
			repairListFrame.table.setRowSorter(sorter);
			sorter.setSortsOnUpdates(true);

			// Add itself as action listener to create, edit and delete a repair
			repairListFrame.createButton.addActionListener(this);
			repairListFrame.viewButton.addActionListener(this);
			repairListFrame.editButton.addActionListener(this);
		}
	}

	/**
	 * Create the details frame
	 */
	private void createDetailsPage() {
		if (repairDetailsController == null) {
			repairDetailsController = new RepairDetailsController();
			repairDetailsController.mainFrame.backButton.addActionListener(this);
		}
	}

	/**
	 * An action should be performed based on the button triggering it
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == repairListFrame.createButton) {
			this.showRepairCreationForm();
		} else if (e.getSource() == repairListFrame.editButton) {
			this.showRepairEditionForm();
		} else if (e.getSource() == repairListFrame.viewButton) {
			this.showRepairDetails();
		} else if (e.getSource() == repairDetailsController.mainFrame.backButton) {
			this.goBackToRepairList();
		} else if (repairForm != null && e.getSource() == repairForm.saveButton) {
			this.saveRepair();
		} else if (repairForm != null && e.getSource() == repairForm.customerComboBox) {
			// A different customer is now selected in the combobox
			// We update the list of cars for this customer in the other combobox
			Customer customer = (Customer) repairForm.customerComboBox.getSelectedItem();
			List<CustomersCar> cars = DaoFactory.getCarDao().getCarsByCustomer(customer);
			CustomersCar[] array = cars.toArray(new CustomersCar[cars.size()]);
			DefaultComboBoxModel<CustomersCar> model = new DefaultComboBoxModel<CustomersCar>(array);
			repairForm.carComboBox.setModel(model);
			repairForm.carComboBox.setEnabled(true);
		}
	}

	/**
	 * Displays the repair creation form in a new window
	 * Listen to the action triggered by the save button
	 */
	private void showRepairCreationForm() {
		repairForm = new RepairFormFrame();
		repairForm.customerComboBox.addActionListener(this);
		repairForm.saveButton.addActionListener(this);

		repairForm.setVisible(true);
	}

	/**
	 * Displays the repair edition form in a new window 
	 * It consists of the same frame as for creation but already filled-in
	 */
	public void showRepairEditionForm() {
		repairForm = new RepairFormFrame();
		repairForm.customerComboBox.addActionListener(this);
		repairForm.saveButton.addActionListener(this);

		// We get the index of the selected row 
		// and retrieve the corresponding Repair entity
		int rowIndex = repairListFrame.table.getSelectedRow();
		rowIndex = repairListFrame.table.getRowSorter().convertRowIndexToModel(rowIndex);

		Repair repair = tableModel.data.get(rowIndex);
		repairForm.setRepair(repair);

		// We fill-in the form
		repairForm.customerComboBox.setSelectedItem(repair.getCar().getCustomer());
		System.out.println("items" + repairForm.customerComboBox.getModel().getSize());
		repairForm.carComboBox.setSelectedItem(repair.getCar());
		System.out.println("items" + repairForm.carComboBox.getModel().getSize());
		repairForm.descriptionField.setText(repair.getDescription());

		repairForm.setVisible(true);
	}

	/**
	 * Fills the appropriate data into the details page and displays it
	 */
	public void showRepairDetails() {
		int rowIndex = repairListFrame.table.getSelectedRow();
		rowIndex = repairListFrame.table.getRowSorter().convertRowIndexToModel(rowIndex);
		Repair repair = tableModel.data.get(rowIndex);
		repairDetailsController.setRepair(repair);

		CardLayout cl = (CardLayout)(mainPanel.getLayout());
		cl.show(mainPanel, DETAILSFRAME);
	}

	/**
	 * Go back to the repair list (from the details page)
	 */
	public void goBackToRepairList() {
		CardLayout cl = (CardLayout)(mainPanel.getLayout());
		cl.show(mainPanel, LISTFRAME);
	}

	/**
	 * Triggered when the user wish to save a repair in the creation form
	 */
	public void saveRepair() {
		CustomersCar car = (CustomersCar)repairForm.carComboBox.getSelectedItem();
		String description = repairForm.descriptionField.getText();

		if (car != null && description.length() > 0) {
			// Every fields contains a value
			
			if(description.length()>1000){
				JOptionPane.showMessageDialog(null, "Description length is too long: "+description.length()+"/255");
				return;
			}
			
			if (repairForm.getRepair() != null) {
				// The form contains an existing repair
				// This was an edition form
				// We update the values in the existing repair and update it
				Repair repair = repairForm.getRepair();
				repair.setCar(car);
				repair.setDescription(description);

				// We update the database and to our table model
				repairDao.updateRepair(repair);
				tableModel.updateRepair(repair);
			} else {
				// The form was a creation form
				// We create a new repair with the values and save it
				Repair repair = new Repair();
				repair.setCar(car);
				repair.setDescription(description);
				repair.setCreation_date(new Date());
				repair.setIdentifier("R"+repair.hashCode());

				StatusDao statusDao = DaoFactory.getStatusDao();
				repair.setStatus(statusDao.getFirstStatus());

				// We add it to the database and our table model
				repairDao.addRepair(repair);
				tableModel.addRepair(repair);
			}

			// We close the window
			repairForm.dispose();
		} else {
			// Not every fields contain a value so we display a message
			repairForm.noEmptyLabel.setVisible(true);
		}
	}

	/**
	 * The row selected by the user has changed
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == repairListFrame.table.getSelectionModel()) {
			// Since a row is now selected, we enable the view button
			repairListFrame.viewButton.setEnabled(true);
			repairListFrame.editButton.setEnabled(true);
		}
	}


}
