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

import com.brookes.garage.entity.Model;
import java.awt.Color;

public class ModelFormFrame extends JFrame {

	private static final long serialVersionUID = 1L;
		
	private JPanel contentPane;
	
	public JTextField nameField;
	public JButton saveButton;
	public JLabel noEmptyLabel;
	
	private Model model;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModelFormFrame frame = new ModelFormFrame();
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
	public ModelFormFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 340, 99);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(13, 16, 36, 16);
		contentPane.add(lblName);
		
		nameField = new JTextField();
		nameField.setBounds(57, 10, 277, 28);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(174, 42, 86, 29);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(cancelButton);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(254, 42, 86, 29);
		contentPane.add(saveButton);
		
		noEmptyLabel = new JLabel("This field is mandatory.");
		noEmptyLabel.setForeground(Color.RED);
		noEmptyLabel.setBounds(13, 47, 149, 16);
		contentPane.add(noEmptyLabel);
		noEmptyLabel.setVisible(false);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
