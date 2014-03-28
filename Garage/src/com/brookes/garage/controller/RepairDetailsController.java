package com.brookes.garage.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.brookes.garage.dao.DaoFactory;
import com.brookes.garage.dao.EstimateDao;
import com.brookes.garage.dao.RepairDao;
import com.brookes.garage.dao.RequestedPartDao;
import com.brookes.garage.dao.StatusDao;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.Estimate;
import com.brookes.garage.entity.Invoice;
import com.brookes.garage.entity.Part;
import com.brookes.garage.entity.Repair;
import com.brookes.garage.entity.RequestedPart;
import com.brookes.garage.entity.Status;
import com.brookes.garage.frame.EstimateFormDialog;
import com.brookes.garage.frame.RepairDetailsFrame;
import com.brookes.garage.frame.StatusFormFrame;
import com.brookes.garage.tablemodel.EstimateTableModel;
import com.brookes.garage.tablemodel.PartTableModel;
import com.brookes.garage.tablemodel.RequestedPartTableModel;

public class RepairDetailsController implements ActionListener, ListSelectionListener {

	// The repair displayed by the view
	private Repair repair;
	
	// The views managed by the controller
	public RepairDetailsFrame mainFrame;
	private StatusFormFrame statusForm;
	private EstimateFormDialog estimateForm;

	// Objects related to data model
	private PartTableModel formTableModel = new PartTableModel();
	private EstimateTableModel estimateTableModel = new EstimateTableModel();
	private RequestedPartTableModel partTableModel = new RequestedPartTableModel();
	
	private RepairDao repairDao = DaoFactory.getRepairDao();
	private StatusDao statusDao = DaoFactory.getStatusDao();
	private EstimateDao estimateDao = DaoFactory.getEstimateDao();
	private RequestedPartDao requestedPartDao = DaoFactory.getRequestedPartDao();
	
	// Allows to know if we are currently invalidating a repair
	private Boolean invalidating = false;

	
	/**
	 * The constructor method
	 */
	public RepairDetailsController() {
		super();

		this.createPage();
	}

	/**
	 * Create and configure the list frame
	 */
	private void createPage() {
		if (mainFrame == null) {
			mainFrame = new RepairDetailsFrame();
			
			// Set the table model and add itself
			mainFrame.estimateTable.setModel(estimateTableModel);
			mainFrame.estimateTable.getSelectionModel().addListSelectionListener(this);

			mainFrame.partTable.setModel(partTableModel);

			// Add itself as action listener to change the status of a repair
			mainFrame.statusButton.addActionListener(this);
			mainFrame.invalidateButton.addActionListener(this);
		}
	}

	/**
	 * An action should be performed based on the button triggering it
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainFrame.statusButton) {
			this.showStatusModificationForm();
		} else if (e.getSource() == mainFrame.invalidateButton) {
			this.invalidateCurrentEstimate();
		} else if (statusForm != null && e.getSource() == statusForm.okButton) {
			this.saveNewStatus();
		} else if (estimateForm != null && e.getSource() == estimateForm.addPartButton) {
			this.addPartToEstimate();
		} else if (estimateForm != null && e.getSource() == estimateForm.saveButton) {
			this.addEstimateToRepair();
		}
	}

	/**
	 * Displays a form allowing to modify the status of the repair
	 */
	public void showStatusModificationForm() {
		statusForm = new StatusFormFrame();
		
		// The status that the user can choose
		// are based on the successors of the current repair status
		List<Status> successors = repair.getStatus().getSuccessors();
		Status[] array = successors.toArray(new Status[successors.size()]);
		DefaultComboBoxModel<Status> model = new DefaultComboBoxModel<Status>(array);
		statusForm.statusComboBox.setModel(model);

		statusForm.okButton.addActionListener(this);
		statusForm.setVisible(true);
	}
	
	/**
	 * We save the new status chosen by the user
	 */
	public void saveNewStatus() {
		Status newStatus = (Status)statusForm.statusComboBox.getSelectedItem();

		if (newStatus.getId() == statusDao.getEstimateStatus().getId()) {
			// If the user chose the status that requires to create an estimate
			this.displayEstimateForm();
			statusForm.dispose();
			return;
		} else if (newStatus.getId() == statusDao.getInvoiceStatus().getId()) {
			// If the user chose the status that requires to create an invoice
			// based on the current estimate
			
			List<Estimate>estimates = repair.getEstimates();
			for (Estimate estimate : estimates) {
				if (estimate.isInvalidated() == false) {
					Invoice invoice = new Invoice();
					invoice.setEstimate(estimate);
					invoice.setCreation_date(new Date());
					invoice.setIdentifier("I"+estimate.hashCode());

					// We add it to the database and update our table model
					DaoFactory.getInvoiceDao().addInvoice(invoice);
					
					estimate.setInvoice(invoice);
					estimateDao.updateEstimate(estimate);
					estimateTableModel.updateEstimate(estimate);
				}
			}
		}
		
		repair.setStatus(newStatus);
		repairDao.updateRepair(repair);
		
		// If the selected status doesn't have successors we disable the button
		if (newStatus.getSuccessors().size() == 0) {
			mainFrame.statusButton.setEnabled(false);
		} else {
			mainFrame.statusButton.setEnabled(true);
		}
		
		// We change the label displaying the name of the status
		mainFrame.statusLabel.setText(repair.getStatus().toString());

		statusForm.dispose();
	}
	
	/**
	 * Displays the form allowing to create an estimate
	 */
	public void displayEstimateForm() {
		estimateForm = new EstimateFormDialog();
		
		// We get the list of parts for the model currently being repaired
		// We display them as choices in a combobox
		List<Part> parts = DaoFactory.getPartDao().getPartsByModel(repair.getCar().getModel());
		Part[] array = parts.toArray(new Part[parts.size()]);
		DefaultComboBoxModel<Part> model = new DefaultComboBoxModel<Part>(array);
		estimateForm.partComboBox.setModel(model);

		estimateForm.addPartButton.addActionListener(this);
		estimateForm.saveButton.addActionListener(this);

		formTableModel.data = new ArrayList<Part>();
		estimateForm.partTable.setModel(formTableModel);
		
		estimateForm.setVisible(true);
	}
	
	/**
	 * The user chose to add a part to the estimate
	 */
	public void addPartToEstimate() {
		// We add the part to our table
		Part selectedPart = (Part)estimateForm.partComboBox.getSelectedItem();
		formTableModel.addPart(selectedPart);
		
		// We update the total price for the estimate
		double totalPrice = this.totalPriceForParts(formTableModel.data);
		String priceString = "Total £"+totalPrice;
		estimateForm.totalPriceLabel.setText(priceString);
	}
	
	/**
	 * We save the Estimate to our Repair
	 */
	public void addEstimateToRepair() {
		if (this.invalidating) {
			// If we were invalidating the previous Estimate we change its state
			int rowIndex = mainFrame.estimateTable.getSelectedRow();
			Estimate selectedEstimate = estimateTableModel.data.get(rowIndex);

			selectedEstimate.setInvalidated(true);
			
			estimateDao.updateEstimate(selectedEstimate);
			estimateTableModel.updateEstimate(selectedEstimate);
			
			this.invalidating = false;
		} else {
			// If we were not invalidating the previous Estimate
			// We need to set the new status to our repair
			Status newStatus = (Status)statusForm.statusComboBox.getSelectedItem();
			repair.setStatus(newStatus);
			repairDao.updateRepair(repair);
			
			mainFrame.statusLabel.setText(repair.getStatus().toString());
		}
		
		
		// We create the new estimate and save it
		Estimate estimate = new Estimate();
		estimate.setRepair(repair);
		estimate.setInvalidated(false);		
		estimate.setCreation_date(new Date());
		estimate.setIdentifier("E"+estimate.hashCode());
		
		// We add it to the database and to our table model
		estimateDao.addEstimate(estimate);
		estimateTableModel.addEstimate(estimate);
		
		// We get the list of Part and create a list of RequestedPart
		// that we set to our estimate
		List<Part> parts = formTableModel.data;
		List<RequestedPart> requestedParts = new ArrayList<RequestedPart>();
		for (Part part : parts) {
			RequestedPart newPart = new RequestedPart();
			newPart.setEstimate(estimate);
			newPart.setPart(part);
			requestedPartDao.addRequestedPart(newPart);
			requestedParts.add(newPart);
		}
		
		estimate.setParts(requestedParts);
		
		mainFrame.estimateTable.setRowSelectionInterval(0, 0);
		estimateForm.dispose();
	}
	
	/**
	 * Return the total price for a given list of parts
	 */
	public double totalPriceForParts(List<Part> parts) {
		double totalPrice = 0;
		
		for (Part part : parts)
			totalPrice += part.getPrice();
		
		return totalPrice;
	}
	
	/**
	 * Save that we are invalidating the estimate
	 * Ask the user for a new Estimate
	 */
	public void invalidateCurrentEstimate() {
		this.invalidating = true;
		this.displayEstimateForm();
	}
	
	/**
	 * Getter for the repair to display
	 */
	public Repair getRepair() {
		return repair;
	}

	/**
	 * Setter for the repair to display
	 */
	public void setRepair(Repair repair) {
		this.repair = repair;
		
		// We update the interface with the informations from the Repair
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = dateFormat.format(repair.getCreation_date());
		mainFrame.repairLabel.setText(repair.getIdentifier() + " - " + formattedDate);
		
		Customer customer = repair.getCar().getCustomer();
		String customerName = customer.getFirstname() + " " + customer.getLastname();
		mainFrame.customerLabel.setText(customerName);		
		mainFrame.carLabel.setText(repair.getCar().toString());
		mainFrame.descriptionLabel.setText(repair.getDescription());
		mainFrame.statusLabel.setText(repair.getStatus().toString());
		
		if (repair.getStatus().getSuccessors().size() == 0) {
			mainFrame.statusButton.setEnabled(false);
		} else {
			mainFrame.statusButton.setEnabled(true);
		}
		
		estimateTableModel.refreshContent(repair);
	}

	/**
	 * The row selected has changed
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == mainFrame.estimateTable.getSelectionModel()) {
			int rowIndex = mainFrame.estimateTable.getSelectedRow();			

			// We check if a row is selected
			if (rowIndex >= 0) {
				Estimate selectedEstimate = estimateTableModel.data.get(rowIndex);
				
				partTableModel.updateContent(selectedEstimate);
				
				if(statusDao.getEstimateStatus().getId() == repair.getStatus().getId()) {
					// We set the state of the Invalidate button depending on the current state
					// of the Estimate selected (already invalidated or not)
					mainFrame.invalidateButton.setEnabled(!selectedEstimate.isInvalidated());				
				}
			} else {
				partTableModel.clearPartContent();
				mainFrame.invalidateButton.setEnabled(false);				
			}
		}

	}
	
}
