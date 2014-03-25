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

import com.brookes.garage.entity.Brand;

public class BrandFormFrame extends JDialog {

	private static final long serialVersionUID = 1L;

	public JTextField nameField;
	public JLabel noEmptyLabel;
	
	private Brand brand;

	
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
	public BrandFormFrame() {
		setResizable(false);
		setBounds(100, 100, 344, 114);
		getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(13, 16, 36, 16);
		getContentPane().add(lblName);
		
		nameField = new JTextField();
		nameField.setBounds(57, 10, 277, 28);
		getContentPane().add(nameField);
		nameField.setColumns(10);
				
		noEmptyLabel = new JLabel("This field is mandatory.");
		noEmptyLabel.setForeground(Color.RED);
		noEmptyLabel.setBounds(25, 57, 149, 16);
		getContentPane().add(noEmptyLabel);
		noEmptyLabel.setVisible(false);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(-35, 48, 369, 33);
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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
}
