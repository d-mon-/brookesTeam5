package com.brookes.garage.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.brookes.garage.dao.BrandDao;
import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.dao.ModelDao;
import com.brookes.garage.dao.PartDao;
import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Model;
import com.brookes.garage.entity.Part;
import com.brookes.garage.frame.BrandFormFrame;
import com.brookes.garage.frame.CarListFrame;
import com.brookes.garage.frame.ModelFormFrame;
import com.brookes.garage.frame.PartFormFrame;
import com.brookes.garage.tablemodel.BrandTableModel;
import com.brookes.garage.tablemodel.CarModelTableModel;
import com.brookes.garage.tablemodel.PartTableModel;

public class CarModuleController implements ActionListener,ListSelectionListener {

	// Identifiers used to switch between the list and the details
	final static String LISTFRAME = "list";
	final static String DETAILSFRAME = "details";

	public JPanel mainPanel;

	// Keep track of the selected brand and model in the tables
	private Brand selectedBrand;
	private Model selectedModel;

	// The different views managed by the controller
	private CarListFrame carListFrame;
	private BrandFormFrame brandForm;
	private ModelFormFrame modelForm;
	private PartFormFrame partForm;

	// Every objects related to model
	private BrandDao brandDao = DaoFactory.getBrandDao();
	private BrandTableModel brandTableModel;
	private ModelDao modelDao = DaoFactory.getModelDao();
	private CarModelTableModel carModelTableModel;
	private PartDao partDao = DaoFactory.getPartDao();
	private PartTableModel partTableModel;


	/**
	 * The constructor method
	 */
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

			// Set the table model and add itself as selection listener for the table
			brandTableModel = new BrandTableModel();
			carListFrame.brandTable.setModel(brandTableModel);
			carListFrame.brandTable.getSelectionModel().addListSelectionListener(this);

			// Create and configure a table row sorter
			// so that the user is able to sort the table according to columns
			TableRowSorter<TableModel> brandSorter = new TableRowSorter<TableModel>(carListFrame.brandTable.getModel());
			carListFrame.brandTable.setRowSorter(brandSorter);
			brandSorter.setSortsOnUpdates(true);

			
			// Set the table model and add itself as selection listener for the table
			carModelTableModel = new CarModelTableModel();
			carListFrame.modelTable.setModel(carModelTableModel);
			carListFrame.modelTable.getSelectionModel().addListSelectionListener(this);

			// Create and configure a table row sorter
			// so that the user is able to sort the table according to columns
			TableRowSorter<TableModel> modelSorter = new TableRowSorter<TableModel>(carListFrame.modelTable.getModel());
			carListFrame.modelTable.setRowSorter(modelSorter);
			modelSorter.setSortsOnUpdates(true);


			// Set the table model and add itself as selection listener for the table
			partTableModel = new PartTableModel();
			carListFrame.partTable.setModel(partTableModel);
			carListFrame.partTable.getSelectionModel().addListSelectionListener(this);

			// Create and configure a table row sorter
			// so that the user is able to sort the table according to columns
			TableRowSorter<TableModel> partSorter = new TableRowSorter<TableModel>(carListFrame.partTable.getModel());
			carListFrame.partTable.setRowSorter(partSorter);
			partSorter.setSortsOnUpdates(true);


			// Add itself as action listener to create, edit and delete a brand
			carListFrame.brandCreateButton.addActionListener(this);
			carListFrame.brandEditButton.addActionListener(this);
			carListFrame.brandDeleteButton.addActionListener(this);

			// Add itself as action listener to create, edit and delete a model
			carListFrame.modelCreateButton.addActionListener(this);
			carListFrame.modelEditButton.addActionListener(this);
			carListFrame.modelDeleteButton.addActionListener(this);

			// Add itself as action listener to create, edit and delete a part
			carListFrame.partCreateButton.addActionListener(this);
			carListFrame.partEditButton.addActionListener(this);
			carListFrame.partDeleteButton.addActionListener(this);

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
		} else if (brandForm != null && e.getSource() == brandForm.okButton) {
			this.saveBrand();
		} else if (e.getSource() == carListFrame.modelCreateButton) {
			this.showModelCreationForm();
		} else if (e.getSource() == carListFrame.modelEditButton) {
			this.showModelEditionForm();
		} else if (e.getSource() == carListFrame.modelDeleteButton) {
			this.deleteModel();
		} else if (modelForm != null && e.getSource() == modelForm.okButton) {
			this.saveModel();
		} else if (e.getSource() == carListFrame.partCreateButton) {
			this.showPartCreationForm();
		} else if (e.getSource() == carListFrame.partEditButton) {
			this.showPartEditionForm();
		} else if (e.getSource() == carListFrame.partDeleteButton) {
			this.deletePart();
		} else if (partForm != null && e.getSource() == partForm.okButton) {
			this.savePart();
		}	
	}

	/**
	 * Displays the brand creation form in a new window
	 * Listen to the action triggered by the save button
	 */
	public void showBrandCreationForm() {
		brandForm = new BrandFormFrame();
		brandForm.okButton.addActionListener(this);

		brandForm.setVisible(true);
	}

	/**
	 * Displays the brand edition form in a new window
	 * It consists of the same frame as for creation but already filled-in
	 */
	public void showBrandEditionForm() {
		brandForm = new BrandFormFrame();
		brandForm.okButton.addActionListener(this);

		// We get the index of the selected row
		// and retrieve the corresponding brand entity
		int rowIndex = carListFrame.brandTable.getSelectedRow();
		rowIndex = carListFrame.brandTable.getRowSorter().convertRowIndexToModel(rowIndex);
		Brand brand = brandTableModel.data.get(rowIndex);
		brandForm.setBrand(brand);

		// We fill-in the form
		brandForm.nameField.setText(brand.getName());

		brandForm.setVisible(true);
	}

				
	/**
	 * Triggered when the user wishes to save a brand 
	 * in either the creation or edition form
	 */
	public void saveBrand() {
		String name = brandForm.nameField.getText();

		if(name.length()>255){
			JOptionPane.showMessageDialog(null, "Name length is too long: "+name.length()+"/255");
			return;
		}
		
		if (name.length() > 0) {
			// The Name field must contain a value

			if (brandForm.getBrand() != null) {
				// The form contains an existing brand
				// This was an edition form
				// We update the values in the existing brand
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

			// We close the window
			brandForm.dispose();
		} else {
			// Not every fields contain a value so we display a message
			brandForm.noEmptyLabel.setVisible(true);
		}
	}

	/**
	 * Triggered when the user has selected a row and clicked the delete button for Brand table
	 */
	public void deleteBrand() {
		// We get the currently selected row index, get the brand
		// and remove it from the table model and the database
		int rowIndex = carListFrame.brandTable.getSelectedRow();
		rowIndex = carListFrame.brandTable.getRowSorter().convertRowIndexToModel(rowIndex);

		Brand brand = brandTableModel.data.get(rowIndex);
		brandTableModel.removeBrand(rowIndex);
		brandDao.removeBrand(brand);
		toggleButton(false,false,false);
	}

	/**
	 * Display the model creation form in a new window
	 * Listen to the action triggered by the save button
	 */
	public void showModelCreationForm() {
		modelForm = new ModelFormFrame();
		modelForm.okButton.addActionListener(this);

		modelForm.setVisible(true);
	}		

	/**
	 * Displays the model edition form in a new window
	 * It consists of the same frame as for creation but already filled-in
	 */
	public void showModelEditionForm() {
		modelForm = new ModelFormFrame();
		modelForm.okButton.addActionListener(this);

		// We get the index of the selected row and retrieve the corresponding model entity
		int rowIndex = carListFrame.modelTable.getSelectedRow();
		rowIndex = carListFrame.modelTable.getRowSorter().convertRowIndexToModel(rowIndex);

		Model model = carModelTableModel.data.get(rowIndex);
		modelForm.setModel(model);

		// We fill-in the form
		modelForm.nameField.setText(model.getName());

		modelForm.setVisible(true);
	}

	/**
	 * Triggered when the user wishes to save a model
	 * in either the creation or edition form
	 */
	public void saveModel() {
		String name = modelForm.nameField.getText();

		if(name.length()>255){
			JOptionPane.showMessageDialog(null, "Name length is too long: "+name.length()+"/255");
			return;
		}
		
		if (name.length() > 0) {
			// The Name field must contain a value

			if (modelForm.getModel() != null) {
				// The form contains an existing model
				// This was an edition form
				// We update the values in the existing model
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
				model.setBrand(selectedBrand);

				// We add it to the database and to our table model
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
	 * Triggered when the user has selected a row and clicked the delete button for Model table
	 */
	public void deleteModel() {
		// We get the currently selected row index, get the model
		// and remove it from the table model and the database
		int rowIndex = carListFrame.modelTable.getSelectedRow();
		rowIndex = carListFrame.modelTable.getRowSorter().convertRowIndexToModel(rowIndex);

		Model model = carModelTableModel.data.get(rowIndex);
		carModelTableModel.removeModel(rowIndex);
		modelDao.removeModel(model);

		toggleButton(true,false,false);
	}


	/**
	 * Displays the part creation form in a new window
	 * Listen to the action triggered by the save button
	 */
	public void showPartCreationForm() {
		partForm = new PartFormFrame();
		partForm.okButton.addActionListener(this);

		partForm.setVisible(true);
	}		

	/**
	 * Displays the part edition form in a new window 
	 * It consists of the same frame as for creation but already filled-in
	 */
	public void showPartEditionForm() {
		partForm = new PartFormFrame();
		partForm.okButton.addActionListener(this);

		// We get the index of the selected row and retrieve the corresponding part entity
		int rowIndex = carListFrame.partTable.getSelectedRow();
		rowIndex = carListFrame.partTable.getRowSorter().convertRowIndexToModel(rowIndex);

		Part part = partTableModel.data.get(rowIndex);
		partForm.setPart(part);

		// We fill-in the form
		partForm.nameField.setText(part.getName());
		partForm.refField.setText(part.getReference());
		partForm.priceField.setText(part.getPrice().toString());

		partForm.setVisible(true);
	}

	/**
	 * Triggered when the user wishes to save a part 
	 * in either the creation or edition form
	 */
	public void savePart() {
		String name = partForm.nameField.getText();
		String ref = partForm.refField.getText();
		String price = partForm.priceField.getText();		
				
				if(name.length()>255){
					JOptionPane.showMessageDialog(null, "Name length is too long: "+name.length()+"/255");
					return;
				}
				if(ref.length()>255){
					JOptionPane.showMessageDialog(null, "Reference length is too long: "+ref.length()+"/255");
					return;
				}
				

		if (name.length() > 0 && ref.length() > 0 && price.length() > 0) {
			// The Reference, name and price fields must contain a value

			// Test that the price value entered is a suitable int/double
			try {
				Double.parseDouble(price);
			} catch (NumberFormatException nfe) {
				partForm.noEmptyLabel.setText("Price must be a number.");
				partForm.noEmptyLabel.setVisible(true);
				return;
			}

			if (partForm.getPart() != null) {
				// The form contains an existing part
				// This was an edition form
				// We update the values in the existing part
				Part part = partForm.getPart();
				part.setName(name);
				part.setReference(ref);
				part.setPrice(Double.parseDouble(price));

				// We update the database and our table model
				partDao.updatePart(part);
				partTableModel.updatePart(part);
			} else {
				// The form was a creation form
				// We create a new part with the values and save it
				Part part = new Part();
				part.setName(name);
				part.setReference(ref);
				part.setPrice(Double.parseDouble(price));
				part.setModel(selectedModel);

				// We add it to the database and our table model
				partDao.addPart(part);
				partTableModel.addPart(part);
			}

			// We close the windows
			partForm.dispose();
		} else {
			// Not every fields contain a value so we display a message
			partForm.noEmptyLabel.setText("All fields are mandatory.");
			partForm.noEmptyLabel.setVisible(true);
		}
	}

	/**
	 * Triggered when the user has selected a row and clicked the delete button for Brand table
	 */
	public void deletePart() {
		// We get the currently selected row index, get the part
		// and remove it from the table model and the database
		int rowIndex = carListFrame.partTable.getSelectedRow();
		rowIndex = carListFrame.partTable.getRowSorter().convertRowIndexToModel(rowIndex);

		Part part = partTableModel.data.get(rowIndex);
		partTableModel.removePart(rowIndex);
		partDao.removePart(part);
		toggleButton(true,true,false);
	}


	/**
	 * The row selected by the user has changed
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == carListFrame.brandTable.getSelectionModel()) {
			// Since a row is now selected in the brand table
			// we enable the edit and delete buttons for Brand
			int rowIndex = carListFrame.brandTable.getSelectedRow();
			if (rowIndex == -1) return;
			rowIndex = carListFrame.brandTable.getRowSorter().convertRowIndexToModel(rowIndex);

			if(rowIndex >= 0) {
				selectedBrand = brandTableModel.data.get(rowIndex);
				carModelTableModel.updateModelContent(selectedBrand);
				partTableModel.clearPartContent();
				toggleButton(true,false,false);
			}
			else {
				toggleButton(false,false,false);
			}
		}
		else if (e.getSource() == carListFrame.modelTable.getSelectionModel()) {
			// Since a row is now selected in the model table
			// we enable the edit and delete buttons for Model
			int rowIndex = carListFrame.modelTable.getSelectedRow();
			if (rowIndex == -1) return;
			rowIndex = carListFrame.modelTable.getRowSorter().convertRowIndexToModel(rowIndex);

			if(rowIndex >= 0) {
				selectedModel = carModelTableModel.data.get(rowIndex);
				partTableModel.updateContent(selectedModel);

				toggleButton(true,true,false);
			}
			else {
				toggleButton(true,false,false);
			}

		}
		else if (e.getSource() == carListFrame.partTable.getSelectionModel()) {
			int rowIndex = carListFrame.partTable.getSelectedRow();
			if (rowIndex == -1) return;
			toggleButton(true,true,true);
		}
	}

	/**
	 * Allows to manage the state of buttons for each table
	 */
	private void toggleButton(boolean brandTable,boolean modelTable, boolean partTable) {
		if(brandTable){	
			//selected item in brand			

			carListFrame.modelCreateButton.setEnabled(true);				
			carListFrame.brandEditButton.setEnabled(true);
			carListFrame.brandDeleteButton.setEnabled(true);

			if(modelTable){		
				togglePartButton(partTable);
				carListFrame.partCreateButton.setEnabled(true);						
				carListFrame.modelEditButton.setEnabled(true);
				carListFrame.modelDeleteButton.setEnabled(true);					
			}else{
				togglePartButton(false);
				carListFrame.partCreateButton.setEnabled(false);					
				carListFrame.modelEditButton.setEnabled(false);	

				carListFrame.modelDeleteButton.setEnabled(false);					

				partTableModel.clearPartContent();
			}
		}else{
			//no selected item in brand
			togglePartButton(false);	
			carListFrame.partCreateButton.setEnabled(false);	

			carListFrame.modelEditButton.setEnabled(false);
			carListFrame.modelDeleteButton.setEnabled(false);
			carListFrame.modelCreateButton.setEnabled(false);

			carListFrame.brandEditButton.setEnabled(false);
			carListFrame.brandDeleteButton.setEnabled(false);
			carModelTableModel.clearModelContent();
			partTableModel.clearPartContent();
		}		
	}

	/**
	 * Allows to manage the state of buttons for the part table
	 */
	private void togglePartButton(boolean partTable){
		if(partTable){
			carListFrame.partEditButton.setEnabled(true);
			carListFrame.partDeleteButton.setEnabled(true);
		}else{
			carListFrame.partEditButton.setEnabled(false);
			carListFrame.partDeleteButton.setEnabled(false);
		}			
	}
}
