package admin;

import javax.swing.JFrame;

import dataModel.DataStore;
import dataModel.Database;
import main.LogData;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author peter
 * @version 21 Jul 2019
 */
public class AdminUI extends JFrame {
	private DataStore dataStore;
	private JLabel lblReportedIpsLast;
	private JTable tblLast30Days;
	private JTable tblAllTimeReports;
	private Database database;

	public AdminUI(DataStore dataStore) {
		setName("AdminUI");
		database = new Database();
		this.dataStore = dataStore;
		makeGUI();

	}

	public void makeGUI() {
		setLocationRelativeTo(null);
		setTitle("Admin ");
		getContentPane().setLayout(new BorderLayout(0, 0));

		setBounds(100, 100, 1169, 686);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblWelcome = new JLabel("Welcome");
		panel.add(lblWelcome);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new MigLayout("", "[275.00][][275.00][][275.00]",
				"[28.00][grow]"));

		lblReportedIpsLast = new JLabel("Reported ips last 30 days");
		panel_1.add(lblReportedIpsLast, "cell 0 0,alignx center");

		JLabel lblAllTimeReports = new JLabel("All time reports");
		panel_1.add(lblAllTimeReports, "cell 2 0,alignx center,aligny center");

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, "cell 0 1,grow");

		tblLast30Days = new JTable();
		tblLast30Days.setModel(database.getRisk30DaysAdmin());
		scrollPane.setViewportView(tblLast30Days);

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, "cell 2 1,alignx center,growy");

		tblAllTimeReports = new JTable();

		tblAllTimeReports.setModel(database.getTotalReports());
		tblAllTimeReports.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				String ip = (String) tblAllTimeReports.getValueAt(row, 0);
				IPDetails details = new IPDetails(dataStore, ip);
				details.setVisible(true);
				dispose();
			}
		});

		scrollPane_1.setViewportView(tblAllTimeReports);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				LogData logData = new LogData(dataStore);
				dispose();
			}
		});
	}



}
