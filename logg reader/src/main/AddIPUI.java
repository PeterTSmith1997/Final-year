package main;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddIPUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtAddAKnown;
	private JTextField textFieldIP; 
	Database database = new Database();
	public AddIPUI() {
		setSize(new Dimension(300, 300));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Ip");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtAddAKnown = new JTextField();
		txtAddAKnown.setText("Add a known IP");
		panel.add(txtAddAKnown);
		txtAddAKnown.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblIp = new JLabel("IP");
		lblIp.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblIp);
		
		textFieldIP = new JTextField();
		panel_1.add(textFieldIP);
		textFieldIP.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		panel_1.add(lblType);
		
		JComboBox<String> comboType = new JComboBox<String>();
		comboType.addItem("Select type");
		//ArrayList to store all the categories returned from the database
		ArrayList<String> categories = new ArrayList<String>();
		//Call the getCategories method from the database and populate the ArrayList with it
		categories = database.getCategories();
		for (int i = 0; i < categories.size(); i++) {
			comboType.addItem(categories.get(i));
		}
		
		panel_1.add(comboType);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		JButton btnAddIp = new JButton("Add IP");
		btnAddIp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String ip = textFieldIP.getText();
			IPFunctions ipFunctions = new IPFunctions();
			if (ipFunctions.validate(ip)) {
				String insertSql = "INSERT INTO knownip (IP, Type) VALUES('" 
						+ ip +"' ,'"
						+ comboType.getSelectedIndex()+"');";
				database.executeSQL(insertSql);
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Ip not vaild", "error", JOptionPane.ERROR_MESSAGE);
			}
			
			}
		});
		panel_2.add(btnAddIp);
		
	}

}
