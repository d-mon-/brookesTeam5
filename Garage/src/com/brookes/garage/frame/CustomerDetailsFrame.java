package com.brookes.garage.frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class CustomerDetailsFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public JPanel contentPane;
	
	public JButton backButton;
	public JLabel nameLabel;
	public JLabel addressLabel;
	public JLabel phoneLabel;
	public JButton addCarButton;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	public JTable carTable;
	public JTable repairTable;
	private JLabel lblCars;
	private JLabel lblRepairs;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CustomerDetailsFrame frame = new CustomerDetailsFrame();
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
	public CustomerDetailsFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(21dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(20dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(58dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(32dlu;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("top:default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(16dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		backButton = new JButton("<<");
		contentPane.add(backButton, "2, 2");
		
		addCarButton = new JButton("Add car");
		addCarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		lblCars = new JLabel("Cars");
		contentPane.add(lblCars, "8, 2");
		contentPane.add(addCarButton, "10, 2");
		
		nameLabel = new JLabel("firstname lastname");
		contentPane.add(nameLabel, "2, 4, 3, 1, left, default");
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "8, 4, 3, 5, fill, fill");
		
		carTable = new JTable();
		scrollPane.setViewportView(carTable);
		
		addressLabel = new JLabel("Address");
		contentPane.add(addressLabel, "2, 6, 3, 1, left, default");
				
		phoneLabel = new JLabel("Phone Number");
		contentPane.add(phoneLabel, "2, 8, 3, 1, left, default");
		
		lblRepairs = new JLabel("Repairs");
		contentPane.add(lblRepairs, "8, 12");
		
		scrollPane_1 = new JScrollPane();
		contentPane.add(scrollPane_1, "8, 13, 3, 6, fill, fill");
		
		repairTable = new JTable();
		scrollPane_1.setViewportView(repairTable);
	
	}

}
