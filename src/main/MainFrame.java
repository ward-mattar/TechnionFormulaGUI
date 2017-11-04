package main;

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.System;
import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFormattedTextField;

public class MainFrame {

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table = new DefaultTableModel(new Object[] { "Feild1" , "Feild2", "Feild3", "Feild4", "Feild5"} ,0);
		scroll = new JScrollPane(new JTable(table));
		frame.getContentPane().add(scroll, BorderLayout.NORTH);
		System.out.println(System.getProperty("user.dir"));
		try {
			speed = new CVSReader("data/Speed-Gear.csv");
		} catch(FileNotFoundException e){
			// log 
			e.printStackTrace();
			return;
		} 
		// log success
		
		timer = new Timer(DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> nextRecord = speed.getNextRecordLine();
				String nextRecordString = speed.getNextRecordString();
				if(nextRecordString == ""){
					timer.stop();
					// log
					return;
				}
				System.out.println(nextRecordString);
				Object[] newRow = new Object[FEILDS_NUMBER];
				for (int i = 0 ; i < FEILDS_NUMBER; i++)
					newRow[i] = nextRecord.get(i);
				table.addRow(newRow);
			}
		});
		timer.start();
		// System.out.println(speed.getNextRecordString());
				
	}
	
	/**
	 * time between updates, in milliseconds
	 */
	public static final int DELAY = 1000; 
	
	public static final int FEILDS_NUMBER = 5;
	private JFrame frame;
	private Timer timer;
	private CVSReader speed;
	private DefaultTableModel table;
	private JScrollPane scroll;
}
