package com.brookes.garage.frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.brookes.garage.entity.Part;

/**
 * Allows to create a car part by typing its name
 */
public class PartFormFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	public JTextField refField;
	public JTextField nameField;
	public JTextField priceField;
	public JLabel noEmptyLabel;
	
	private Part part;
	
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
	public PartFormFrame() {
		setResizable(false);
		setBounds(100, 100, 345, 196);
		getContentPane().setLayout(null);
		
		JLabel lblRef = new JLabel("Reference");
		lblRef.setBounds(13, 16, 66, 16);
		getContentPane().add(lblRef);
	
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(13, 56, 36, 16);
		getContentPane().add(lblName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(13, 96, 36, 16);
		getContentPane().add(lblPrice);
		
		refField = new JTextField();

		refField.setBounds(79, 10, 255, 28);
		getContentPane().add(refField);
		refField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(79, 50, 255, 28);
		getContentPane().add(nameField);
		nameField.setColumns(10);
		
		priceField = new JTextField();
		priceField.setBounds(79, 90, 255, 28);
		getContentPane().add(priceField);
		priceField.setColumns(10);
		
		noEmptyLabel = new JLabel("All fields are mandatory.");
		noEmptyLabel.setForeground(Color.RED);
		noEmptyLabel.setBounds(10, 136, 149, 16);
		getContentPane().add(noEmptyLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(-35, 129, 369, 33);
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
	
	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}
}
