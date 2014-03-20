package com.brookes.garage.frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.brookes.garage.entity.Part;

public class PartFormFrame extends JFrame {

	private static final long serialVersionUID = 1L;
		
	private JPanel contentPane;
	
	public JTextField refField;
	public JTextField nameField;
	public JTextField priceField;
	public JButton saveButton;
	public JLabel noEmptyLabel;
	
	private Part part;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartFormFrame frame = new PartFormFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PartFormFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 363, 216);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRef = new JLabel("Reference");
		lblRef.setBounds(13, 16, 60, 16);
		contentPane.add(lblRef);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(13, 56, 36, 16);
		contentPane.add(lblName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(13, 96, 36, 16);
		contentPane.add(lblPrice);
		
		refField = new JTextField();
		refField.setBounds(72, 10, 262, 28);
		contentPane.add(refField);
		refField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(72, 50, 262, 28);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		priceField = new JTextField();
		priceField.setBounds(72, 90, 262, 28);
		contentPane.add(priceField);
		priceField.setColumns(10);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(168, 138, 86, 29);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(cancelButton);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(248, 138, 86, 29);
		contentPane.add(saveButton);
		
		noEmptyLabel = new JLabel("This field is mandatory.");
		noEmptyLabel.setBounds(7, 143, 149, 16);
		contentPane.add(noEmptyLabel);
		noEmptyLabel.setVisible(false);
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

}
