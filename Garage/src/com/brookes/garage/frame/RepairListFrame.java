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

public class RepairListFrame extends JFrame {

private static final long serialVersionUID = 1L;
	
	public JPanel contentPane;
	public JTable table;
	public JButton editButton;
	public JButton viewButton;
	public JButton createButton;

	/**
	 * Create the frame.
	 */
	public RepairListFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		createButton = new JButton("Create");
		GridBagConstraints gbc_createButton = new GridBagConstraints();
		gbc_createButton.anchor = GridBagConstraints.WEST;
		gbc_createButton.gridwidth = 2;
		gbc_createButton.insets = new Insets(0, 0, 5, 5);
		gbc_createButton.gridx = 0;
		gbc_createButton.gridy = 0;
		contentPane.add(createButton, gbc_createButton);
		
		editButton = new JButton("Edit");
		editButton.setEnabled(false);
		GridBagConstraints gbc_editButton = new GridBagConstraints();
		gbc_editButton.anchor = GridBagConstraints.EAST;
		gbc_editButton.insets = new Insets(0, 0, 5, 5);
		gbc_editButton.gridx = 12;
		gbc_editButton.gridy = 0;
		contentPane.add(editButton, gbc_editButton);
		
		viewButton = new JButton("View");
		viewButton.setEnabled(false);
		GridBagConstraints gbc_viewButton = new GridBagConstraints();
		gbc_viewButton.anchor = GridBagConstraints.EAST;
		gbc_viewButton.insets = new Insets(0, 0, 5, 0);
		gbc_viewButton.gridx = 13;
		gbc_viewButton.gridy = 0;
		contentPane.add(viewButton, gbc_viewButton);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 14;
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		contentPane.add(new JScrollPane(table), gbc_table);
		
	}


}
