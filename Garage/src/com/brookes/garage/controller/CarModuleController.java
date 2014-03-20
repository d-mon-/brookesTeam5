package com.brookes.garage.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.derby.tools.sysinfo;

import com.brookes.garage.dao.BrandDao;
import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.entity.Brand;
import com.brookes.garage.frame.BrandFormFrame;
import com.brookes.garage.frame.CarListFrame;
import com.brookes.garage.tablemodel.BrandTableModel;
import com.brookes.garage.tablemodel.CarModelTableModel;

public class CarModuleController implements ActionListener,ListSelectionListener {
	
	 final static String LISTFRAME = "list";
	    final static String DETAILSFRAME = "details";
	    
		public JPanel mainPanel;

		private CarListFrame carListFrame;
		private BrandFormFrame brandForm;

		private BrandDao brandDao = DaoFactory.getBrandDao();
		private BrandTableModel brandTableModel;
		
		private CarModelTableModel carModelTableModel;

		public CarModuleController() {
			super();

			this.createListPage();
	        
			mainPanel = new JPanel(new CardLayout());
			mainPanel.setOpaque(false);
			mainPanel.add(carListFrame.contentPane, LISTFRAME);		
		}

		/**
		 * Create and configure the list frame
		 */
		private void createListPage() {
			if (carListFrame == null) {
				carListFrame = new CarListFrame();
				
				// Set the table model and add itself as selection listener for the
				// table
				brandTableModel = new BrandTableModel();
				carListFrame.brandTable.setModel(brandTableModel);
				//carListFrame.brandTable.getSelectionModel().addListSelectionListener(this);

				// Create and configure a table row sorter
				// so that the user is able to sort the table according to columns
				TableRowSorter<TableModel> brandSorter = new TableRowSorter<TableModel>(carListFrame.brandTable.getModel());
				carListFrame.brandTable.setRowSorter(brandSorter);
				brandSorter.setSortsOnUpdates(true);
				
				
				// Set the table model and add itself as selection listener for the
				// table
				carModelTableModel = new CarModelTableModel();
				carListFrame.modelTable.setModel(carModelTableModel);
				carListFrame.modelTable.getSelectionModel().addListSelectionListener(this);

				// Create and configure a table row sorter
				// so that the user is able to sort the table according to columns
				TableRowSorter<TableModel> modelSorter = new TableRowSorter<TableModel>(carListFrame.modelTable.getModel());
				carListFrame.modelTable.setRowSorter(modelSorter);
				modelSorter.setSortsOnUpdates(true);
				
				
				// Add itself as action listener to create, edit and delete a brand
				carListFrame.brandCreateButton.addActionListener(this);
				carListFrame.brandEditButton.addActionListener(this);
				carListFrame.brandDeleteButton.addActionListener(this);
				
				// Add itself as action listener to create, edit and delete a model
				carListFrame.modelCreateButton.addActionListener(this);
				carListFrame.modelEditButton.addActionListener(this);
				carListFrame.modelDeleteButton.addActionListener(this);
				
			}
		}

		/**
		 * An action should be performed based on the button triggering it
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == carListFrame.brandCreateButton) {
				this.showBrandCreationForm();
			} else if (e.getSource() == carListFrame.brandEditButton) {
				this.showBrandEditionForm();
			} else if (e.getSource() == carListFrame.brandDeleteButton) {
				this.deleteBrand();
			} else if (brandForm != null && e.getSource() == brandForm.saveButton) {
				this.saveBrand();
			} else if (e.getSource() == carListFrame.modelCreateButton) {
				//this.showBrandCreationForm();
			} else if (e.getSource() == carListFrame.modelEditButton) {
				//this.showBrandEditionForm();
				System.out.println("Hello");
			} else if (e.getSource() == carListFrame.modelDeleteButton) {
				//this.deleteBrand();
				System.out.println("Test");
				System.out.println("Test2");
			} 
			//else if (brandForm != null && e.getSource() == brandForm.saveButton) {
			//	this.saveBrand();
			//}	
		}
		
		/**
		 * Display the customer creation form in a new window Listen to the action
		 * triggered by the save button
		 */
		public void showBrandCreationForm() {
			brandForm = new BrandFormFrame();
			brandForm.saveButton.addActionListener(this);

			brandForm.setVisible(true);
		}
		
		/**
		 * Displays the brand edition form in a new window It consists of the
		 * same frame as for creation but already filled-in
		 */
		public void showBrandEditionForm() {
			brandForm = new BrandFormFrame();
			brandForm.saveButton.addActionListener(this);

			// We get the index of the selected row and retrieve the corresponding
			// Customer entity
			int rowIndex = carListFrame.brandTable.getSelectedRow();
			Brand brand = brandTableModel.data.get(rowIndex);
			brandForm.setBrand(brand);

			// We fill-in the form
			brandForm.nameField.setText(brand.getName());

			brandForm.setVisible(true);
		}
		
		/**
		 * Triggered when the user wish to save a customer in either the creation or
		 * edition form
		 */
		public void saveBrand() {
			String name = brandForm.nameField.getText();

			if (name.length() > 0) {
				// The Name field must contain a value

				if (brandForm.getBrand() != null) {
					// The form contains an existing brand
					// This was an edition form
					// We update the values in the existing brand and update it
					Brand brand = brandForm.getBrand();
					brand.setName(name);

					// We update the database and our table model
					brandDao.updateBrand(brand);
					brandTableModel.updateBrand(brand);
				} else {
					// The form was a creation form
					// We create a new brand with the values and save it
					Brand brand= new Brand();
					brand.setName(name);

					// We add it to the database and our table model
					brandDao.addBrand(brand);
					brandTableModel.addBrand(brand);
				}

				// We close the windows
				brandForm.dispose();
			} else {
				// Not every fields contain a value so we display a message
				brandForm.noEmptyLabel.setVisible(true);
			}
		}
		
		/**
		 * Triggered when the user have selected a row and clicked the delete button for Brand table
		 */
		public void deleteBrand() {
			// We get the currently selected row index, get the brand
			// and remove it from the table model and the database
			int rowIndex = carListFrame.brandTable.getSelectedRow();
			Brand brand = brandTableModel.data.get(rowIndex);
			brandTableModel.removeBrand(rowIndex);
			brandDao.removeBrand(brand);
		}
		
		/**
		 * The row selected by the user has changed
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource() == carListFrame.brandTable.getSelectionModel()) {
				// Since a row is now selected, we enable the edit and delete
				// buttons for Brand
				carListFrame.brandEditButton.setEnabled(true);
				carListFrame.brandDeleteButton.setEnabled(true);
			}
			else if (e.getSource() == carListFrame.modelTable.getSelectionModel()) {
				// Since a row is now selected, we enable the edit and delete
				// buttons for Model
				carListFrame.modelEditButton.setEnabled(true);
				carListFrame.modelDeleteButton.setEnabled(true);
			}
		}
}
