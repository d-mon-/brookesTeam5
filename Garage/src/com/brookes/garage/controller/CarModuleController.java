package com.brookes.garage.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.brookes.garage.dao.BrandDao;
import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.dao.ModelDao;
import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Model;
import com.brookes.garage.frame.BrandFormFrame;
import com.brookes.garage.frame.CarListFrame;
import com.brookes.garage.frame.ModelFormFrame;
import com.brookes.garage.tablemodel.BrandTableModel;
import com.brookes.garage.tablemodel.CarModelTableModel;

public class CarModuleController implements ActionListener,ListSelectionListener {
	
	 final static String LISTFRAME = "list";
	    final static String DETAILSFRAME = "details";
	    
		public JPanel mainPanel;

		private CarListFrame carListFrame;
		private BrandFormFrame brandForm;
		private ModelFormFrame modelForm;

		private BrandDao brandDao = DaoFactory.getBrandDao();
		private BrandTableModel brandTableModel;
		
		private ModelDao modelDao = DaoFactory.getModelDao();
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
				this.showModelCreationForm();
			} else if (e.getSource() == carListFrame.modelEditButton) {
				this.showModelEditionForm();
			} else if (e.getSource() == carListFrame.modelDeleteButton) {
				this.deleteModel();
			} else if (modelForm != null && e.getSource() == modelForm.saveButton) {
				this.saveModel();
			}	
		}
		
		/**
		 * Display the brand creation form in a new window Listen to the action
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
			// brand entity
			int rowIndex = carListFrame.brandTable.getSelectedRow();
			Brand brand = brandTableModel.data.get(rowIndex);
			brandForm.setBrand(brand);

			// We fill-in the form
			brandForm.nameField.setText(brand.getName());

			brandForm.setVisible(true);
		}
		
		/**
		 * Triggered when the user wishes to save a brand in either the creation or
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
		 * Display the model creation form in a new window Listen to the action
		 * triggered by the save button
		 */
		public void showModelCreationForm() {
			modelForm = new ModelFormFrame();
			modelForm.saveButton.addActionListener(this);

			modelForm.setVisible(true);
		}		
		
		/**
		 * Displays the model edition form in a new window It consists of the
		 * same frame as for creation but already filled-in
		 */
		public void showModelEditionForm() {
			modelForm = new ModelFormFrame();
			modelForm.saveButton.addActionListener(this);

			// We get the index of the selected row and retrieve the corresponding
			// model entity
			int rowIndex = carListFrame.modelTable.getSelectedRow();
			Model model = carModelTableModel.data.get(rowIndex);
			modelForm.setModel(model);
			
			// We fill-in the form
			modelForm.nameField.setText(model.getName());

			modelForm.setVisible(true);
		}
		
		/**
		 * Triggered when the user wishes to save a model in either the creation or
		 * edition form
		 */
		public void saveModel() {
			String name = modelForm.nameField.getText();

			if (name.length() > 0) {
				// The Name field must contain a value

				if (modelForm.getModel() != null) {
					// The form contains an existing model
					// This was an edition form
					// We update the values in the existing model and update it
					Model model = modelForm.getModel();
					model.setName(name);

					// We update the database and our table model
					modelDao.updateModel(model);
					carModelTableModel.updateModel(model);
				} else {
					// The form was a creation form
					// We create a new model with the values and save it
					Model model= new Model();
					model.setName(name);

					// We add it to the database and our table model
					modelDao.addModel(model);
					carModelTableModel.addModel(model);
				}

				// We close the windows
				modelForm.dispose();
			} else {
				// Not every fields contain a value so we display a message
				modelForm.noEmptyLabel.setVisible(true);
			}
		}

		/**
		 * Triggered when the user have selected a row and clicked the delete button for Brand table
		 */
		public void deleteModel() {
			// We get the currently selected row index, get the model
			// and remove it from the table model and the database
			int rowIndex = carListFrame.modelTable.getSelectedRow();
			Model model = carModelTableModel.data.get(rowIndex);
			carModelTableModel.removeModel(rowIndex);
			modelDao.removeModel(model);
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
