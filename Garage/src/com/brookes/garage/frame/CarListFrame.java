package com.brookes.garage.frame;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


public class CarListFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public JPanel contentPane;
	
	public JTable brandTable;
	public JButton brandCreateButton;
	public JButton brandEditButton;
	public JButton brandDeleteButton;

	public JTable modelTable;
	public JButton modelCreateButton;
	public JButton modelEditButton;
	public JButton modelDeleteButton;
	
	public JTable partTable;
	public JButton partCreateButton;
	public JButton partEditButton;
	public JButton partDeleteButton;
	
	private JSplitPane splitPane;
	private JSplitPane splitPane_1;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;


	/**
	 * Create the frame.
	 */
	public CarListFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		splitPane.setBorder(null);
		splitPane.setOpaque(false);
		contentPane.add(splitPane);
		
		splitPane_1 = new JSplitPane();
		splitPane_1.setBorder(null);
		splitPane_1.setOpaque(false);
		splitPane.setRightComponent(splitPane_1);
		
		panel_3 = new JPanel();
		panel_3.setOpaque(false);
		splitPane_1.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		panel_4 = new JPanel();
		panel_4.setOpaque(false);
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("70px"),
				ColumnSpec.decode("70px"),
				ColumnSpec.decode("70px"),},
			new RowSpec[] {
				RowSpec.decode("28px"),
				RowSpec.decode("28px"),}));
		
		JLabel modelLabel = new JLabel("  Models");
		panel_4.add(modelLabel, "1, 1, left, center");
		
		modelCreateButton = new JButton("Create");
		modelCreateButton.setEnabled(false);
		panel_4.add(modelCreateButton, "1, 2, left, top");
		
		modelEditButton = new JButton("Edit");
		modelEditButton.setEnabled(false);
		panel_4.add(modelEditButton, "2, 2, left, top");
		
		modelDeleteButton = new JButton("Delete");
		modelDeleteButton.setEnabled(false);
		panel_4.add(modelDeleteButton, "3, 2, left, top");
		
		scrollPane_1 = new JScrollPane();
		panel_3.add(scrollPane_1, BorderLayout.CENTER);
		
		modelTable = new JTable();
		modelTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(modelTable);
		
		panel_5 = new JPanel();
		panel_5.setOpaque(false);
		splitPane_1.setRightComponent(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_6.setOpaque(false);
		panel_5.add(panel_6, BorderLayout.NORTH);
		panel_6.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("left:70px"),
				ColumnSpec.decode("70px"),
				ColumnSpec.decode("70px"),},
			new RowSpec[] {
				RowSpec.decode("28px"),
				RowSpec.decode("28px"),}));
		
		JLabel partLabel = new JLabel("  Parts");
		panel_6.add(partLabel, "1, 1, left, center");
		
		partCreateButton = new JButton("Create");
		partCreateButton.setEnabled(false);
		panel_6.add(partCreateButton, "1, 2, left, top");
		
		partEditButton = new JButton("Edit");
		partEditButton.setEnabled(false);
		panel_6.add(partEditButton, "2, 2, left, top");
		
		partDeleteButton = new JButton("Delete");
		partDeleteButton.setEnabled(false);
		panel_6.add(partDeleteButton, "3, 2, left, top");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_5.add(scrollPane_2, BorderLayout.CENTER);
		
		partTable = new JTable();
		partTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(partTable);
		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		splitPane.setLeftComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("70px"),
				ColumnSpec.decode("70px"),
				ColumnSpec.decode("70px"),},
			new RowSpec[] {
				RowSpec.decode("28px"),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel brandLabel = new JLabel("  Brands");
		panel_2.add(brandLabel, "1, 1, left, center");
		
		brandCreateButton = new JButton("Create");
		panel_2.add(brandCreateButton, "1, 2, left, center");
		
		brandEditButton = new JButton("Edit");
		brandEditButton.setEnabled(false);
		panel_2.add(brandEditButton, "2, 2, left, center");
		
		brandDeleteButton = new JButton("Delete");
		brandDeleteButton.setEnabled(false);
		panel_2.add(brandDeleteButton, "3, 2, left, center");
		
		scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		brandTable = new JTable();
		brandTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(brandTable);
		
	}
}
