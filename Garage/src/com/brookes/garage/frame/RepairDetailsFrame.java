package com.brookes.garage.frame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

public class RepairDetailsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;

	public JButton backButton;
	public JLabel repairLabel;
	public JLabel statusLabel;
	public JLabel customerLabel;
	public JTextArea descriptionLabel;
	public JLabel carLabel;
	public JButton statusButton;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	public JTable estimateTable;
	public JTable partTable;
	public JButton invalidateButton;

	/**
	 * Create the frame.
	 */
	public RepairDetailsFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 592, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 80, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		backButton = new JButton("<<");
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.WEST;
		gbc_backButton.insets = new Insets(0, 0, 5, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;
		contentPane.add(backButton, gbc_backButton);
		
		statusLabel = new JLabel("Status");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.gridwidth = 3;
		gbc_statusLabel.anchor = GridBagConstraints.EAST;
		gbc_statusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_statusLabel.gridx = 7;
		gbc_statusLabel.gridy = 1;
		contentPane.add(statusLabel, gbc_statusLabel);
		
		repairLabel = new JLabel("Reference - Date");
		GridBagConstraints gbc_repairLabel = new GridBagConstraints();
		gbc_repairLabel.anchor = GridBagConstraints.WEST;
		gbc_repairLabel.insets = new Insets(0, 0, 5, 5);
		gbc_repairLabel.gridx = 0;
		gbc_repairLabel.gridy = 1;
		contentPane.add(repairLabel, gbc_repairLabel);
		
		statusButton = new JButton("Update Status");
		GridBagConstraints gbc_statusButton = new GridBagConstraints();
		gbc_statusButton.anchor = GridBagConstraints.EAST;
		gbc_statusButton.insets = new Insets(0, 0, 5, 0);
		gbc_statusButton.gridx = 10;
		gbc_statusButton.gridy = 1;
		contentPane.add(statusButton, gbc_statusButton);
		
		descriptionLabel = new JTextArea("Description");
		descriptionLabel.setFont(UIManager.getFont("Label.font"));
		descriptionLabel.setLineWrap(true);
		descriptionLabel.setOpaque(false);
		descriptionLabel.setWrapStyleWord(true);
		descriptionLabel.setEditable(false);
		GridBagConstraints gbc_descriptionLabel = new GridBagConstraints();
		gbc_descriptionLabel.gridheight = 3;
		gbc_descriptionLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_descriptionLabel.gridwidth = 6;
		gbc_descriptionLabel.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionLabel.gridx = 5;
		gbc_descriptionLabel.gridy = 2;
		contentPane.add(descriptionLabel, gbc_descriptionLabel);
		
		customerLabel = new JLabel("Firstname Lastname");
		GridBagConstraints gbc_customerLabel = new GridBagConstraints();
		gbc_customerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_customerLabel.anchor = GridBagConstraints.WEST;
		gbc_customerLabel.gridx = 0;
		gbc_customerLabel.gridy = 2;
		contentPane.add(customerLabel, gbc_customerLabel);
		
		carLabel = new JLabel("Brand Model");
		GridBagConstraints gbc_carLabel = new GridBagConstraints();
		gbc_carLabel.insets = new Insets(0, 0, 5, 5);
		gbc_carLabel.anchor = GridBagConstraints.WEST;
		gbc_carLabel.gridx = 0;
		gbc_carLabel.gridy = 3;
		contentPane.add(carLabel, gbc_carLabel);
		
		invalidateButton = new JButton("Invalidate");
		invalidateButton.setEnabled(false);
		GridBagConstraints gbc_invalidateButton = new GridBagConstraints();
		gbc_invalidateButton.anchor = GridBagConstraints.EAST;
		gbc_invalidateButton.insets = new Insets(0, 0, 5, 0);
		gbc_invalidateButton.gridx = 10;
		gbc_invalidateButton.gridy = 5;
		contentPane.add(invalidateButton, gbc_invalidateButton);
		
		splitPane = new JSplitPane();
		splitPane.setBorder(null);
		splitPane.setResizeWeight(0.45);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.gridwidth = 11;
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 6;
		contentPane.add(splitPane, gbc_splitPane);
		
		scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		estimateTable = new JTable();
		scrollPane.setViewportView(estimateTable);
		
		scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		partTable = new JTable();
		scrollPane_1.setViewportView(partTable);
	}

}
