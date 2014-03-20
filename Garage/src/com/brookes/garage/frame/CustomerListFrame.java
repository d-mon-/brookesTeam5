package com.brookes.garage.frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;


public class CustomerListFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public JPanel contentPane;
	public JTable table;
	public JButton editButton;
	public JButton deleteButton;
	public JButton viewButton;
	public JButton createButton;
	public JTextField filterTextField;
	private JLabel lblFilter;
	

	/**
	 * Create the frame.
	 */
	public CustomerListFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		createButton = new JButton("Create");
		GridBagConstraints gbc_createButton = new GridBagConstraints();
		gbc_createButton.anchor = GridBagConstraints.WEST;
		gbc_createButton.gridwidth = 2;
		gbc_createButton.insets = new Insets(0, 0, 5, 5);
		gbc_createButton.gridx = 0;
		gbc_createButton.gridy = 0;
		contentPane.add(createButton, gbc_createButton);
		
		lblFilter = new JLabel("filter:");
		GridBagConstraints gbc_lblFilter = new GridBagConstraints();
		gbc_lblFilter.insets = new Insets(0, 0, 5, 5);
		gbc_lblFilter.anchor = GridBagConstraints.EAST;
		gbc_lblFilter.gridx = 2;
		gbc_lblFilter.gridy = 0;
		contentPane.add(lblFilter, gbc_lblFilter);
		
		filterTextField = new JTextField();
		//Whenever filterText changes, invoke newFilter.				
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 0;
		contentPane.add(filterTextField, gbc_textField);
		
        
		
		
		
		deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.anchor = GridBagConstraints.EAST;
		gbc_deleteButton.insets = new Insets(0, 0, 5, 5);
		gbc_deleteButton.gridx = 13;
		gbc_deleteButton.gridy = 0;
		contentPane.add(deleteButton, gbc_deleteButton);
		
		editButton = new JButton("Edit");
		editButton.setEnabled(false);
		GridBagConstraints gbc_editButton = new GridBagConstraints();
		gbc_editButton.anchor = GridBagConstraints.EAST;
		gbc_editButton.insets = new Insets(0, 0, 5, 5);
		gbc_editButton.gridx = 14;
		gbc_editButton.gridy = 0;
		contentPane.add(editButton, gbc_editButton);
		
		viewButton = new JButton("View");
		viewButton.setEnabled(false);
		GridBagConstraints gbc_viewButton = new GridBagConstraints();
		gbc_viewButton.anchor = GridBagConstraints.EAST;
		gbc_viewButton.insets = new Insets(0, 0, 5, 0);
		gbc_viewButton.gridx = 15;
		gbc_viewButton.gridy = 0;
		contentPane.add(viewButton, gbc_viewButton);
		
		
		table = new JTable();
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 16;
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 2;
		contentPane.add(new JScrollPane(table), gbc_table);
		
		
		
	}

}
