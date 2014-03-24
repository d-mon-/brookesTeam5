package com.brookes.garage.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Model;

public class CarFormFrame extends JFrame {


private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	public JTextField identifierField;
	public JComboBox<Brand> brandComboBox;
	public JComboBox<Model> modelComboBox;
	
	public JLabel noEmptyLabel;
	
	public JButton cancelButton;
	public JButton saveButton;
	

	/**
	 * Create the frame.
	 */
	public CarFormFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 329, 161);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblIdentifier = new JLabel("Plate Number");
		lblIdentifier.setBounds(6, 11, 91, 16);
		lblIdentifier.setHorizontalAlignment(SwingConstants.RIGHT);
		
		identifierField = new JTextField();
		identifierField.setBounds(102, 5, 222, 28);
		lblIdentifier.setLabelFor(identifierField);
		identifierField.setColumns(10);
		
		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setBounds(35, 44, 62, 16);
		lblBrand.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(6, 104, 86, 29);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		saveButton = new JButton("Save");
		saveButton.setBounds(98, 104, 75, 29);
		saveButton.setHorizontalAlignment(SwingConstants.LEFT);
		
		noEmptyLabel = new JLabel("No field can be empty");
		noEmptyLabel.setBounds(179, 109, 138, 16);
		noEmptyLabel.setVisible(false);
		
		contentPane.setLayout(null);
		contentPane.add(lblIdentifier);
		contentPane.add(identifierField);
		contentPane.add(lblBrand);
		contentPane.add(cancelButton);
		contentPane.add(saveButton);
		contentPane.add(noEmptyLabel);

		
		brandComboBox = new JComboBox<Brand>();
		brandComboBox.setBounds(102, 40, 222, 27);
		contentPane.add(brandComboBox);
		
		JLabel lblModel = new JLabel("Model");
		lblModel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModel.setBounds(35, 76, 62, 16);
		contentPane.add(lblModel);
		
		
		modelComboBox = new JComboBox<Model>();
		modelComboBox.setBounds(102, 72, 222, 27);
		contentPane.add(modelComboBox);
	}
	

}
