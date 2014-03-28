package com.brookes.garage.frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Model;

/**
 * Form allowing to create a car assigned to a customer
 */
public class CarFormFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	public JTextField identifierField;
	public JComboBox<Brand> brandComboBox;
	public JComboBox<Model> modelComboBox;
	
	public JLabel noEmptyLabel;
	
	public JButton okButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			StatusFormFrame dialog = new StatusFormFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CarFormFrame() {
		setResizable(false);
		setBounds(100, 100, 331, 169);
		getContentPane().setLayout(null);
		
		JLabel lblIdentifier = new JLabel("Plate Number");
		lblIdentifier.setBounds(6, 11, 91, 16);
		lblIdentifier.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(lblIdentifier);
		
		identifierField = new JTextField();
		identifierField.setBounds(102, 5, 222, 28);
		lblIdentifier.setLabelFor(identifierField);
		identifierField.setColumns(10);
		getContentPane().add(identifierField);
		
		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setBounds(35, 44, 62, 16);
		lblBrand.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(lblBrand);
		
		noEmptyLabel = new JLabel("All fields are mandatory.");
		noEmptyLabel.setForeground(Color.RED);
		noEmptyLabel.setBounds(12, 117, 149, 16);
		getContentPane().add(noEmptyLabel);
		
		
		brandComboBox = new JComboBox<Brand>();
		brandComboBox.setBounds(102, 40, 222, 27);
		getContentPane().add(brandComboBox);
		
		JLabel lblModel = new JLabel("Model");
		lblModel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModel.setBounds(35, 76, 62, 16);
		getContentPane().add(lblModel);
		
		
		modelComboBox = new JComboBox<Model>();
		modelComboBox.setBounds(102, 72, 222, 27);
		getContentPane().add(modelComboBox);
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(-45, 105, 369, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		noEmptyLabel.setVisible(false);
		
	}
	
}
