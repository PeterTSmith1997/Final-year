package admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import dataModel.DataStore;
import dataModel.Database;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditBotUI extends JFrame{
	private JFrame ui;
	private DataStore dataStore;
	private String ip;
	private Database database;
	private JPanel Panel;
	private JLabel lblIp;
	private JTextField IpAddressText;
	private JLabel lblType;
	private JComboBox<String> typeComboBox;
	private JLabel lblDiscription;
	private JLabel lblUserAgent;
	private JTextField textField;
	private Map<String, String> botInfo = new HashMap<String, String>();
	private JTextArea botDiscriptioTextArea;
	private JButton btnSave;
	
	public EditBotUI() {
		ip= "66.249.79.2";
		dataStore = new  DataStore();
		database = new Database();
		botInfo= database.getBotInfo(ip);
		makeUi();
	}
	/**
	 * @param dataStore
	 * @param ip
	 */
	public EditBotUI(DataStore dataStore, String ip) {
		this.dataStore = dataStore;
		this.ip = ip;
		database = new Database();
		botInfo = database.getBotInfo(ip);
		makeUi(); 
	}
	private void makeUi() {
		ui = new JFrame();
		ui.setLocationRelativeTo(null);
		ui.setResizable(false);
		ui.setTitle("Add bots");
		ui.getContentPane().setLayout(new BorderLayout(0,0));
		
		ui.setBounds(100, 100, 1169, 686);
		
		Panel = new JPanel();
		ui.getContentPane().add(Panel, BorderLayout.NORTH);
		
		JLabel lblAddBots = new JLabel("Add bots");
		Panel.add(lblAddBots);
		
		JPanel panel = new JPanel();
		ui.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[98.00][][807.00,grow][]", "[][][][][][]"));
		
		lblIp = new JLabel("IP ");
		panel.add(lblIp, "cell 0 0,alignx center");
		
		IpAddressText = new JTextField();
		panel.add(IpAddressText, "cell 2 0,growx");
		IpAddressText.setColumns(10);
		IpAddressText.setText(ip);
		IpAddressText.setEditable(false);
		
		lblType = new JLabel("Type");
		panel.add(lblType, "cell 0 1,alignx center");
		
		typeComboBox = new JComboBox<String>();
		panel.add(typeComboBox, "cell 2 1");
		
		
		ArrayList<String> botCats = database.getBotCats();
		for (int i = 0; i < botCats.size(); i++) {
			typeComboBox.addItem(botCats.get(i));
			if (botInfo.get("cat").equals(botCats.get(i).substring(0, 1))) {
				typeComboBox.setSelectedItem(botCats.get(i));
			}
			
		}
		
		lblDiscription = new JLabel("Discription");
		panel.add(lblDiscription, "cell 0 2,alignx center");
		
		botDiscriptioTextArea = new JTextArea();
		panel.add(botDiscriptioTextArea, "cell 2 2,grow");
		
		lblUserAgent = new JLabel("User Agent");
		panel.add(lblUserAgent, "cell 0 3,alignx center");
		
		textField = new JTextField();
		panel.add(textField, "cell 2 3,growx");
		textField.setColumns(10);
		botDiscriptioTextArea.setText(botInfo.get("Discription"));
		textField.setText(botInfo.get("Name"));
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cat = typeComboBox.getSelectedItem().toString().substring(0,1);
				botInfo.clear();
				botInfo.put("cat", cat);
				botInfo.put("ip", ip);
				botInfo.put("Discription", botDiscriptioTextArea.getText().trim());
				botInfo.put("Name", textField.getText().trim());
				
				database.updateBotInfo(botInfo);
			}
		});
		panel.add(btnSave, "cell 0 5,alignx center");
		this.ui.setVisible(true);
	}
	public static void main(String arg[]) { 
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new EditBotUI();
			}
		});
	}

}
