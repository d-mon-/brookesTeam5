package com.brookes.garage.controller;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class Navigation
{
   	private JFrame appFrame;
   	private JPanel cPane;

   	private CustomerModuleController customerModuleController;
   	private CarModuleController carModuleController;
   	private RepairModuleController repairModuleController;
   	   	
   	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Navigation();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * The constructor method
	 */
   public Navigation()
   { 	
	   customerModuleController = new CustomerModuleController();
	   carModuleController = new CarModuleController();
	   repairModuleController = new RepairModuleController();
	   
       createGUI();
   }

   /**
	* Create and configure the GUI
	*/
   public void createGUI(){

   		// Create a frame, get its content pane and set layout
   		appFrame = new JFrame("Garage");
        cPane = new JPanel();
        cPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        cPane.setLayout(new BorderLayout(0, 0));
		
        // Configure basic values and options for the windows
        appFrame.setContentPane(cPane);
   		appFrame.setSize(800,700);
   		appFrame.setResizable(false);
   		appFrame.setVisible(true);
   		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   			
   				
   		// Create and configure the tabbed pane (composed of modules)
   		JTabbedPane tabbedPane = new JTabbedPane();
   		tabbedPane.addTab("Customers", customerModuleController.mainPanel);
   		tabbedPane.addTab("Cars", carModuleController.mainPanel);
   		tabbedPane.addTab("Repairs", repairModuleController.mainPanel);
   		cPane.add(tabbedPane, BorderLayout.CENTER);

   	}

}