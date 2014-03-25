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
import com.brookes.garage.dao.StatusDao;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.Estimate;
import com.brookes.garage.entity.Part;
import com.brookes.garage.entity.Repair;
import com.brookes.garage.entity.Status;
import com.brookes.garage.frame.EstimateFormDialog;
import com.brookes.garage.frame.RepairDetailsFrame;
import com.brookes.garage.frame.StatusFormFrame;
import com.brookes.garage.tablemodel.EstimateTableModel;
import com.brookes.garage.tablemodel.PartTableModel;

public class RepairDetailsController implements ActionListener, ListSelectionListener {

	private Repair repair;
	
	public RepairDetailsFrame mainFrame;
	private StatusFormFrame statusForm;
	private EstimateFormDialog estimateForm;

	private PartTableModel formTableModel = new PartTableModel();
	private EstimateTableModel estimateTableModel = new EstimateTableModel();
	private PartTableModel partTableModel = new PartTableModel();
	
	private RepairDao repairDao = DaoFactory.getRepairDao();
	private StatusDao statusDao = DaoFactory.getStatusDao();
	private EstimateDao estimateDao = DaoFactory.getEstimateDao();

	private Boolean invalidating = false;

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

	public void showStatusModificationForm() {
		statusForm = new StatusFormFrame();
		
		List<Status> successors = repair.getStatus().getSuccessors();
		Status[] array = successors.toArray(new Status[successors.size()]);
		DefaultComboBoxModel<Status> model = new DefaultComboBoxModel<Status>(array);
		statusForm.statusComboBox.setModel(model);

		statusForm.okButton.addActionListener(this);
		statusForm.setVisible(true);
	}
	
	public void saveNewStatus() {
		Status newStatus = (Status)statusForm.statusComboBox.getSelectedItem();

		if (newStatus.getId() == statusDao.getEstimateStatus().getId()) {
			this.displayEstimateForm();
		} else {
			repair.setStatus(newStatus);
			repairDao.updateRepair(repair);
			
			mainFrame.statusLabel.setText(repair.getStatus().toString());
		}

		statusForm.dispose();
	}
	
	public void displayEstimateForm() {
		estimateForm = new EstimateFormDialog();
		
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
	
	public void addPartToEstimate() {
		Part selectedPart = (Part)estimateForm.partComboBox.getSelectedItem();
		formTableModel.addPart(selectedPart);
		
		double totalPrice = this.totalPriceForParts(formTableModel.data);
		String priceString = "Total £"+totalPrice;
		estimateForm.totalPriceLabel.setText(priceString);
	}
	
	public void addEstimateToRepair() {
		
		if (this.invalidating) {
			int rowIndex = mainFrame.estimateTable.getSelectedRow();
			Estimate selectedEstimate = estimateTableModel.data.get(rowIndex);

			selectedEstimate.setInvalidated(true);
			
			estimateDao.updateEstimate(selectedEstimate);
			estimateTableModel.updateEstimate(selectedEstimate);
			
			this.invalidating = false;
		} else {
			// First we set the new status to our repair
			Status newStatus = (Status)statusForm.statusComboBox.getSelectedItem();
			repair.setStatus(newStatus);
			repairDao.updateRepair(repair);
			
			mainFrame.statusLabel.setText(repair.getStatus().toString());
		}
		
		
		// We create a new estimate and save it
		Estimate estimate = new Estimate();
		estimate.setRepair(repair);
		estimate.setInvalidated(false);
		List<Part> parts = formTableModel.data;
		estimate.setParts(parts);
		estimate.setCreation_date(new Date());
		estimate.setIdentifier("E"+estimate.hashCode());
		
		// We add it to the database and to our table model
		estimateDao.addEstimate(estimate);
		estimateTableModel.addEstimate(estimate);
		
		mainFrame.estimateTable.setRowSelectionInterval(0, 0);
		estimateForm.dispose();
	}
	
	public double totalPriceForParts(List<Part> parts) {
		double totalPrice = 0;
		
		for (Part part : parts)
			totalPrice += part.getPrice();
		
		return totalPrice;
	}
	
	public void invalidateCurrentEstimate() {
		this.invalidating = true;
		this.displayEstimateForm();
	}
	
	public Repair getRepair() {
		return repair;
	}

	public void setRepair(Repair repair) {
		this.repair = repair;
		
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
		}
		
		estimateTableModel.refreshContent(repair);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == mainFrame.estimateTable.getSelectionModel()) {
			int rowIndex = mainFrame.estimateTable.getSelectedRow();			
			
			System.out.println(rowIndex + " " + estimateTableModel.data.size());
			// We check if a row is selected
			if (rowIndex >= 0) {
				Estimate selectedEstimate = estimateTableModel.data.get(rowIndex);
				
				partTableModel.updateContent(selectedEstimate);
				
				if(statusDao.getEstimateStatus().getId() == repair.getStatus().getId()) {
					mainFrame.invalidateButton.setEnabled(!selectedEstimate.isInvalidated());				
				}
			}
		}

	}
	
}
