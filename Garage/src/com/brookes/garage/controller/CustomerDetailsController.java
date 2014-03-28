package com.brookes.garage.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

//github.com/d-mon-/brookesTeam5.git
import com.brookes.garage.dao.CarDao;
import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.CustomersCar;
import com.brookes.garage.entity.Model;
import com.brookes.garage.frame.CarFormFrame;
import com.brookes.garage.frame.CustomerDetailsFrame;
import com.brookes.garage.tablemodel.CustomerCarTableModel;
import com.brookes.garage.tablemodel.RepairTableModel;

public class CustomerDetailsController implements ActionListener {

	// The customer displayed by the view
	private Customer customer;

	// The views managed by the controller
	public CustomerDetailsFrame mainFrame;
	private CarFormFrame carForm;

	// Objects related to data model
	private CarDao carDao = DaoFactory.getCarDao();
	private CustomerCarTableModel carTableModel;
	private RepairTableModel repairTableModel;


	/**
	 * The constructor method
	 */
	public CustomerDetailsController() {
		super();
		this.createPage();
	}

	/**
	 * Create and configure the list frame
	 */
	private void createPage() {
		if (mainFrame == null) {
			mainFrame = new CustomerDetailsFrame();

			// Set the table models
			carTableModel = new CustomerCarTableModel();
			mainFrame.carTable.setModel(carTableModel);

			repairTableModel = new RepairTableModel();
			mainFrame.repairTable.setModel(repairTableModel);

			// Add itself as action listener to create, edit and delete a car
			mainFrame.addCarButton.addActionListener(this);
		}
	}

	/**
	 * An action should be performed based on the button triggering it
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainFrame.addCarButton) {
			this.showCarCreationForm();
		} else if (e.getSource() == carForm.brandComboBox) {
			Brand brand = (Brand) carForm.brandComboBox.getSelectedItem();
			List<Model> models = DaoFactory.getModelDao().getModelsByBrand(brand);
			Model[] array = models.toArray(new Model[models.size()]);
			DefaultComboBoxModel<Model> model = new DefaultComboBoxModel<Model>(array);
			carForm.modelComboBox.setModel(model);
			carForm.modelComboBox.setEnabled(true);
		} else if (e.getSource() == carForm.okButton) {
			this.saveCustomerCar();
		}
	}

	/**
	 * Add a new car according to a customer and a (Brand->) model
	 * triggered by the save button
	 */
	public void addCarToCustomer(String plate, Model model) {
		if(plate.isEmpty()||model.equals(null)){
			//no plate or model defined before save -> pop up "error"
			JOptionPane.showMessageDialog(null, "You must specify a plate and a model.");
		}else{
			//must verify if the plate already exist in the database
			CustomersCar customer_car = new CustomersCar();
			customer_car.setModel(model);
			customer_car.setNumber_plate(plate);
			customer_car.setCustomer(customer);
			carDao.addCar(customer_car);
			carTableModel.addCar(customer_car);
			carForm.dispose();
		}

	}

	/**
	 * Displays the car creation form in a new window.
	 * Listen to the action triggered by the save button
	 */
	public void showCarCreationForm() {
		carForm = new CarFormFrame();
		carForm.okButton.addActionListener(this);

		List<Brand> brands = DaoFactory.getBrandDao().getAllBrands();
		Brand[] brandsArray = brands.toArray(new Brand[brands.size()]);
		DefaultComboBoxModel<Brand> firstModel = new DefaultComboBoxModel<Brand>(brandsArray);
		carForm.brandComboBox.setModel(firstModel);
		carForm.brandComboBox.addActionListener(this);

		Brand brand = (Brand) carForm.brandComboBox.getSelectedItem();
		if(brand != null){
			List<Model> models = DaoFactory.getModelDao().getModelsByBrand(brand);
			Model[] modelsArray = models.toArray(new Model[models.size()]);
			DefaultComboBoxModel<Model> secondModel = new DefaultComboBoxModel<Model>(modelsArray);
			carForm.modelComboBox.setModel(secondModel);
			carForm.modelComboBox.setEnabled(true);
		}

		carForm.setVisible(true);
	}

	/**
	 * Triggered when the user wish to save a car
	 */
	public void saveCustomerCar() {
		Model model = (Model)carForm.modelComboBox.getSelectedItem();
		String plate_number = carForm.identifierField.getText();

		if (model != null && plate_number.length() > 0) {
			// Every fields contains a value
			// We create a new car with the values and save it
			CustomersCar car = new CustomersCar();
			car.setCustomer(customer);
			car.setModel(model);
			car.setNumber_plate(plate_number);

			// We add it to the database and our table model
			carDao.addCar(car);
			carTableModel.addCar(car);

			// We close the windows
			carForm.dispose();
		} else {
			// Not every fields contain a value so we display a message
			carForm.noEmptyLabel.setVisible(true);
		}
	}

	/**
	 * Getter for the customer to display
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Setter for the customer to display
	 */
	public void setCustomer(Customer customer) {
		// We update the interface with the informations from the Customer
		this.customer = customer;
		mainFrame.nameLabel.setText(customer.getFirstname() + " " + customer.getLastname());
		mainFrame.addressLabel.setText(customer.getAddress());
		mainFrame.phoneLabel.setText(customer.getPhone_number());
		carTableModel.refreshTable(customer);
		repairTableModel.refreshTable(this.customer);
	}

}
